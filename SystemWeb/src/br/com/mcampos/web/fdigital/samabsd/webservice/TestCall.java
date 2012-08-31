package br.com.mcampos.web.fdigital.samabsd.webservice;

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

import javax.xml.rpc.ServiceException;

import org.apache.axis.AxisFault;
import org.apache.axis.encoding.Base64;

import br.com.mcampos.sysutils.SysUtils;

public class TestCall
{
	public static void main( String[ ] args )
	{
		TestCall tc = new TestCall( );

		tc.export( );
	}

	protected Connection getConnection( ) throws SQLException, ClassNotFoundException
	{
		Class.forName( "org.postgresql.Driver" );
		String url = "jdbc:postgresql:fdigital";
		Properties props = new Properties( );
		props.setProperty( "user", "cloud" );
		props.setProperty( "password", "cloud" );
		return DriverManager.getConnection( url, props );
	}

	public boolean export( )
	{
		Connection conn = null;
		try {
			conn = getConnection( );
			InovadoraWSServiceLocator stub = new InovadoraWSServiceLocator( );
			InovadoraWS service = stub.getInovadoraWSPort( );
			List<PgcPage> list = getPgcs( conn );
			for ( PgcPage item : list ) {
				String xml = getXml( conn, item );
				System.out.println( xml );
				if ( false ) {
					service.addFicha( xml );
				}
			}
			conn.close( );
			return true;
		}
		catch ( ServiceException e ) {
			e.printStackTrace( );
			return false;
		}
		catch ( AxisFault e ) {
			e.printStackTrace( );
			return false;
		}
		catch ( RemoteException e ) {
			e.printStackTrace( );
			return false;
		}
		catch ( SQLException e ) {
			e.printStackTrace( );
			return false;
		}
		catch ( ClassNotFoundException e ) {
			e.printStackTrace( );
			return false;
		}
		finally {
			try {
				if ( conn != null && conn.isClosed( ) == false ) {
					conn.close( );
				}
			}
			catch ( SQLException e ) {
				e.printStackTrace( );
			}
		}
	}

	private List<PgcPage> getPgcs( Connection conn ) throws SQLException
	{
		if ( conn == null ) {
			return Collections.emptyList( );
		}
		ArrayList<PgcPage> list = new ArrayList<PgcPage>( );
		ResultSet rSet = conn.createStatement( ).executeQuery(
				"select distinct pgc_id_in from pgc_page where frm_id_in = 7 order by pgc_id_in " );
		while ( rSet.next( ) ) {
			PgcPage item = new PgcPage( );
			item.setPgc_id_in( rSet.getInt( "pgc_id_in" ) );
			list.add( item );
		}
		rSet.close( );
		return list;
	}

