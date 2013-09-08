package br.com.mcampos.web.core.report;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Window;

import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseLoggedController;

public class JasperReportController extends BaseLoggedController<Window>
{
	private static final long serialVersionUID = 2753873781502371152L;
	public static final String paramName = "JasperReportParameter";
	private static final Logger logger = LoggerFactory.getLogger( JasperReportController.class );
	private ReportItem reportItem;
	private JasperReport report;

	@Wire( "iframe" )
	private Iframe frame;

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		Object obj = getParameter( paramName );
		if ( obj != null && obj instanceof ReportItem ) {
			reportItem = (ReportItem) obj;
			Connection conn = getConnection( );
			if ( getReport( ) == null ) {
				setReport( compileReport( reportItem.getReportUrl( ), reportItem.getParams( ) ) );
			}
			byte[ ] b = JasperRunManager.runReportToPdf( getReport( ), cofigureParameters( reportItem.getParams( ) ), conn );
			if ( b != null ) {
				setMedia( b );
			}
		}
	}

	private Map<String, Object> cofigureParameters( Map<String, Object> params )
	{
		if ( params == null ) {
			params = new HashMap<String, Object>( );
		}
		String realPath;

		realPath = getRealPath( "/img" );
		params.put( "IMAGE_PATH", realPath );
		return params;
	}

	private void setMedia( byte[ ] obj )
	{
		AMedia media = new AMedia( reportItem.getName( ), "pdf", "application/pdf", obj );
		getFrame( ).setContent( media );
	}

	public Iframe getFrame( )
	{
		return frame;
	}

	protected JasperReport compileReport( String report, Map<String, Object> params )
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

		/*
		 * if we could get a compiled report!!! Just try to 
		 * JasperReport jasperReport = (JasperReport)JRLoader.loadObject(new File("filename.jasper"));
		 */

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
			logger.info( "Compiling report: " + report );
			JasperReport jasperReport = JasperCompileManager.compileReport( name );
			return jasperReport;
		}
		catch ( JRException e ) {
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

	protected String getRealPath( String uri )
	{
		return Executions.getCurrent( ).getDesktop( ).getWebApp( ).getRealPath( uri );
	}

	protected Connection getConnection( ) throws Exception
	{
		InitialContext cxt = new InitialContext( );
		DataSource ds = (DataSource) cxt.lookup( "java:/ReportDS" );
		return ds.getConnection( );
	}

	private JasperReport getReport( )
	{
		return report;
	}

	private void setReport( JasperReport report )
	{
		this.report = report;
	}
}
