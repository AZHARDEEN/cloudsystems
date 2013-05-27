package br.com.mcampos.web.quartz;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class InepOralTestLoader extends InepBaseJob
{
	private static final long serialVersionUID = 4850828799710714212L;
	private static String INITAL_PATH = "D:/Publico/Prova Oral - Celpe Bras 2013-1/";
	private ArrayList<String> fileToProcess = new ArrayList<String>( );
	private int nIndex = 0;

	@Override
	public void execute( JobExecutionContext arg0 ) throws JobExecutionException
	{

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
		System.out.println( "Processing: " + initialPath );
		File file = new File( initialPath );
		if ( file.exists( ) == false ) {
			// logger.error( "Directory " + initialPath + " does not exists!" );
			System.err.println( "Directory " + initialPath + " does not exists!" );
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
		Thread[ ] threads = new Thread[ 10 ];

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
