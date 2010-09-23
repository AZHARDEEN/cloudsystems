package br.com.mcampos.controller.reporting;


import com.crystaldecisions.report.web.viewer.CrystalReportViewer;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ReportServlet extends HttpServlet
{
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
    public static final String PARAM_NAME = "CrystalReportViewer";

    public void init( ServletConfig config ) throws ServletException
    {
        super.init( config );
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        doGetPost( request, response, "GET" );
    }

    /**Process the HTTP doPost request.
     */
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        doGetPost( request, response, "POST" );
    }

    private void doGetPost( HttpServletRequest request, HttpServletResponse response, String method ) throws ServletException,
                                                                                                             IOException
    {
        response.setContentType( CONTENT_TYPE );
        CrystalReportViewer viewer = ( CrystalReportViewer )request.getSession().getAttribute( PARAM_NAME );
        if ( viewer == null ) {
            showErrorMessage( response );
            return;
        }
        try {
            viewer.setProductLocale( request.getLocale() );
            viewer.setOwnPage( true );
            viewer.setBestFitPage( true );
            viewer.setHasLogo( false );
            viewer.setHasRefreshButton( true );
            viewer.setName( "Relatório" );
            viewer.processHttpRequest( request, response, getServletContext(), response.getWriter() );
            response.getWriter().close();
        }
        catch ( Exception e ) {
            showErrorMessage( response );
        }
    }


    private void showErrorMessage( HttpServletResponse response ) throws IOException
    {
        PrintWriter out = response.getWriter();
        out.println( "<html>" );
        out.println( "<head><title>ReportServlet</title></head>" );
        out.println( "<body>" );
        out.println( "<p>The servlet has received a GET. This is the reply.</p>" );
        out.println( "</body></html>" );
        out.close();
    }

}
