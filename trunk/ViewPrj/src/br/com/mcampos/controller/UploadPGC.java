package br.com.mcampos.controller;


import br.com.mcampos.controller.anoto.util.AnotoDir;
import br.com.mcampos.controller.anoto.util.PadFile;
import br.com.mcampos.controller.anoto.util.PgcFile;
import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.sysutils.SysUtils;

import com.anoto.api.NoSuchPermissionException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


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

    private void doGetPost( HttpServletRequest request, HttpServletResponse response, String method ) throws ServletException, IOException
    {
        System.out.println( "New pgc received." );
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


    private PgcFile createDTO( byte[] pgc ) throws IOException, NoSuchPermissionException
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

    private MediaDTO createMedia( FileItem item ) throws IOException
    {
        MediaDTO media = new MediaDTO();
        media.setMimeType( item.getContentType() );
        media.setObject( readByte( item ) );
        int nIndex = item.getName().lastIndexOf( '/' );
        if ( nIndex != -1 )
            media.setName( item.getName().substring( nIndex + 1 ) );
        else
            media.setName( item.getName() );
        if ( item.getName().endsWith( ".jpg" ) ) {
            media.setFormat( "jpg" );
            media.setMimeType( "image/jpeg" );
        }
        return media;
    }

    private boolean getPGC( HttpServletRequest request )
    {
        try {
            if ( ServletFileUpload.isMultipartContent( request ) ) {
                return processMultiPart( request );
            }
            else {
                String header;

                header = request.getHeader( "Content-Type" );
                if ( SysUtils.isEmpty( header ) )
                    return false;
                System.out.println( "Header content type is: " + header );
                if ( header.startsWith( "application/octet-stream" ) ) {
                    header = request.getHeader( "Content-Length" );
                    if ( SysUtils.isEmpty( header ) == false ) {
                        System.out.println( "Total Size is: " + header );
                        int totalSize = Integer.parseInt( header );
                        byte[] pgc = readByte( request.getInputStream(), totalSize );
                        savePgcFile( pgc );
                        return persist( request, pgc, null );
                    }
                }
            }
            return false;
        }
        catch ( Exception e ) {
            System.out.println( e.getMessage() );
            return false;
        }
    }

    private byte[] readByte( InputStream is, int totalSize ) throws IOException
    {
        byte[] pgc = new byte[ totalSize ];
        int offset = 0;
        int nRead;
        do {
            nRead = is.read( pgc, offset, totalSize - offset );
            if ( nRead == -1 )
                break;
            offset += nRead;
        } while ( offset < totalSize );
        return pgc;
    }

    private byte[] readByte( FileItem item ) throws IOException
    {
        byte[] buffer;

        if ( item.isInMemory() )
            buffer = item.get();
        else {
            buffer = readByte( item.getInputStream(), ( int )item.getSize() );
        }
        return buffer;
    }

    private boolean persist( HttpServletRequest request, byte[] pgc, ArrayList<MediaDTO> medias ) throws IOException, NoSuchPermissionException, ApplicationException
    {
        PgcFile pgcFile = createDTO( pgc );
        PadFile.setHttpRealPath( getAnotoPath() );
        PGCDTO insertedPgc = pgcFile.persist( medias );
        if ( insertedPgc == null )
            return false;
        System.out.println( "PGC was successfully received: " + insertedPgc.getId() );
        return true;
    }

    private boolean processMultiPart( HttpServletRequest request ) throws IOException, NoSuchPermissionException, ApplicationException
    {
        System.out.println( "Multipart file received " );
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold( 32 * 1024 * 1024 );
        String path = getAnotoPath();
        path += "/file_upload";
        System.out.println( "Path: " + path );
        File res = new File( path );
        if ( res.exists() == false )
            res.mkdirs();
        factory.setRepository( res );
        ServletFileUpload upload = new ServletFileUpload( factory );
        upload.setSizeMax( 6 * 1024 * 1024 );
        List /* FileItem */items;
        byte[] pgc = null;
        ArrayList<MediaDTO> medias = null;
        System.out.println( "Processando envio:" );
        try {
            items = upload.parseRequest( request );
            System.out.println( "Itens to parse: " + items.size() );
            Iterator iter = items.iterator();
            while ( iter != null && iter.hasNext() ) {
                FileItem item = ( FileItem )iter.next();

                System.out.println( "ItemProperties" );
                System.out.println( "\t" + item.getContentType() );
                System.out.println( "\t" + item.getFieldName() );
                System.out.println( "\t" + item.getSize() );
                System.out.println( "\t" + item.getName() );
                if ( item.getFieldName().equalsIgnoreCase( "pgcFile" ) ) {
                    pgc = readByte( item );
                    savePgcFile( pgc );
                }
                else if ( item.getFieldName().equalsIgnoreCase( "attachedFile" ) ) {
                    if ( medias == null )
                        medias = new ArrayList<MediaDTO>();
                    medias.add( createMedia( item ) );
                }
            }
            return persist( request, pgc, medias );
        }
        catch ( FileUploadException e ) {
            System.out.println( e.getMessage() );
            return false;
        }
    }

    private void savePgcFile( byte[] pgc )
    {

        try {
            String path = getAnotoPath() + "/saved_pcg";
            DateFormat df = new SimpleDateFormat( "yyyyMMddHHmmssSSS" );
            Date now = new Date();
            File file = new File( path + "/" + df.format( now ) );
            FileOutputStream fos = new FileOutputStream( file );
            fos.write( pgc );
            fos.close();
        }
        catch ( Exception e ) {
            e = null;
        }
    }

    private String getAnotoPath()
    {
        String realPath = AnotoDir.anotoServerPath;
        System.out.println( "Realpath: " + realPath );
        File file = new File( realPath );
        if ( file.exists() == false )
            file.mkdirs();
        return realPath;
    }
}
