package br.com.mcampos.web.quartz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.entity.inep.InepOralTest;
import br.com.mcampos.entity.inep.InepPackage;
import br.com.mcampos.sysutils.SysUtils;

public class InepOralTestGradeLoader extends InepBaseJob
{
	private static final long serialVersionUID = 8868066826707447404L;
	private static final Logger logger = LoggerFactory.getLogger( InepBaseJob.class );
	public static final String jobName = "oralGradeLoader";

	@Override
	public void execute( JobExecutionContext context ) throws JobExecutionException
	{
		if ( amIRunning( context ) )
			return;

		List<InepPackage> currentEvents;

		try {
			currentEvents = getSession( ).getAvailableEvents( );
		}
		catch ( Exception e ) {
			return;
		}

		for ( InepPackage item : currentEvents ) {
			String eventPath = getBasePath( item );
			String files[] = getFiles( item, "nota_oral.csv" );
			if ( files == null )
				return;
			for ( String file : files ) {
				try {
					boolean bRet = processFile( item, file );
					moveFile( eventPath + file, bRet ? eventPath + "PRO/" : eventPath + "ERR/" );
				}
				catch ( Exception e )
				{
					logger.error( "Process Oral Test File Error", e );
					moveFile( eventPath + file, eventPath + "ERR/" );
				}
			}
		}
	}

	private boolean processFile( InepPackage item, String fileName )
	{
		File file = new File( getBasePath( item ) + fileName );

		if ( file.exists( ) == false )
			return false;

		try {
			BufferedReader br = new BufferedReader( new FileReader( file ) );
			String line;
			int lineNumber = 0;
			InepOralTest entity;

			while ( SysUtils.isEmpty( ( line = br.readLine( ) ) ) == false ) {
				lineNumber++;
				if ( lineNumber == 1 )
					continue;
				String[ ] parts = line.split( ";" );
				if ( parts.length < 8 )
					continue;
				entity = new InepOralTest( item );
				entity.setStation( parts[ 1 ] );
				entity.getId( ).setSubscriptionId( parts[ 2 ] );
				entity.setInterviewGrade( SysUtils.parseBigDecimal( parts[ 3 ] ) );
				entity.setObserverGrade( SysUtils.parseBigDecimal( parts[ 4 ] ) );
				entity.setFinalGrade( SysUtils.parseBigDecimal( parts[ 5 ] ) );
				entity.setDescStatus( parts[ 7 ] );
				getSession( ).insert( entity, true );
			}
			br.close( );
		}
		catch ( IOException e ) {
			logger.error( "ProcessFile ", e );
		}
		return true;
	}
}
