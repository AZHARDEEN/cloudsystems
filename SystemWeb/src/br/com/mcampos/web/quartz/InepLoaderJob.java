package br.com.mcampos.web.quartz;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepTestPK;
import br.com.mcampos.sysutils.SysUtils;

public class InepLoaderJob extends InepBaseJob
{
	private static final long serialVersionUID = 4328234055384577345L;
	private static final Logger logger = LoggerFactory.getLogger( InepLoaderJob.class );

	public static final String jobName = "loaderJob";
	public static final String jobGroup = "mcampos";

	@Override
	public void execute( JobExecutionContext arg0 ) throws JobExecutionException
	{
		String[ ] parts;
		boolean bRet;

		try {
			List<InepPackage> currentEvents;

			if ( amIRunning( arg0 ) ) {
				logger.info( "Already running!!!!" );
				return;
			}

			currentEvents = getSession( ).getAvailableEvents( );
			if ( SysUtils.isEmpty( currentEvents ) )
				return;
			for ( InepPackage item : currentEvents ) {
				String[ ] files = getFiles( item, ".pdf" );
				if ( files == null )
					return;
				for ( String file : files )
				{
					String eventPath = getBasePath( item );
					parts = file.split( "-" );
					if ( parts == null || parts.length < 2 ) {
						moveFile( eventPath + file, eventPath + "ERR/" );
						continue;
					}
					try {
						String subscripton = parts[ 0 ].trim( );
						Integer task;
						try {
							String aux = parts[ 1 ].trim( );
							int nIndex = aux.indexOf( '.' );
							if ( nIndex > 0 ) {
								aux = aux.substring( 0, nIndex );
							}
							task = Integer.parseInt( aux );
						}
						catch ( NumberFormatException e ) {
							moveFile( eventPath + file, eventPath + "ERR/" );
							continue;
						}
						bRet = processFile( item, eventPath + file, subscripton, task );
						moveFile( eventPath + file, bRet ? eventPath + "PRO/" : eventPath + "ERR/" );
					}
					catch ( Exception e ) {
						moveFile( eventPath + file, eventPath + "ERR/" );
						logger.error( e.getMessage( ) );
					}
				}
			}
		}
		catch ( Exception e )
		{
			logger.error( e.getMessage( ) );
		}
	}

	private boolean processFile( InepPackage pack, String file, String subscription, Integer task )
	{
		InepTestPK key = new InepTestPK( );
		byte[ ] object = read( file );
		if ( object == null || object.length <= 0 ) {
			return false;
		}
		logger.info( "File has " + object.length + " bytes " );
		key.setCompanyId( pack.getId( ).getCompanyId( ) );
		key.setEventId( pack.getId( ).getId( ) );
		key.setSubscriptionId( subscription );
		key.setTaskId( task );
		return getSession( ).insert( key, object );
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
