package br.com.mcampos.web.core.report;

import java.io.File;
import java.security.InvalidParameterException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

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

	@Wire( "iframe" )
	private Iframe frame;

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );

		byte[ ] b = null;
		Object obj = this.getParameter( paramName );

		if ( obj != null && obj instanceof ReportItem ) {
			this.reportItem = (ReportItem) obj;
			b = JasperRunManager.runReportToPdf( this.compileReport( this.reportItem.getReportUrl( ) ), this.getParams( ), this.getConnection( ) );
			if ( b != null ) {
				this.setMedia( b );
			}
		}
	}

	private Map<String, Object> getParams( )
	{
		Object obj = this.getParameter( paramName );
		if ( obj == null || !( obj instanceof ReportItem ) ) {
			throw new InvalidParameterException( "Invalid Report Item Object" );
		}
		this.reportItem = (ReportItem) obj;
		if ( this.reportItem.getParams( ) == null ) {
			this.reportItem.setParams( new HashMap<String, Object>( ) );
		}
		return this.configureParameters( this.reportItem.getParams( ) );
	}

	private Map<String, Object> configureParameters( Map<String, Object> params )
	{
		if ( params == null ) {
			params = new HashMap<String, Object>( );
		}
		params.put( "IMAGE_PATH", this.getRealPath( "/img" ) );
		return params;
	}

	private void setMedia( byte[ ] obj )
	{
		AMedia media = new AMedia( this.reportItem.getName( ), "pdf", "application/pdf", obj );
		this.getFrame( ).setContent( media );
	}

	public Iframe getFrame( )
	{
		return this.frame;
	}

	/**
	 * Brief Obtém o objeto report do jasperreport
	 * 
	 * @param report
	 *            path do relatório relativo ao endereço /report do war.
	 * @param params
	 *            map com os parametros para o relatório
	 * @return JasperReport object
	 */
	protected JasperReport compileReport( @NotNull String report )
	{
		if ( report == null ) {
			throw new InvalidParameterException( "Report cannot be null in compileReport" );
		}
		/*
		 * if we could get a compiled report!!! Just try to 
		 * JasperReport jasperReport = (JasperReport)JRLoader.loadObject(new File("filename.jasper"));
		 */
		try {
			String realPath = this.getReportRealPath( report );
			String compiledFilename = realPath.replace( ".jrxml", ".jasper" );

			File file = new File( compiledFilename );
			if ( !file.exists( ) ) {
				logger.info( "Compiling report: " + report );
				JasperCompileManager.compileReportToFile( realPath, compiledFilename );
				if ( !file.exists( ) ) {
					logger.error( "Missing compiled report" + compiledFilename );
				}
			}
			return (JasperReport) JRLoader.loadObject( file );
		}
		catch ( Exception e ) {
			logger.error( "JRException", e );
			return null;
		}
	}

	private String getReportRealPath( String report )
	{
		String realPath;

		realPath = this.getRealPath( report );
		File file = new File( realPath );
		String name = SysUtils.getExtension( file );
		if ( SysUtils.isEmpty( name ) ) {
			name = realPath + ".jrxml";
		}
		else {
			name = realPath;
		}
		return name;
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
}
