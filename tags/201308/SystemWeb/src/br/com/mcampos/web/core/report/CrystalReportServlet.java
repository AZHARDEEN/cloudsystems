package br.com.mcampos.web.core.report;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crystaldecisions.report.web.viewer.CrPrintMode;
import com.crystaldecisions.report.web.viewer.CrystalReportViewer;
import com.crystaldecisions.sdk.occa.report.application.OpenReportOptions;
import com.crystaldecisions.sdk.occa.report.application.ReportClientDocument;
import com.crystaldecisions.sdk.occa.report.data.ConnectionInfo;
import com.crystaldecisions.sdk.occa.report.data.ConnectionInfos;
import com.crystaldecisions.sdk.occa.report.data.IConnectionInfo;
import com.crystaldecisions.sdk.occa.report.lib.ReportSDKException;
import com.crystaldecisions.sdk.occa.report.lib.ReportSDKExceptionBase;
import com.crystaldecisions.sdk.occa.report.reportsource.IReportSource;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		doGetPost( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
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
		} catch( Exception e ) {
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

		viewer.setReportSource( getSource( request ) );
		viewer.setDatabaseLogonInfos( getConnectionInfo( ) );
		viewer.setEnableLogonPrompt( false );
		viewer.setOwnPage( true );
		viewer.setBestFitPage( true );
		viewer.setHasLogo( false );
		viewer.setHasRefreshButton( true );
		viewer.setName( "Relatório" );
		viewer.setReuseParameterValuesOnRefresh( false );
		viewer.setPrintMode( CrPrintMode.ACTIVEX );
		viewer.processHttpRequest( request, response, getServletContext( ), response.getWriter( ) );
		response.getWriter( ).close( );
	}

	private IReportSource getSource( HttpServletRequest request ) throws ReportSDKException
	{
		// IReportSourceFactory2 rsf =new JPEReportSourceFactory();
		// IReportSource rptSource = (IReportSource)rsf.createReportSource(
		// getReportRealFilename(),request.getLocale());
		// return rptSource;

		IReportSource rptSource = null;
		String reportName = getReportRealFilename( );
		ReportClientDocument reportClientDocument = (ReportClientDocument) request.getSession( ).getAttribute( reportName );

		if ( reportClientDocument == null ) {
			reportClientDocument = new ReportClientDocument( );
			reportClientDocument.setReportAppServer( ReportClientDocument.inprocConnectionString ); // Version
																									// 12+
			reportClientDocument.setLocale( request.getLocale( ) );
			reportClientDocument.enableBuiltinControllers( );
			reportClientDocument.open( getReportRealFilename( ), OpenReportOptions._openAsReadOnly + OpenReportOptions._discardSavedData );
			rptSource = reportClientDocument.getReportSource( );
			request.getSession( ).setAttribute( reportName, reportClientDocument );
		}
		return rptSource;
	}

	private ConnectionInfos getConnectionInfo( )
	{
		ConnectionInfos connInfos = new ConnectionInfos( );
		IConnectionInfo connInfo1 = new ConnectionInfo( );
		connInfo1.setUserName( "jreport" );
		connInfo1.setPassword( "jreport" );
		connInfos.add( connInfo1 );
		return connInfos;
	}

	private String getReportRealFilename( )
	{
		String reportName = "clients";

		reportName = "/reports/" + reportName + ".rpt";
		reportName = getServletContext( ).getRealPath( reportName );
		return reportName;
	}
}
