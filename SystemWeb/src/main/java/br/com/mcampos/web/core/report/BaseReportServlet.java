package br.com.mcampos.web.core.report;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import net.sf.jasperreports.view.JasperViewer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.sysutils.SysUtils;

public abstract class BaseReportServlet extends HttpServlet
{
	private static final long serialVersionUID = -3459374710231890502L;
	private static final Logger logger = LoggerFactory.getLogger( JDBCReportServlet.class );

	protected abstract void doGetPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException,
			IOException;

	@Override
	protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
	{
		doGetPost( req, resp );
	}

	@Override
	protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException
	{
		doGetPost( req, resp );
	}

	protected String getRealPath( String uri )
	{
		return getServletContext( ).getRealPath( uri );
	}

	protected ReportItem getReportItem( HttpServletRequest req )
	{
		ReportItem item = (ReportItem) req.getSession( ).getAttribute( JasperReportController.paramName );
		return item;
	}

	protected JasperPrint compileReport( String report, Map<String, Object> params )
	{
		if ( report == null ) {
			return null;
		}
		if ( params == null ) {
			params = new HashMap<String, Object>( );
		}
		String realPath;

		realPath = getRealPath( "/img" );
		params.put( "IMAGE_PATH", realPath );

		Connection conn = null;
		try {
			realPath = getRealPath( report );
			File file = new File( realPath );
			String name = SysUtils.getExtension( file );
			if ( SysUtils.isEmpty( name ) ) {
				name = realPath + ".jrxml";
			}
			else {
				name = realPath;
			}
			conn = getConnection( );
			logger.info( "Compiling report: " + report );
			JasperReport jasperReport = JasperCompileManager.compileReport( name );
			JasperPrint nRet = JasperFillManager.fillReport( jasperReport, params, conn );
			return nRet;
		}
		catch ( JRException e ) {
			logger.error( "JRException", e );
		}
		catch ( SQLException e ) {
			logger.error( "JRException", e );
		}
		catch ( ClassNotFoundException e ) {
			logger.error( "JRException", e );
		}
		catch ( Exception e ) {
			logger.error( "JRException", e );
		}
		finally {
			try {
				if ( conn != null && conn.isClosed( ) == false ) {
					conn.close( );
					conn = null;
				}
			}
			catch ( SQLException e ) {
				e.printStackTrace( );
			}
		}
		return null;
	}

	/**
	 * Sends a file to the ServletResponse output stream. Typically you want the browser to receive a different name than the name the file has been saved
	 * in your local database, since your local names need to be unique.
	 * 
	 * @param resp
	 *            The response
	 * @param filename
	 *            The name of the file you want to download.
	 */
	protected void doDownload( HttpServletResponse resp, String filename, byte[ ] bbuf ) throws IOException
	{
		ServletOutputStream op = resp.getOutputStream( );
		ServletContext context = getServletConfig( ).getServletContext( );
		String mimetype = context.getMimeType( filename );

		resp.setContentType( ( mimetype != null ) ? mimetype : "application/octet-stream" );
		resp.setContentLength( bbuf.length );
		resp.setHeader( "Content-Disposition", "attachment; filename=\"" + filename + "\"" );
		op.write( bbuf, 0, bbuf.length );
		op.flush( );
		op.close( );
	}

	protected void doDownload( HttpServletResponse resp, String filename, String bbuf, String alternateMime ) throws IOException
	{
		ServletOutputStream op = resp.getOutputStream( );
		ServletContext context = getServletConfig( ).getServletContext( );
		String mimetype = context.getMimeType( filename );

		resp.setContentType( mimetype != null ? mimetype : alternateMime != null ? alternateMime : "application/octet-stream" );
		resp.setContentLength( bbuf.length( ) );
		resp.setHeader( "Content-Disposition", "attachment; filename=\"" + filename + "\"" );
		op.print( bbuf );
		op.flush( );
		op.close( );
	}

	protected void exportToHTML( HttpServletRequest request, HttpServletResponse response ) throws IOException
	{
		ReportItem item = getReportItem( request );
		if ( item == null ) {
			return;
		}

		JRHtmlExporter htmlExporter = new JRHtmlExporter( );
		response.setContentType( "text/html" );
		JasperPrint print = compileReport( item.getReportUrl( ), item.getParams( ) );
		if ( print != null ) {
			PrintWriter printWriter = response.getWriter( );
			try {
				request.getSession( ).setAttribute( ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, print );
				htmlExporter.setParameter( JRExporterParameter.JASPER_PRINT, print );
				htmlExporter.setParameter( JRExporterParameter.OUTPUT_WRITER, printWriter );
				htmlExporter.setParameter( JRHtmlExporterParameter.CHARACTER_ENCODING, "UTF-8" );
				htmlExporter.setParameter( JRHtmlExporterParameter.IMAGES_URI, "image?image=" );
				htmlExporter.exportReport( );
			}
			catch ( JRException e ) {
				logger.error( "JRException", e );
			}
		}
	}

