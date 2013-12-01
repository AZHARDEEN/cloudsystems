package br.com.mcampos.batch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.MediaUtil;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.jpa.user.Company;
import br.com.mcampos.sysutils.SysUtils;

public class SimpleLoaderJob extends BaseQuartzJob
{
	private static final long serialVersionUID = -2723048064942513434L;
	private static final Logger LOGGER = LoggerFactory.getLogger( SimpleLoaderJob.class );

	@Override
	public void execute( JobExecutionContext context ) throws JobExecutionException
	{
		if ( this.amIRunning( context ) ) {
			return;
		}
		List<Company> companies = this.getSession( ).getCompanies( );
		if ( SysUtils.isEmpty( companies ) ) {
			return;
		}
		for ( Company company : companies ) {
			this.processCompanyUpload( company );
		}
	}

	private void processCompanyUpload( Company c )
	{
		String basePath = this.getBasePath( c );
		if ( SysUtils.isEmpty( basePath ) ) {
			LOGGER.error( "Error getting base path for company ", c.getName( ) );
			return;
		}
		List<String> items = SysUtils.searchDirectory( new File( basePath ) );
		if ( SysUtils.isEmpty( items ) ) {
			return;
		}
		for ( String item : items ) {
			this.processFile( c, new File( item ) );
		}
	}

	private void processFile( Company c, File file )
	{
		if ( !file.exists( ) )
		{
			LOGGER.error( "File " + file.getAbsolutePath( ) + " does not exists" );
			return;
		}
		if ( !file.canRead( ) ) {
			LOGGER.error( "File " + file.getAbsolutePath( ) + " cannot be read" );
			return;
		}
		try {
			InputStream is = new FileInputStream( file );
			byte[ ] buffer = SysUtils.readByteFromStream( is );
			is.close( );
			String mimeType = Files.probeContentType( Paths.get( file.getAbsolutePath( ) ) );
			if ( SysUtils.isEmpty( mimeType ) ) {
				mimeType = this.findOutMimeType( file.getName( ) );
			}
			LOGGER.info( "Processing " + file.getAbsolutePath( ) + ". MimeType is " + mimeType + ". Size: " + buffer.length );
			MediaDTO mediaDto = MediaUtil.getMediaDTO( c.getId( ), file.getName( ), buffer, mimeType );
			this.getSession( ).set( c, mediaDto );
			file.delete( );
		}
		catch ( IOException e ) {
			LOGGER.error( "Error acessing file " + file.getAbsolutePath( ), e );
			return;
		}
	}

	private String findOutMimeType( String fileName )
	{
		String mime = null;
		fileName = fileName.toLowerCase( );
		if ( fileName.endsWith( ".pdf" ) ) {
			mime = "application/pdf";
		}
		return mime;
	}
}
