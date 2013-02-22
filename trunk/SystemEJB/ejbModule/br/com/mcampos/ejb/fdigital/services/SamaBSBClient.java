package br.com.mcampos.ejb.fdigital.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.xml.rpc.ServiceException;

import org.apache.axis.encoding.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.sysutils.SysUtils;

/**
 * Session Bean implementation class SamaBSBClient
 */
@Singleton
@LocalBean
public class SamaBSBClient {
	private static final Logger logger = LoggerFactory
			.getLogger(SamaBSBClient.class);

	@Schedule(second = "*/30", minute = "*", hour = "*", persistent = false)
	public void lookForForms() {
		if (false) {
			try {
				Connection conn = getConnection();
				InovadoraWSServiceLocator stub = new InovadoraWSServiceLocator();
				InovadoraWS service = stub.getInovadoraWSPort();
				List<PgcPageDTO> list = getPgcs(conn);

				for (PgcPageDTO item : list) {
					String xml = getXml(conn, item);
					logger.info("Exporting........................................................................");
					logger.info(xml);
					service.addFicha(xml);
					updateRecordStatus(conn, item);
					logger.info("Done............................................................................");
				}
				conn.close();
			} catch (SQLException e) {
				logger.error("SqlException ", e);
			} catch (ClassNotFoundException e) {
				logger.error("Class Not Found Exception ", e);
			} catch (ServiceException e) {
				logger.error("ServiceException ", e);
			} catch (RemoteException e) {
				logger.error("RemoteException ", e);
			}
		}
	}

