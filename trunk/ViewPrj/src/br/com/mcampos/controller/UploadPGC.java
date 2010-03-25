package br.com.mcampos.controller;


import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;
import br.com.mcampos.util.locator.ServiceLocator;
import br.com.mcampos.util.locator.ServiceLocatorException;

import java.io.IOException;
import java.io.PrintWriter;

import java.text.SimpleDateFormat;

import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
            response.addHeader( "Router-Commit-ASH", "true" );
            response.addHeader( "Router-Commit-Application-Name", "PGC Recebido com sucesso" );
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
            byte[] pgcByte = new byte[ 64 * 1024 ];
            byte[] output = null;
            /*I really need pgc info*/
            int nRead;
            do {
                nRead = is.read( pgcByte );
                if ( nRead > 0 ) {
                    if ( output == null ) {
                        output = new byte[ nRead ];
                        System.arraycopy( pgcByte, 0, output, 0, nRead );
                    }
                    else
                        output = concat( output, pgcByte );
                }
            } while ( nRead > 0 );
            if ( output == null )
                return false;
            MediaDTO media = new MediaDTO();
            media.setFormat( "pgc" );
            media.setMimeType( "application/octet-stream" );
            Date currentDate = new Date();
            SimpleDateFormat format = new SimpleDateFormat( "dd_MM_yyyy_hh.mm.ss.SSS" );
            media.setName( String.format( "penDispatcher_%s.pcg", format.format( currentDate ) ) );
            media.setObject( output );
            PGCDTO pgc = new PGCDTO( media );
            session = ( AnodeFacade )getRemoteSession( AnodeFacade.class );
            //session.add( pgc );
            return true;
        }
        catch ( Exception e ) {
            System.out.println( e.getMessage() );
            return false;
        }
    }

    protected byte[] concat( byte[] A, byte[] B )
    {
        byte[] C = new byte[ A.length + B.length ];

        System.arraycopy( A, 0, C, 0, A.length );
        System.arraycopy( B, 0, C, A.length, B.length );
        return C;
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
