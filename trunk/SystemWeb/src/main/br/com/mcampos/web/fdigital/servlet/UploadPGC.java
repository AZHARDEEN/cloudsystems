package br.com.mcampos.web.fdigital.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.omg.CORBA.portable.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.dto.MediaDTO;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.fdigital.util.AnotoDir;
import br.com.mcampos.web.fdigital.util.PadFile;
import br.com.mcampos.web.fdigital.util.PgcFile;

import com.anoto.api.NoSuchPermissionException;

/**
 * Servlet implementation class UploadPGC
 */
@WebServlet( urlPatterns = { "/uploadpgc", "/upload_pgc" } )
public class UploadPGC extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private static final Logger logger = LoggerFactory.getLogger( UploadPGC.class );

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
		logger.info( "UploadPGC has been called" );
		if ( getPGC( request, response ) == true ) {
			logger.info( "PGC was successfully received!! " );
			response.addHeader( "Router-Commit-ASH", "true" );
			response.addHeader( "Router-Commit-Application-Name", "PGC Recebido com sucesso" );
		}
		else {
			logger.error( "Error on receive pgc" );
		}
	}

	private boolean getPGC( HttpServletRequest request, HttpServletResponse response )
	{
		logger.info( "getPGC has been called" );
		try {
			if ( ServletFileUpload.isMultipartContent( request ) ) {
				return processMultiPart( request );
			}
			else {
				String header;

				logger.info( "Simple Part" );
				header = request.getHeader( "Content-Type" );
				if ( SysUtils.isEmpty( header ) ) {
					logger.error( "Conten-Type is empty or header is empty" );
					header = "application/octet-stream";
				}
				logger.info( "Header content type is: " + header );
				if ( header.startsWith( "application/octet-stream" ) ) {
					header = request.getHeader( "Content-Length" );
					if ( SysUtils.isEmpty( header ) == false ) {
						logger.info( "Total Size is: " + header );
						int totalSize = Integer.parseInt( header );
						byte[ ] pgc = readByte( request.getInputStream( ), totalSize );
						savePgcFile( pgc );
						return persist( pgc, null );
					}
				}
			}
			return false;
		}
		catch ( Exception e ) {
			logger.error( "getPGC error", e );
			return false;
		}
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

	private boolean persist( byte[ ] pgc, ArrayList<MediaDTO> medias ) throws IOException, NoSuchPermissionException,
			ApplicationException
	{
		PadFile.setHttpRealPath( getAnotoPath( ) );
		MediaDTO pgcFile = createDTO( pgc );
		Thread uploadPGC = new Thread( new PgcFile( pgcFile, medias ) );
		uploadPGC.start( );
		return true;
	}

	@SuppressWarnings( "unchecked" )
	private boolean processMultiPart( HttpServletRequest request ) throws IOException, NoSuchPermissionException,
			ApplicationException
	{
		logger.info( "Multipart file received " );
		DiskFileItemFactory factory = new DiskFileItemFactory( );
		factory.setSizeThreshold( 32 * 1024 * 1024 );
		String path = getAnotoPath( );
		path += "/file_upload";
		logger.info( "Path: " + path );
		File res = new File( path );
		if ( res.exists( ) == false && res.mkdirs( ) == false ) {
			logger.error( "Mkdir error! Trying to continue" );
		}
		factory.setRepository( res );
		ServletFileUpload upload = new ServletFileUpload( factory );
		upload.setSizeMax( 1 * 1024 * 1024 );
		List<FileItem> items;
		byte[ ] pgc = null;
		ArrayList<MediaDTO> medias = null;
		logger.info( "Processando envio:" );
		try {
			items = upload.parseRequest( request );
			logger.info( "Itens to parse: " + items.size( ) );
			for ( FileItem item : items ) {
				logger.info( "ItemProperties" );
				logger.info( "\t" + item.getContentType( ) );
				logger.info( "\t" + item.getFieldName( ) );
				logger.info( "\t" + item.getSize( ) );
				logger.info( "\t" + item.getName( ) );
				if ( item.getFieldName( ).equalsIgnoreCase( "pgcFile" ) ) {
					pgc = readByte( item );
					// savePgcFile( pgc );
				}
				else if ( item.getFieldName( ).equalsIgnoreCase( "attachedFile" ) ) {
					if ( medias == null ) {
						medias = new ArrayList<MediaDTO>( );
					}
					medias.add( createMedia( item ) );
				}
			}
			return persist( pgc, medias );
		}
		catch ( FileUploadException e ) {
			logger.error( "", e );
			return false;
		}
	}

	private void savePgcFile( byte[ ] pgc )
	{

		try {
			String path = getAnotoPath( ) + "/saved_pcg";
			File file = new File( path );
			if ( file.exists( ) == false && file.mkdirs( ) == false ) {
				logger.warn( "error creating directory " + path );
			}
			DateFormat df = new SimpleDateFormat( "yyyyMMddHHmmssSSS" );
			Date now = new Date( );
			String str = path + "/" + df.format( now );
			logger.info( "Trying to save file " + str );
			file = new File( str );
			FileOutputStream fos = new FileOutputStream( file );
			fos.write( pgc );
			fos.close( );
		}
		catch ( Exception e ) {
			logger.error( "savePgcFile error", e );
		}
	}

	private String getAnotoPath( )
	{
		String realPath = AnotoDir.anotoServerPath;
		File file = new File( realPath );
		if ( file.exists( ) == false && file.mkdirs( ) == false ) {
			logger.error( "Mkdir Error. Trying to continue" );
		}
		return realPath;
	}

	private byte[ ] readByte( FileItem item ) throws IOException
	{
		byte[ ] buffer;

		if ( item.isInMemory( ) ) {
			buffer = item.get( );
		}
		else {
			buffer = readByte( item.getInputStream( ), (int) item.getSize( ) );
		}
		return buffer;
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

	private MediaDTO createMedia( FileItem item ) throws IOException
	{
		MediaDTO media = new MediaDTO( );
		media.setMimeType( item.getContentType( ) );
		media.setObject( readByte( item ) );
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

}
