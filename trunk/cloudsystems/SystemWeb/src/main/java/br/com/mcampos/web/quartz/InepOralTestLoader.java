package br.com.mcampos.web.quartz;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InepOralTestLoader extends InepBaseJob
{
	private static final long serialVersionUID = 4850828799710714212L;
	private static String INITAL_PATH = "/var/local/jboss/loader/inep/audio";
	private ArrayList<String> fileToProcess = new ArrayList<String>( );
	private int nIndex = 0;
	private static final Logger logger = LoggerFactory.getLogger( InepLoaderJob.class );
	public static final String jobName = "oralLoaderJob";
	public static final String jobGroup = "mcampos";

	@Override
	public void execute( JobExecutionContext arg0 ) throws JobExecutionException
	{
		InepOralTestLoader loader = new InepOralTestLoader( );

		if ( amIRunning( arg0 ) ) {
			logger.info( "Already running!!!!" );
			return;
		}

		try {
			loader.processDirectory( INITAL_PATH );
			loader.processFiles( );
		}
		catch ( Exception e ) {
			e.printStackTrace( );
		}

	}

	public static void main( String[ ] args )
	{
		InepOralTestLoader loader = new InepOralTestLoader( );

		try {
			loader.processDirectory( INITAL_PATH );
			System.out.println( "We have about " + loader.getFilesToProcess( ).size( ) + " files to process " );
			loader.processFiles( );
		}
		catch ( Exception e ) {
			e.printStackTrace( );
		}

	}

	private void processDirectory( String initialPath ) throws IOException
	{
		File file = new File( initialPath );
		if ( file.exists( ) == false ) {
			// logger.error( "Directory " + initialPath + " does not exists!" );
			return;
		}
		if ( file.isDirectory( ) == false ) {
			// logger.error( initialPath + " is not a directory!" );
			System.err.println( initialPath + " is not a directory!" );
			return;
		}

		File[ ] files = file.listFiles( );
		if ( files == null || files.length == 0 )
			return;
		for ( File sub : files )
		{
			if ( sub.isDirectory( ) )
				processDirectory( sub.getCanonicalPath( ) );
			else {
				// logger.info( "Processing File " + sub.getAbsolutePath( ) );
				addFileToList( sub );
			}
		}
	}

	private void addFileToList( File file ) throws IOException
	{
		if ( file == null )
			return;
		if ( file.exists( ) == false ) {
			// logger.error( "Directory " + initialPath + " does not exists!" );
			System.err.println( "File " + file.getName( ) + " does not exists!" );
			return;
		}
		if ( file.isDirectory( ) ) {
			// logger.error( initialPath + " is not a directory!" );
			System.err.println( file.getName( ) + " is a directory. This is wrong!" );
			return;
		}
		fileToProcess.add( file.getCanonicalPath( ) );
	}

	public ArrayList<String> getFilesToProcess( )
	{
		return fileToProcess;
	}

	private void processFiles( )
	{
		Thread[ ] threads = new Thread[ 2 ];

		for ( int i = 0; i < threads.length; i++ ) {
			threads[ i ] = new Thread( new OralTestWorkerThread( this ) );
		}

		for ( Thread thread : threads )
		{
			thread.start( );
		}

		for ( Thread thread : threads )
		{
			try {
				thread.join( );
			}
			catch ( Exception e )
			{
				e = null;
			}
		}

	}

	public synchronized String getFileToProcess( )
	{
		try {
			return getFilesToProcess( ).get( nIndex++ );
		}
		catch ( Exception e )
		{
			return null;
		}
	}
}