	protected void exportToPDF( HttpServletRequest request, HttpServletResponse response ) throws IOException
	{
		ReportItem item = getReportItem( request );
		if ( item == null ) {
			return;
		}

		JasperPrint print = compileReport( item.getReportUrl( ), item.getParams( ) );
		if ( print != null ) {
			try {
				JasperViewer.viewReport( print );
				if ( 1 != 1 ) {
					byte[ ] obj = JasperExportManager.exportReportToPdf( print );
					File file = new File( item.getReportUrl( ) );
					doDownload( response, file.getName( ) + ".pdf", obj );
				}
			}
			catch ( JRException e ) {
				logger.error( "JRException", e );
			}
		}
	}

	protected void exportToXML( HttpServletRequest request, HttpServletResponse response ) throws IOException
	{
		ReportItem item = getReportItem( request );

		if ( item == null ) {
			return;
		}
		JasperPrint print = compileReport( item.getReportUrl( ), item.getParams( ) );
		if ( print != null ) {
			try {
				String xml = JasperExportManager.exportReportToXml( print );
				File file = new File( item.getReportUrl( ) );
				doDownload( response, file.getName( ) + ".xml", xml, null );
			}
			catch ( JRException e ) {
				logger.error( "JRException", e );
			}
		}
	}

	protected void exportToXLS( HttpServletRequest request, HttpServletResponse response ) throws IOException
	{
		exportTo( new JRRtfExporter( ), request, response, "application/ms-excel", ".xls" );
	}

	protected void exportToRTF( HttpServletRequest request, HttpServletResponse response ) throws IOException
	{
		exportTo( new JRRtfExporter( ), request, response, "application/rtf", ".rtf" );
	}

	protected void exportToODS( HttpServletRequest request, HttpServletResponse response ) throws IOException
	{
		exportTo( new JROdsExporter( ), request, response, "application/vnd.oasis.opendocument.spreadsheet", ".ods" );
	}

	protected void exportToOdt( HttpServletRequest request, HttpServletResponse response ) throws IOException
	{
		exportTo( new JROdtExporter( ), request, response, "application/vnd.oasis.opendocument.text", ".odt" );
	}

	protected void exportToTxt( HttpServletRequest request, HttpServletResponse response ) throws IOException
	{
		exportTo( new JRTextExporter( ), request, response, "text/plain", ".txt" );
	}

	protected void exportToCsv( HttpServletRequest request, HttpServletResponse response ) throws IOException
	{
		exportTo( new JRCsvExporter( ), request, response, "text/plain", ".csv" );
	}

	protected void exportTo( JRExporter exporter, HttpServletRequest request, HttpServletResponse response, String contentType,
			String extension ) throws IOException
	{
		ReportItem item = getReportItem( request );

		if ( item == null || exporter == null ) {
			return;
		}
		JasperPrint print = compileReport( item.getReportUrl( ), item.getParams( ) );
		if ( print != null ) {
			try {
				exporter.setParameter( JRExporterParameter.JASPER_PRINT, print );
				exporter.setParameter( JRExporterParameter.OUTPUT_STREAM, response.getOutputStream( ) );
				response.setContentType( contentType );
				File file = new File( item.getReportUrl( ) );
				response.setHeader( "Content-Disposition", "attachment; filename=\"" + file.getName( ) + extension + "\"" );
				exporter.exportReport( );
			}
			catch ( JRException e ) {
				logger.error( "JRException", e );
			}
		}
	}

	protected Connection getConnection( ) throws Exception
	{
		InitialContext cxt = new InitialContext( );
		DataSource ds = (DataSource) cxt.lookup( "java:/ReportDS" );
		if ( ds != null ) {
			return ds.getConnection( );
		}
		Class.forName( "org.postgresql.Driver" );
		String url = "jdbc:postgresql://69.59.21.123:5500/inep";
		Properties props = new Properties( );
		props.setProperty( "user", "jreport" );
		props.setProperty( "password", "jreport" );
		return DriverManager.getConnection( url, props );
	}

}
