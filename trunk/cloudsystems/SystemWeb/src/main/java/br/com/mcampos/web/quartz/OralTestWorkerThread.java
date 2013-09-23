package br.com.mcampos.web.quartz;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.naming.NamingException;

import br.com.mcampos.dto.MediaDTO;
import br.com.mcampos.ejb.inep.InepOralFacade;
import br.com.mcampos.sysutils.ServiceLocator;

public class OralTestWorkerThread implements Runnable
{
	private final InepOralTestLoader job;
	private InepOralFacade session;

	public OralTestWorkerThread( InepOralTestLoader info )
	{
		job = info;
	}

	public InepOralFacade getSession( )
	{
		try {
			if ( session == null )
				session = (InepOralFacade) ServiceLocator.getInstance( ).getRemoteSession( InepOralFacade.class, ServiceLocator.ejbName[ 0 ] );
		}
		catch ( NamingException e ) {
			e.printStackTrace( );
		}
		return session;
	}

	@Override
	public void run( )
	{
		String fileName;

		while ( ( fileName = job.getFileToProcess( ) ) != null )
			try {
				processFile( new File( fileName ) );
			}
			catch ( Exception e )
			{
				e = null;
			}
	}

	private void processFile( File file ) throws IOException
	{
		byte[ ] obj = readFile( file );
		System.out.println( "Processing File: " + file.getCanonicalPath( ) + " with " + obj.length + " bytes " );
		String isc = file.getName( );
		String[ ] parts = isc.split( "\\." );
		boolean bRet = getSession( ).uploadAudio( 13623, 1, parts[ 0 ], createMedia( file, obj ) );
		String a1, a2;

		a1 = file.getCanonicalPath( );
		a2 = file.getParentFile( ).getParent( ) + ( bRet ? "\\AUDIO_PROCESSED\\PRO" : "\\AUDIO_PROCESSED\\ERR" );
		moveFile( a1, a2 );
		obj = null;
	}

	private MediaDTO createMedia( File file, byte[ ] obj )
	{
		MediaDTO media = new MediaDTO( );
		media.setObject( obj );
		String name = file.getName( ).toLowerCase( );
		media.setName( file.getName( ) );

		if ( name.endsWith( ".mp3" ) )
			media.setMimeType( "audio/mpeg3" );
		else if ( name.endsWith( ".wav" ) )
			media.setMimeType( "audio/wav" );
		else if ( name.endsWith( ".wma" ) )
			media.setMimeType( "Audio/x-ms-wma" );
		else
			media.setMimeType( "undefined" );
		return media;
	}

	private byte[ ] readFile( File file )
	{
		byte[ ] result = new byte[ (int) file.length( ) ];
		try {
			InputStream input = null;
			try {
				int totalBytesRead = 0;
				input = new BufferedInputStream( new FileInputStream( file ) );
				while ( totalBytesRead < result.length ) {
					int bytesRemaining = result.length - totalBytesRead;
					// input.read() returns -1, 0, or more :
					int bytesRead = input.read( result, totalBytesRead, bytesRemaining );
					if ( bytesRead > 0 )
						totalBytesRead = totalBytesRead + bytesRead;
				}
				/*
				 * the above style is a bit tricky: it places bytes into the
				 * 'result' array; 'result' is an output parameter; the while
				 * loop usually has a single iteration only.
				 */
			}
			finally {
				input.close( );
				input = null;
			}
		}
		catch ( FileNotFoundException ex ) {
			ex = null;
		}
		catch ( IOException ex ) {
			ex = null;
		}
		return result;
	}

	protected void moveFile( String filename, String directory )
	{
		try {
			File file = new File( filename );
			if ( file.exists( ) == false )
				return;
			File dest = new File( directory );
			if ( dest.exists( ) == false )
				if ( dest.mkdirs( ) == false )
					return;
			file.renameTo( new File( dest, file.getName( ) ) );
		}
		catch ( Exception e )
		{
			e.printStackTrace( );
		}
	}

}
