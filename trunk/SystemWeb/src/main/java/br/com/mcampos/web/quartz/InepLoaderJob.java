package br.com.mcampos.web.quartz;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.entity.inep.InepPackage;
import br.com.mcampos.entity.inep.InepTestPK;
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
							if ( "VERSO".equalsIgnoreCase( aux ) ) {
								aux = parts[ 2 ].trim( );
							}
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
}