	protected Connection getConnection() throws SQLException,
			ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		String url = "jdbc:postgresql://69.59.21.123:5500/db_cloud";
		Properties props = new Properties();
		props.setProperty("user", "cloud");
		props.setProperty("password", "cloud");
		return DriverManager.getConnection(url, props);
	}

	private List<PgcPageDTO> getPgcs(Connection conn) throws SQLException {
		if (conn == null) {
			return Collections.emptyList();
		}
		ArrayList<PgcPageDTO> list = new ArrayList<PgcPageDTO>();
		ResultSet rSet = conn
				.createStatement()
				.executeQuery(
						"select "
								+ "distinct pgc.pgc_id_in "
								+ "from  "
								+ "pgc_page, pgc "
								+ "where  "
								+ "pgc.pgc_id_in = pgc_page.pgc_id_in "
								+ "and	frm_id_in in ( 7, 8 ) and coalesce ( ppg_exported_bt, 'false') = false "
								+ "and pgc.rst_id_in = 3 "
								+ "order by pgc_id_in ");
		while (rSet.next()) {
			PgcPageDTO item = new PgcPageDTO();
			item.setPgc_id_in(rSet.getInt("pgc_id_in"));
			list.add(item);
		}
		rSet.close();
		if (list.size() > 0) {
			if (list.size() == 1) {
				logger.info("Found just " + list.size()
						+ " record to export to SAMA");
			} else {
				logger.info("Found " + list.size()
						+ " records to export to SAMA");
			}
		}
		return list;
	}

	private void updateRecordStatus(Connection conn, PgcPageDTO page)
			throws SQLException {
		String sql;

		sql = "UPDATE pgc_page SET ppg_exported_bt = TRUE where not ppg_exported_bt AND pgc_id_in = "
				+ page.getPgc_id_in();
		logger.error("Updating PGC_PAGE where pgc_id_in =  "
				+ page.getPgc_id_in());
		conn.createStatement().executeUpdate(sql);
	}

	private String getXml(Connection conn, PgcPageDTO page) throws SQLException {
		boolean bFirst = true;
		if (conn == null || page == null) {
			return "";
		}
		String sql;
		StringBuffer xml = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");

		sql = "SELECT * FROM public.pgc, public.pgc_field, public.field_type WHERE pgc_field.flt_id_in = field_type.flt_id_in "
				+ "and pgc.pgc_id_in = "
				+ page.getPgc_id_in()
				+ " and pgc.pgc_id_in = pgc_field.pgc_id_in "
				+ " order by pgc_field.pgc_id_in, ppg_book_id, ppg_page_id ";

		ResultSet rSet = conn.createStatement().executeQuery(sql);
		xml.append("<FICHA-A");
		while (rSet.next()) {
			if (bFirst) {
				xml.append(" sequence=\"");
				xml.append(rSet.getInt("pgc_id_in"));
				xml.append("\"");

				xml.append(" pen=\"");
				xml.append(rSet.getString("pgc_pen_id"));
				xml.append("\"");

				xml.append(">\n");
				bFirst = false;
				getProperties(xml, conn, page.getPgc_id_in());
				getAttachments(xml, conn, page.getPgc_id_in());
				xml.append("\t<Fields>\n");
			}
			String fieldName = rSet.getString("pfl_name_ch").trim();
			fieldName = fieldName.replaceAll(" ", "_");
			int type = rSet.getInt("flt_id_in");
			if (rSet.getBoolean("pfl_has_penstrokes_bt")) {
				xml.append("\t\t<");
				xml.append(fieldName);
				xml.append(" filled=\"true\" type=\"");
				xml.append(type == 6 ? "Boolean" : "String");
				xml.append("\"");
				xml.append(">");
				if (type == 6) {
					xml.append("X");
				} else {
					String field = rSet.getString("pfl_revised_tx");
					if (SysUtils.isEmpty(field)) {
						field = rSet.getString("pfl_icr_tx");
					}
					if (SysUtils.isEmpty(field)) {
						field = "";
					}
					xml.append(field);
				}
				xml.append("</");
				xml.append(fieldName);
				xml.append(">\n");
			} else {
				xml.append("\t\t<");
				xml.append(fieldName);
				xml.append(" filled=\"false\" type=\"");
				xml.append(type == 6 ? "Boolean" : "String");
				xml.append("\"");
				xml.append("/>\n");
			}
		}
		xml.append("\t</Fields>\n");
		xml.append("</FICHA-A>\n");
		rSet.close();
		return xml.toString();
	}

	private void getProperties(StringBuffer xml, Connection conn, int pgcId)
			throws SQLException {
		if (conn == null) {
			return;
		}
		String sql;
		int sequence = 0;
		sql = "SELECT * FROM public.pgc_property WHERE pgp_id_in = 16386 and pgc_id_in = "
				+ pgcId;
		ResultSet rSet = conn.createStatement().executeQuery(sql);
		xml.append("\t<Properties>\n");
		Double latitude = 0D;
		Double longitude = 0D;
		while (rSet.next()) {
			sequence++;
			switch (sequence) {
			case 3:
				try {
					latitude = Double.parseDouble(rSet
							.getString("ppg_value_ch"));
				} catch (NumberFormatException e) {
					latitude = 0D;
				}
				break;
			case 4:
				try {
					longitude = Double.parseDouble(rSet
							.getString("ppg_value_ch"));
				} catch (NumberFormatException e) {
					longitude = 0D;
				}
				break;
			}
		}
		xml.append("\t\t<Gps>\n");
		xml.append("\t\t\t<latitute>" + latitude.toString() + "</latitute>\n");
		xml.append("\t\t\t<longitude>" + longitude.toString()
				+ "</longitude>\n");
		xml.append("\t\t</Gps>\n");
		xml.append("\t</Properties>\n");
		rSet.close();
	}

	private void getAttachments(StringBuffer xml, Connection conn, int pgcId)
			throws SQLException {
		if (conn == null) {
			return;
		}
		String sql;
		sql = "select * from pgc_attachment t1, media m where t1.med_id_in = m.med_id_in and pgc_id_in = "
				+ pgcId;
		ResultSet rSet = conn.createStatement().executeQuery(sql);
		xml.append("\t<Attachments>\n");
		while (rSet.next()) {
			String name;
			String type;

			name = rSet.getString("med_name_ch");
			type = rSet.getString("med_mime_ch");
			xml.append("\t\t<attachment code=\"Base64\" name=\"" + name
					+ "\" type=\"" + type + "\">\n");
			InputStream st = rSet.getBinaryStream("med_object_bin");
			byte[] allBytesInBlob;
			String coded = "";
			try {
				allBytesInBlob = SysUtils.readByteFromStream(st);
				coded = Base64.encode(allBytesInBlob);
			} catch (Exception e) {
				e.printStackTrace();
			}
			xml.append(coded);
			xml.append("\t\t</attachment>\n");
		}
		xml.append("\t</Attachments>\n");
		rSet.close();
	}

	byte[] readAndClose(InputStream aInput) {
		// carries the data from input to output :
		byte[] bucket = new byte[64 * 1024];
		ByteArrayOutputStream result = null;
		try {
			try {
				// Use buffering? No. Buffering avoids costly access to disk or
				// network;
				// buffering to an in-memory stream makes no sense.
				result = new ByteArrayOutputStream(bucket.length);
				int bytesRead = 0;
				while (bytesRead != -1) {
					// aInput.read() returns -1, 0, or more :
					bytesRead = aInput.read(bucket);
					if (bytesRead > 0) {
						result.write(bucket, 0, bytesRead);
					}
				}
			} finally {
				aInput.close();
				// result.close(); this is a no-operation for
				// ByteArrayOutputStream
			}
		} catch (IOException ex) {
			ex = null;
		}
		return result.toByteArray();
	}

}
