package br.com.mcampos.web.quartz;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class OralTestWorkerThread implements Runnable
{
	private InepOralTestLoader job;

	public OralTestWorkerThread( InepOralTestLoader info )
	{
		job = info;
	}

	@Override
	public void run( )
	{
		String fileName;

		while ( ( fileName = job.getFileToProcess( ) ) != null ) {
			try {
				processFile( new File( fileName ) );
			}
			catch ( Exception e )
			{
				e = null;
			}
		}
	}

	private void processFile( File file ) throws IOException
	{
		byte[ ] obj = readFile( file );
		System.out.println( "Processing File: " + file.getCanonicalPath( ) + " with " + obj.length + " bytes " );
		obj = null;
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

}
