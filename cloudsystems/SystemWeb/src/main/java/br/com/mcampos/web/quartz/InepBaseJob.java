package br.com.mcampos.web.quartz;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import javax.naming.NamingException;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.ejb.inep.InepLoaderSession;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.sysutils.ServiceLocator;

public abstract class InepBaseJob implements Serializable, Job
{
	private static final long serialVersionUID = 9162031955846573614L;
	private static final Logger logger = LoggerFactory.getLogger( InepBaseJob.class );
	private static final String path = "/var/local/jboss/loader/inep/written/";
	private transient InepLoaderSession session = null;

	protected static String getPath( )
	{
		return path;
	}

	protected InepLoaderSession getSession( )
	{
		try {
			if ( this.session == null ) {
				this.session = (InepLoaderSession) ServiceLocator.getInstance( ).getRemoteSession( InepLoaderSession.class, ServiceLocator.EJB_NAME[ 0 ] );
			}
		}
		catch ( NamingException e ) {
			e.printStackTrace( );
		}
		return this.session;
	}

	protected boolean amIRunning( JobExecutionContext context )
	{
		Scheduler sched = context.getScheduler( );

		JobDetail existingJobDetail;
		try {
			existingJobDetail = sched.getJobDetail( context.getJobDetail( ).getKey( ) );
			if ( existingJobDetail != null ) {
				List<JobExecutionContext> currentlyExecutingJobs = sched.getCurrentlyExecutingJobs( );
				for ( JobExecutionContext jec : currentlyExecutingJobs ) {
					if ( existingJobDetail.equals( jec.getJobDetail( ) ) && jec.equals( context ) == false ) {
						return true;
					}
				}
			}
		}
		catch ( SchedulerException e ) {
			logger.error( "amIRunning Error", e );
		}
		return false;
	}

	protected String getBasePath( InepEvent item )
	{
		String eventPath = getPath( ) + item.getId( ).getCompanyId( ).toString( ) + "/" + item.getId( ).getId( ).toString( ) + "/";
		File filePath = new File( eventPath );
		if ( filePath.exists( ) == false ) {
			filePath.mkdirs( );
		}
		return eventPath;
	}

	protected String[ ] getFiles( InepEvent item, final String searchFor )
	{
		File dir;

		String eventPath = this.getBasePath( item );
		dir = new File( eventPath );
		FilenameFilter filter = new FilenameFilter( )
		{
			@Override
			public boolean accept( File dir, String name )
			{
				name = name.toLowerCase( );
				return !name.startsWith( "." ) && name.endsWith( searchFor );
			}
		};
		return dir.list( filter );
	}

	protected void moveFile( String filename, String directory )
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

	protected byte[ ] read( String aInputFileName )
	{
		File file = new File( aInputFileName );
		return this.readFile( file );
	}

	protected byte[ ] readFile( File file )
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
