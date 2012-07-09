package br.com.mcampos.web.core.report;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crystaldecisions.report.web.viewer.CrystalReportViewer;
import com.crystaldecisions.sdk.occa.report.application.OpenReportOptions;
import com.crystaldecisions.sdk.occa.report.application.ReportClientDocument;
import com.crystaldecisions.sdk.occa.report.lib.ReportSDKExceptionBase;

/**
 * Servlet implementation class CrystalReportServlet
 */
@WebServlet( { "/creport", "/report", "/Report" } )
public class CrystalReportServlet extends HttpServlet
{
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	public static final String PARAM_NAME = "CrystalReportViewer";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CrystalReportServlet( )
	{
		super( );
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		doGetPost( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		doGetPost( request, response );
	}

	private void doGetPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		response.setContentType( CONTENT_TYPE );
		try {
			doReport( "clients", request, response );
		}
		catch ( Exception e ) {
			e.printStackTrace( );
			showErrorMessage( response );
		}
	}

	private void showErrorMessage( HttpServletResponse response ) throws IOException
	{
		PrintWriter out = response.getWriter( );
		out.println( "<html>" );
		out.println( "<head><title>ReportServlet</title></head>" );
		out.println( "<body>" );
		out.println( "<p>The servlet has received a GET. This is the reply.</p>" );
		out.println( "</body></html>" );
		out.close( );
	}

	private void doReport( String reportName, HttpServletRequest request, HttpServletResponse response )
			throws ReportSDKExceptionBase, IOException
	{
		CrystalReportViewer viewer = new CrystalReportViewer( );
		ReportClientDocument crystalDoc = new ReportClientDocument( );

		crystalDoc.setReportAppServer( ReportClientDocument.inprocConnectionString );
		reportName = "/reports/" + reportName + ".rpt";
		reportName = getServletContext( ).getRealPath( reportName );
		crystalDoc.open( reportName, OpenReportOptions._discardSavedData );
		crystalDoc.getDatabaseController( ).logon( "jreport", "jreport" );
		viewer.setReportSource( crystalDoc.getReportSource( ) );
		viewer.setProductLocale( request.getLocale( ) );
		viewer.setOwnPage( true );
		viewer.setBestFitPage( true );
		viewer.setHasLogo( false );
		viewer.setHasRefreshButton( true );
		viewer.setName( "Relatório" );
		viewer.processHttpRequest( request, response, getServletContext( ), response.getWriter( ) );
		response.getWriter( ).close( );
	}
}
