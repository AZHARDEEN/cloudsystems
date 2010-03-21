package br.com.mcampos.controller;

import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;

import br.com.mcampos.util.locator.ServiceLocator;
import br.com.mcampos.util.locator.ServiceLocatorException;

import com.anoto.api.PenCreationException;
import com.anoto.api.PenHome;

import java.io.IOException;
import java.io.PrintWriter;

import java.text.SimpleDateFormat;

import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.*;

public class UploadPGC extends HttpServlet
{
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

    public void init( ServletConfig config ) throws ServletException
    {
        super.init( config );
    }

    /**Process the HTTP doGet request.
     */
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

    protected void doGetPost( HttpServletRequest request, HttpServletResponse response, String method ) throws ServletException,
                                                                                                               IOException
    {
        response.setContentType( CONTENT_TYPE );
        if ( getPGC( request ) == true ) {
            response.addHeader( "Router-Commit-ASH", "HTTP 200 OK" );
            response.addHeader( "Router-Commit-Application-Name", "Minha Aplicação" );
        }
        PrintWriter out = response.getWriter();
        out.println( "<html>" );
        out.println( "<head>" );
        out.println( "<title>UploadPGC</title>" );
        out.println( "</head>" );
        out.println( "<body>" );
        out.println( "<p>The servlet has received a POST. This is the reply.</p>" );
        out.println( "</body></html>" );
        out.close();
    }


    protected boolean getPGC( HttpServletRequest req )
    {
        AnodeFacade session;

        try {
            ServletInputStream is = req.getInputStream();
            System.out.println( "Size: " + is.available() );
            if ( is.available() > 0 ) {
                byte[] pgcByte = new byte[ is.available() ];
                /*I really need pgc info*/
                int nRead = is.read( pgcByte );
                System.out.println( "Bytes Read: " + nRead );
                while ( nRead != pgcByte.length && nRead > 0 ) {
                    nRead += is.read( pgcByte, nRead, pgcByte.length );
                    System.out.println( "Bytes Read: " + nRead );
                }
                MediaDTO media = new MediaDTO();
                media.setFormat( "pgc" );
                media.setMimeType( "application/octet-stream" );
                Date currentDate = new Date();
                SimpleDateFormat format = new SimpleDateFormat( "dd_MM_yyyy_hh.mm.ss.SSS" );
                media.setName( String.format( "penDispatcher_%s.pcg", format.format( currentDate ) ) );
                media.setObject( pgcByte );
                PGCDTO pgc = new PGCDTO( media );
                session = ( AnodeFacade )getRemoteSession( AnodeFacade.class );
                session.add( pgc );
                return true;
            }
            else
                return false;
        }
        catch ( Exception e ) {
            System.out.println( e.getMessage() );
            return false;
        }
    }

    protected AnodeFacade getRemoteSession( Class remoteClass )
    {
        try {
            return ( AnodeFacade )ServiceLocator.getInstance().getRemoteSession( remoteClass );
        }
        catch ( ServiceLocatorException e ) {
            throw new NullPointerException( "Invalid EJB Session (possible null)" );
        }
    }
}
