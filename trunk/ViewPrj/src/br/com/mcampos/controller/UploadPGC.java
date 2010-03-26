package br.com.mcampos.controller;


import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.cloudsystem.anode.facade.AnodeFacade;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.util.locator.ServiceLocator;
import br.com.mcampos.util.locator.ServiceLocatorException;
import br.com.mcampos.util.system.PgcFile;

import com.anoto.api.NoSuchPermissionException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;


public class UploadPGC extends HttpServlet
{
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
    private static final String TMP_DIR_PATH = "/anoto_res/tmp_dir";
    private static final String DESTINATION_DIR_PATH = "/files";

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


    protected File getTempDir()
    {
        File file;

        String realPath = getServletContext().getRealPath( TMP_DIR_PATH );
        file = new File( realPath );
        if ( file.exists() == false )
            file.mkdirs();
        return file;
    }


    protected PgcFile createDTO( byte[] pgc ) throws IOException, NoSuchPermissionException
    {
        Date now = new Date();
        SimpleDateFormat df = new SimpleDateFormat( "yyyyMmddHHmmssSSSS" );

        MediaDTO dto = new MediaDTO();
        dto.setFormat( "pgc" );
        dto.setMimeType( "application/octet-stream" );
        dto.setName( "uploaded" + df.format( now ) );
        dto.setObject( pgc );
        PgcFile file = new PgcFile();
        file.uploadPgc( dto );
        return file;
    }

    protected boolean getPGC( HttpServletRequest request )
    {
        try {
            if ( ServletFileUpload.isMultipartContent( request ) ) {
                return processMultiPart();
            }
            else {
                String header;

                header = request.getHeader( "Content-Type" );
                if ( SysUtils.isEmpty( header ) )
                    return false;
                if ( header.equals( "application/octet-stream" ) ) {
                    header = request.getHeader( "Content-Length" );
                    if ( SysUtils.isEmpty( header ) == false ) {
                        int totalSize = Integer.parseInt( header );
                        byte[] pgc = new byte[ totalSize ];
                        request.getInputStream().read( pgc );
                        PgcFile pgcFile = createDTO( pgc );
                        pgcFile.persist();
                    }
                }
            }
            return true;
        }
        catch ( Exception e ) {
            System.out.println( e.getMessage() );
            return false;
        }
    }

    protected boolean processMultiPart()
    {
        return false;
    }
}
