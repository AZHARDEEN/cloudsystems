package br.com.mcampos.web.core.report;

import java.io.StringWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;

import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Window;

import br.com.mcampos.web.core.BaseLoggedController;

public class JasperReportController extends BaseLoggedController<Window>
{
	private static final long serialVersionUID = 2753873781502371152L;
	public static final String paramName = "JasperReportSessionParameter";

	@Wire( "iframe" )
	private Iframe frame;

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		ReportItem item = (ReportItem) popSessionParameter( paramName );
		if ( item != null && item.getCallable( ) != null ) {
			doReport( item );
		}

	}

	@SuppressWarnings( { "unchecked", "rawtypes" } )
	private void doReport( ReportItem item ) throws JRException
	{
		JasperReport jasperReport;
		JasperPrint jasperPrint;

		String reportUrl = getMainWindow( ).getDesktop( ).getWebApp( ).getRealPath( item.getReportUrl( ) );
		jasperReport = JasperCompileManager.compileReport( reportUrl );
		Collection<?> collection = Collections.emptyList( );
		try {
			collection = item.getCallable( ).call( );
		}
		catch ( Exception e ) {
			e = null;
		}
		JRBeanCollectionDataSource jrCollection = new JRBeanCollectionDataSource( collection );
		jasperPrint = JasperFillManager.fillReport( jasperReport, new HashMap( ), jrCollection );
		AMedia media;
		switch ( item.getReportFormat( ) ) {
		case 2:
			String xml = JasperExportManager.exportReportToXml( jasperPrint );
			Filedownload.save( xml, "text/xml", null );
			break;
		case 3:
			StringWriter w = new StringWriter( );
			JRHtmlExporter htmlExporter = new JRHtmlExporter( );
			htmlExporter.setParameter( JRExporterParameter.JASPER_PRINT, jasperPrint );
			htmlExporter.setParameter( JRExporterParameter.OUTPUT_WRITER, w );
			htmlExporter.exportReport( );
			break;
		default:
			byte[ ] obj;
			obj = JasperExportManager.exportReportToPdf( jasperPrint );
			media = new AMedia( null, null, null, obj );
			getFrame( ).setContent( media );
			break;
		}
	}

	public Iframe getFrame( )
	{
		return this.frame;
	}

}
