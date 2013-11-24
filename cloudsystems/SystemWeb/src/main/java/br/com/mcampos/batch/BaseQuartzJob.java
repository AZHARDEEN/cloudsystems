package br.com.mcampos.batch;

import java.io.File;
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

import br.com.mcampos.ejb.quartz.QuartzSession;
import br.com.mcampos.jpa.user.Company;
import br.com.mcampos.sysutils.ServiceLocator;
import br.com.mcampos.sysutils.SysUtils;

public abstract class BaseQuartzJob implements Serializable, Job
{
	private static final long serialVersionUID = 7866422337038599574L;
	private static final Logger LOGGER = LoggerFactory.getLogger( BaseQuartzJob.class );
	private static final String BASE_PATH = "/var/local/jboss/loader";
	private static final String BASE_PATH_PARAM_NAME = "BaseQuartzJobUploadPathName";

	private QuartzSession session;

	protected QuartzSession getSession( )
	{
		try {
			if ( this.session == null ) {
				this.session = (QuartzSession) ServiceLocator.getInstance( ).getRemoteSession( QuartzSession.class, ServiceLocator.EJB_NAME[ 0 ] );
			}
		}
		catch ( NamingException e ) {
			LOGGER.error( "Error getting session object", e );
		}
		return this.session;
	}

	protected String getBasePath( )
	{
		String basePath = this.getSession( ).get( BASE_PATH_PARAM_NAME );
		if ( SysUtils.isEmpty( basePath ) ) {
			basePath = BASE_PATH;
		}
		return basePath;
	}

	protected String getBasePath( Company c )
	{
		String basePath = this.getBasePath( );
		basePath += "/" + c.getId( );
		File file = new File( basePath );
		if ( !file.exists( ) ) {
			if ( !file.mkdirs( ) ) {
				LOGGER.error( "Cannot create directory " + file.getAbsolutePath( ) );
				return null;
			}
		}
		return basePath;
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
			LOGGER.error( "amIRunning Error", e );
		}
		return false;
	}

}
