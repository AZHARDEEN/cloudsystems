package br.com.mcampos.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.mcampos.web.quartz.InepLoaderJob;
import br.com.mcampos.web.quartz.InepOralTestGradeLoader;
import br.com.mcampos.web.quartz.InepOralTestLoader;

/**
 * Application Lifecycle Listener implementation class QuartzLoaderListener.
 * 
 */
@WebListener( )
public class QuartzLoaderListener implements ServletContextListener
{
	private static final Logger logger = LoggerFactory.getLogger( QuartzLoaderListener.class );

	/**
	 * Default constructor.
	 */
	public QuartzLoaderListener( )
	{
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	@Override
	public void contextInitialized( ServletContextEvent arg0 )
	{

		try {
			// First we must get a reference to a scheduler
			SchedulerFactory sf = new StdSchedulerFactory( );
			Scheduler sched = sf.getScheduler( );

			if ( sched.isStarted( ) == false ) {

				sched.scheduleJob( createLoaderJob( ), createLoaderTrigger( InepLoaderJob.jobName ) );
				sched.scheduleJob( createOralGradeLoaderJob( ), createLoaderTrigger( InepOralTestGradeLoader.jobName ) );
				sched.scheduleJob( createOralLoaderJob( ), createLoaderTrigger( InepOralTestLoader.jobName ) );

				sched.start( );
				logger.info( "Started scheduler" );
			}
			logger.info( "Scheduler is on" );
		}
		catch ( SchedulerException e ) {
			logger.error( "Error starting scheduler" );
			e.printStackTrace( );
		}
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	@Override
	public void contextDestroyed( ServletContextEvent arg0 )
	{
		try {
			// First we must get a reference to a scheduler
			SchedulerFactory sf = new StdSchedulerFactory( );
			Scheduler sched = sf.getScheduler( );

			if ( sched.isStarted( ) == true ) {
				logger.info( "Shutdown quartz scheduler" );
				sched.shutdown( );
			}
		}
		catch ( SchedulerException e ) {
			e.printStackTrace( );
		}
	}

	private JobDetail createLoaderJob( )
	{
		JobBuilder builder = JobBuilder.newJob( InepLoaderJob.class );
		builder.withIdentity( InepLoaderJob.jobName, InepLoaderJob.jobGroup );
		builder.withDescription( "InepOralTestGradeLoader" );
		builder.storeDurably( );
		return builder.build( );
	}

	private JobDetail createOralGradeLoaderJob( )
	{
		JobBuilder builder = JobBuilder.newJob( InepOralTestGradeLoader.class );
		builder.withIdentity( InepOralTestGradeLoader.jobName, InepLoaderJob.jobGroup );
		builder.withDescription( "InepOralTestGradeLoader" );
		builder.storeDurably( );
		return builder.build( );
	}

	private JobDetail createOralLoaderJob( )
	{
		JobBuilder builder = JobBuilder.newJob( InepOralTestLoader.class );
		builder.withIdentity( InepOralTestLoader.jobName, InepLoaderJob.jobGroup );
		builder.withDescription( "InepOralTestGradeLoader" );
		builder.storeDurably( );
		return builder.build( );
	}

	private Trigger createLoaderTrigger( String jobName )
	{

		SimpleScheduleBuilder s = SimpleScheduleBuilder.simpleSchedule( );
		s.repeatForever( );
		s.withIntervalInSeconds( 60 );

		TriggerBuilder<Trigger> b = TriggerBuilder.newTrigger( );
		b.withIdentity( jobName, InepLoaderJob.jobGroup );
		b.withSchedule( s );
		b.startNow( );
		return b.build( );
	}

}
