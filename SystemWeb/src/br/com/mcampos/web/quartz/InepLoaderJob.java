package br.com.mcampos.web.quartz;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.naming.NamingException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.ejb.inep.entity.InepTestPK;
import br.com.mcampos.ejb.inep.test.InepTestSession;
import br.com.mcampos.web.locator.ServiceLocator;

public class InepLoaderJob implements Serializable, Job
{
	private static final long serialVersionUID = 4328234055384577345L;
	private static final Logger logger = LoggerFactory.getLogger( InepLoaderJob.class );
	private static final String path = "T:/loader/inep/provas/";

	private transient InepTestSession session;

	@Override
	public void execute( JobExecutionContext arg0 ) throws JobExecutionException
	{
		String[ ] parts;
		boolean bRet;

		try {
			String[ ] files = getFiles( );
			for ( String file : files )
			{
				parts = file.split( "-" );
				if ( parts == null || parts.length < 2 ) {
					moveFile( path + file, path + "ERR/" );
					continue;
				}
				bRet = processFile( path + file, parts[ 0 ], Integer.parseInt( parts[ 1 ] ) );
				moveFile( path + file, bRet ? path + "PRO/" : path + "ERR/" );
			}
		}
		catch ( Exception e )
		{
			logger.error( e.getMessage( ) );
		}
	}

	private boolean processFile( String file, String subscription, Integer task )
	{
		InepTestPK key = new InepTestPK( );
		byte[ ] object = read( file );
		if ( object == null || object.length <= 0 ) {
			return false;
		}
		logger.info( "File has " + object.length + " bytes " );
		key.setCompanyId( 13623 );
		key.setEventId( 1 );
		key.setSubscriptionId( subscription );
		key.setTaskId( task );
		return getSession( ).insert( key, object );
	}

	private String[ ] getFiles( )
	{
		File dir;

		dir = new File( path );
		FilenameFilter filter = new FilenameFilter( )
		{
			@Override
			public boolean accept( File dir, String name )
			{
				name = name.toLowerCase( );
				return !name.startsWith( "." ) && name.endsWith( ".pdf" );
			}
		};
		return dir.list( filter );
	}

	private void moveFile( String filename, String directory )
	{
		try {
			logger.info( "Moving " + filename + " to " + directory );
			File file = new File( filename );
			if ( file.exists( ) == false ) {
				return;
			}
			File dest = new File( directory );
			if ( dest.exists( ) == false ) {
				if ( dest.mkdirs( ) == false ) {
					return;
				}
			}
			if ( file.renameTo( new File( dest, file.getName( ) ) ) == false ) {
				logger.error( "Error moving file " + filename + " to " + directory );
			}
		}
		catch ( Exception e )
		{
			logger.error( "Error moving file " + filename + " to directory:  " + directory );
			logger.error( e.getMessage( ) );
		}
	}

	protected InepTestSession getSession( )
	{
		try {
			if ( this.session == null ) {
				this.session = (InepTestSession) ServiceLocator.getInstance( ).getRemoteSession( InepTestSession.class );
			}
		}
		catch ( NamingException e ) {
			e.printStackTrace( );
		}
		return this.session;
	}

	private byte[ ] read( String aInputFileName )
	{
		File file = new File( aInputFileName );
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
					if ( bytesRead > 0 ) {
						totalBytesRead = totalBytesRead + bytesRead;
					}
				}
				/*
				 * the above style is a bit tricky: it places bytes into the
				 * 'result' array; 'result' is an output parameter; the while
				 * loop usually has a single iteration only.
				 */
			}
			finally {
				input.close( );
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

}
