package br.com.mcampos.web.core.report;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

/**
 * Servlet implementation class JasperReportServlet
 */
@WebServlet( description = "JasperReportServlet handler", urlPatterns = { JasperReportServlet.reportUrl } )
public class JasperReportServlet extends BaseReportServlet
{
	private static final long serialVersionUID = 1L;

	public static final String reportUrl = "/jreport";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public JasperReportServlet( )
	{
		super( );
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGetPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		ReportItem item = getReportItem( request );
		if ( item != null ) {
			PrintWriter printWriter = response.getWriter( );
			try {
				JRHtmlExporter htmlExporter = new JRHtmlExporter( );
				response.setContentType( "text/html" );
				request.getSession( ).setAttribute( ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, item.getPrint( ) );
				htmlExporter.setParameter( JRExporterParameter.JASPER_PRINT, item.getPrint( ) );
				htmlExporter.setParameter( JRExporterParameter.OUTPUT_WRITER, printWriter );
				htmlExporter.setParameter( JRHtmlExporterParameter.CHARACTER_ENCODING, "UTF-8" );
				htmlExporter.setParameter( JRHtmlExporterParameter.IMAGES_URI, "image?image=" );
				htmlExporter.exportReport( );
			}
			catch ( JRException e ) {
				e.printStackTrace( printWriter );
			}
		}
	}
}
