package br.com.mcampos.controller;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.controller.anoto.util.AnotoDir;
import br.com.mcampos.controller.anoto.util.PadFile;
import br.com.mcampos.controller.anoto.util.PgcFile;
import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.sysutils.SysUtils;

import com.anoto.api.NoSuchPermissionException;

public class UploadPGC extends HttpServlet
{
	private static final long serialVersionUID = -2307731255551700215L;
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private static final Logger LOGGER = LoggerFactory.getLogger( UploadPGC.class.getSimpleName( ) );

	@Override
	public void init( ServletConfig config ) throws ServletException
	{
		super.init( config );
	}

	/**
	 * Process the HTTP doGet request.
	 */
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		this.doGetPost( request, response, "GET" );
	}

	/**
	 * Process the HTTP doPost request.
	 */
	@Override
	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		this.doGetPost( request, response, "POST" );
	}

	private void doGetPost( HttpServletRequest request, HttpServletResponse response, String method ) throws ServletException,
			IOException
	{
		LOGGER.info( "New pgc received." );
		response.setContentType( CONTENT_TYPE );
		if ( this.getPGC( request ) == true ) {
			LOGGER.info( "PGC was successfully received!! " );
			response.addHeader( "Router-Commit-ASH", "true" );
			response.addHeader( "Router-Commit-Application-Name", "PGC Recebido com sucesso" );
		}
		PrintWriter out = response.getWriter( );
		out.println( "<html>" );
		out.println( "<head>" );
		out.println( "<title>UploadPGC</title>" );
		out.println( "</head>" );
		out.println( "<body>" );
		out.println( "<p>The servlet has received a POST. This is the reply.</p>" );
		out.println( "</body></html>" );
		out.close( );
	}

	private MediaDTO createDTO( byte[ ] pgc ) throws IOException, NoSuchPermissionException
	{
		Date now = new Date( );
		SimpleDateFormat df = new SimpleDateFormat( "yyyy_MM_dd_HH_mm_ss_SSSS" );

		MediaDTO dto = new MediaDTO( );
		dto.setFormat( "pgc" );
		dto.setMimeType( "application/octet-stream" );
		dto.setName( "uploaded" + df.format( now ) + ".pgc" );
		dto.setObject( pgc );
		return dto;
	}

	private MediaDTO createMedia( FileItem item ) throws IOException
	{
		MediaDTO media = new MediaDTO( );
		media.setMimeType( item.getContentType( ) );
		media.setObject( this.readByte( item ) );
		int nIndex = item.getName( ).lastIndexOf( '/' );
		if ( nIndex != -1 ) {
			media.setName( item.getName( ).substring( nIndex + 1 ) );
		}
		else {
			media.setName( item.getName( ) );
		}
		if ( item.getName( ).endsWith( ".jpg" ) ) {
			media.setFormat( "jpg" );
			media.setMimeType( "image/jpeg" );
		}
		return media;
	}

	private boolean getPGC( HttpServletRequest request )
	{
		try {
			if ( ServletFileUpload.isMultipartContent( request ) ) {
				return this.processMultiPart( request );
			}
			else {
				String header;

				header = request.getHeader( "Content-Type" );
				if ( SysUtils.isEmpty( header ) ) {
					return false;
				}
				LOGGER.info( "Header content type is: " + header );
				if ( header.startsWith( "application/octet-stream" ) ) {
					header = request.getHeader( "Content-Length" );
					if ( SysUtils.isEmpty( header ) == false ) {
						LOGGER.info( "Total Size is: " + header );
						int totalSize = Integer.parseInt( header );
						byte[ ] pgc = this.readByte( request.getInputStream( ), totalSize );
						this.savePgcFile( pgc );
						return this.persist( pgc, null );
					}
				}
			}
			return false;
		}
		catch ( Exception e ) {
			LOGGER.error( "getPGC: ", e );
			return false;
		}
	}

	private byte[ ] readByte( InputStream is, int totalSize ) throws IOException
	{
		byte[ ] pgc = new byte[ totalSize ];
		int offset = 0;
		int nRead;
		do {
			nRead = is.read( pgc, offset, totalSize - offset );
			if ( nRead == -1 ) {
				break;
			}
			offset += nRead;
		} while ( offset < totalSize );
		return pgc;
	}

	private byte[ ] readByte( FileItem item ) throws IOException
	{
		byte[ ] buffer;

		if ( item.isInMemory( ) ) {
			buffer = item.get( );
		}
		else {
			buffer = this.readByte( item.getInputStream( ), (int) item.getSize( ) );
		}
		return buffer;
	}

	private boolean persist( byte[ ] pgc, ArrayList<MediaDTO> medias ) throws IOException, NoSuchPermissionException,
			ApplicationException
	{
		PadFile.setHttpRealPath( this.getAnotoPath( ) );
		MediaDTO pgcFile = this.createDTO( pgc );
		Thread uploadPGC = new Thread( new PgcFile( pgcFile, medias ) );
		uploadPGC.run( );
		return true;
	}

	@SuppressWarnings( "rawtypes" )
	private boolean processMultiPart( HttpServletRequest request ) throws IOException, NoSuchPermissionException,
			ApplicationException
	{
		LOGGER.info( "Multipart file received " );
		DiskFileItemFactory factory = new DiskFileItemFactory( );
		factory.setSizeThreshold( 32 * 1024 * 1024 );
		String path = this.getAnotoPath( );
		path += "/file_upload";
		LOGGER.info( "Path: " + path );
		File res = new File( path );
		if ( res.exists( ) == false ) {
			res.mkdirs( );
		}
		factory.setRepository( res );
		ServletFileUpload upload = new ServletFileUpload( factory );
		upload.setSizeMax( 6 * 1024 * 1024 );
		List /* FileItem */items;
		byte[ ] pgc = null;
		ArrayList<MediaDTO> medias = null;
		LOGGER.info( "Processando envio:" );
		try {
			items = upload.parseRequest( request );
			LOGGER.info( "Itens to parse: " + items.size( ) );
			Iterator iter = items.iterator( );
			while ( iter != null && iter.hasNext( ) ) {
				FileItem item = (FileItem) iter.next( );

				LOGGER.info( "ItemProperties" );
				LOGGER.info( "\t" + item.getContentType( ) );
				LOGGER.info( "\t" + item.getFieldName( ) );
				LOGGER.info( "\t" + item.getSize( ) );
				LOGGER.info( "\t" + item.getName( ) );
				if ( item.getFieldName( ).equalsIgnoreCase( "pgcFile" ) ) {
					pgc = this.readByte( item );
					this.savePgcFile( pgc );
				}
				else if ( item.getFieldName( ).equalsIgnoreCase( "attachedFile" ) ) {
					if ( medias == null ) {
						medias = new ArrayList<MediaDTO>( );
					}
					medias.add( this.createMedia( item ) );
				}
			}
			return this.persist( pgc, medias );
		}
		catch ( FileUploadException e ) {
			LOGGER.error( "processMultiPart", e );
			return false;
		}
	}

	private void savePgcFile( byte[ ] pgc )
	{

		try {
			String path = this.getAnotoPath( ) + "/saved_pcg";
			DateFormat df = new SimpleDateFormat( "yyyyMMddHHmmssSSS" );
			Date now = new Date( );
			File file = new File( path + "/" + df.format( now ) );
			FileOutputStream fos = new FileOutputStream( file );
			fos.write( pgc );
			fos.close( );
		}
		catch ( Exception e ) {
			LOGGER.error( "savePgcFile", e );
		}
	}

	private String getAnotoPath( )
	{
		String realPath = AnotoDir.anotoServerPath;
		LOGGER.info( "Realpath: " + realPath );
		File file = new File( realPath );
		if ( file.exists( ) == false ) {
			file.mkdirs( );
		}
		return realPath;
	}
}