	private String getXml( Connection conn, PgcPage page ) throws SQLException
	{
		boolean bFirst = true;
		if ( conn == null || page == null ) {
			return "";
		}
		String sql;
		StringBuffer xml = new StringBuffer( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" );

		sql = "SELECT * FROM public.pgc, public.pgc_field, public.field_type WHERE pgc_field.flt_id_in = field_type.flt_id_in " +
				"and pgc.pgc_id_in = " + page.getPgc_id_in( ) + " and pgc.pgc_id_in = pgc_field.pgc_id_in "
				+ " order by pgc_field.pgc_id_in, ppg_book_id, ppg_page_id ";

		ResultSet rSet = conn.createStatement( ).executeQuery( sql );
		xml.append( "<FICHA-A" );
		while ( rSet.next( ) ) {
			if ( bFirst ) {
				xml.append( " sequence=\"" );
				xml.append( rSet.getInt( "pgc_id_in" ) );
				xml.append( "\"" );

				xml.append( " pen=\"" );
				xml.append( rSet.getString( "pgc_pen_id" ) );
				xml.append( "\"" );

				xml.append( ">\n" );
				bFirst = false;
				getProperties( xml, conn, page.getPgc_id_in( ) );
				getAttachments( xml, conn, page.getPgc_id_in( ) );
				xml.append( "\t<Fields>\n" );
			}
			String fieldName = rSet.getString( "pfl_name_ch" ).trim( );
			fieldName = fieldName.replaceAll( " ", "_" );
			int type = rSet.getInt( "flt_id_in" );
			if ( rSet.getBoolean( "pfl_has_penstrokes_bt" ) ) {
				xml.append( "\t\t<" );
				xml.append( fieldName );
				xml.append( " filled=\"true\" type=\"" );
				xml.append( type == 6 ? "Boolean" : "String" );
				xml.append( "\"" );
				xml.append( ">" );
				if ( type == 6 ) {
					xml.append( "X" );
				}
				else {
					String field = rSet.getString( "pfl_revised_tx" );
					if ( SysUtils.isEmpty( field ) ) {
						field = rSet.getString( "pfl_icr_tx" );
					}
					if ( SysUtils.isEmpty( field ) ) {
						field = "";
					}
					xml.append( field );
				}
				xml.append( "</" );
				xml.append( fieldName );
				xml.append( ">\n" );
			}
			else {
				xml.append( "\t\t<" );
				xml.append( fieldName );
				xml.append( " filled=\"false\" type=\"" );
				xml.append( type == 6 ? "Boolean" : "String" );
				xml.append( "\"" );
				xml.append( "/>\n" );
			}
		}
		xml.append( "\t</Fields>\n" );
		xml.append( "</FICHA-A>\n" );
		rSet.close( );
		return xml.toString( );
	}

	private void getProperties( StringBuffer xml, Connection conn, int pgcId ) throws SQLException
	{
		if ( conn == null ) {
			return;
		}
		String sql;
		int sequence = 0;
		sql = "SELECT * FROM public.pgc_property WHERE pgp_id_in = 16386 and pgc_id_in = " + pgcId;
		ResultSet rSet = conn.createStatement( ).executeQuery( sql );
		xml.append( "\t<Properties>\n" );
		Double latitude = 0D;
		Double longitude = 0D;
		while ( rSet.next( ) ) {
			sequence++;
			switch ( sequence ) {
			case 3:
				try {
					latitude = Double.parseDouble( rSet.getString( "ppg_value_ch" ) );
				}
				catch ( NumberFormatException e ) {
					latitude = 0D;
				}
				break;
			case 4:
				try {
					longitude = Double.parseDouble( rSet.getString( "ppg_value_ch" ) );
				}
				catch ( NumberFormatException e ) {
					longitude = 0D;
				}
				break;
			}
		}
		xml.append( "\t\t<Gps>\n" );
		xml.append( "\t\t\t<latitute>" + latitude.toString( ) + "</latitute>\n" );
		xml.append( "\t\t\t<longitude>" + longitude.toString( ) + "</longitude>\n" );
		xml.append( "\t\t</Gps>\n" );
		xml.append( "\t</Properties>\n" );
		rSet.close( );
	}

	private void getAttachments( StringBuffer xml, Connection conn, int pgcId ) throws SQLException
	{
		if ( conn == null ) {
			return;
		}
		String sql;
		sql = "select * from pgc_attachment t1, media m where t1.med_id_in = m.med_id_in and pgc_id_in = " + pgcId;
		ResultSet rSet = conn.createStatement( ).executeQuery( sql );
		xml.append( "\t<Attachments>\n" );
		while ( rSet.next( ) ) {
			String name;
			String type;

			name = rSet.getString( "med_name_ch" );
			type = rSet.getString( "med_mime_ch" );
			xml.append( "\t\t<attachment code=\"Base64\" name=\"" + name + "\" type=\"" + type + "\">\n" );
			InputStream st = rSet.getBinaryStream( "med_object_bin" );
			byte[ ] allBytesInBlob;
			String coded = "";
			try {
				allBytesInBlob = SysUtils.readByteFromStream( st );
				coded = Base64.encode( allBytesInBlob );
			}
			catch ( Exception e ) {
				e.printStackTrace( );
			}
			xml.append( coded );
			xml.append( "\t\t</attachment>\n" );
		}
		xml.append( "\t</Attachments>\n" );
		rSet.close( );
	}

	byte[ ] readAndClose( InputStream aInput )
	{
		// carries the data from input to output :
		byte[ ] bucket = new byte[ 64 * 1024 ];
		ByteArrayOutputStream result = null;
		try {
			try {
				// Use buffering? No. Buffering avoids costly access to disk or network;
				// buffering to an in-memory stream makes no sense.
				result = new ByteArrayOutputStream( bucket.length );
				int bytesRead = 0;
				while ( bytesRead != -1 ) {
					// aInput.read() returns -1, 0, or more :
					bytesRead = aInput.read( bucket );
					if ( bytesRead > 0 ) {
						result.write( bucket, 0, bytesRead );
					}
				}
			}
			finally {
				aInput.close( );
				// result.close(); this is a no-operation for ByteArrayOutputStream
			}
		}
		catch ( IOException ex ) {
			ex = null;
		}
		return result.toByteArray( );
	}
}
