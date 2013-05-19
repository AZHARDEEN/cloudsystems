package br.com.mcampos.web.quartz;

import java.io.File;
import java.io.FilenameFilter;
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

import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.test.InepTestSession;
import br.com.mcampos.web.locator.ServiceLocator;

public abstract class InepBaseJob implements Serializable, Job
{
	private static final long serialVersionUID = 9162031955846573614L;
	private static final Logger logger = LoggerFactory.getLogger( InepBaseJob.class );
	private static final String path = "/loader/inep/provas/";
	private transient InepTestSession session = null;

	protected static String getPath( )
	{
		return path;
	}

	protected InepTestSession getSession( )
	{
		try {
			if ( session == null ) {
				session = (InepTestSession) ServiceLocator.getInstance( ).getRemoteSession( InepTestSession.class );
			}
		}
		catch ( NamingException e ) {
			e.printStackTrace( );
		}
		return session;
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

	protected String getBasePath( InepPackage item )
	{
		String eventPath = getPath( ) + item.getId( ).getCompanyId( ).toString( ) + "/" + item.getId( ).getId( ).toString( ) + "/";
		File filePath = new File( eventPath );
		if ( filePath.exists( ) == false ) {
			filePath.mkdirs( );
		}
		return eventPath;
	}

	protected String[ ] getFiles( InepPackage item, final String searchFor )
	{
		File dir;

		String eventPath = getBasePath( item );
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

}
