package br.com.mcampos.web.listener;

import java.security.InvalidParameterException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.quartz.Job;
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

import br.com.mcampos.batch.SimpleLoaderJob;
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
	private static final Logger LOGGER = LoggerFactory.getLogger( QuartzLoaderListener.class );

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

				sched.scheduleJob( this.createLoaderJob( ), this.createLoaderTrigger( InepLoaderJob.jobName ) );
				sched.scheduleJob( this.createOralGradeLoaderJob( ), this.createLoaderTrigger( InepOralTestGradeLoader.jobName ) );
				sched.scheduleJob( this.createOralLoaderJob( ), this.createLoaderTrigger( InepOralTestLoader.jobName ) );
				sched.scheduleJob( this.createSimpleJob( SimpleLoaderJob.class, "SimpleLoaderJob" ),
						this.createLoaderTrigger( SimpleLoaderJob.class.getSimpleName( ), 300 ) );
				sched.startDelayed( 60 );
				LOGGER.info( "Started scheduler" );
			}
			LOGGER.info( "Scheduler is on" );
		}
		catch ( SchedulerException e ) {
			LOGGER.error( "Error starting scheduler" );
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
				LOGGER.info( "Shutdown quartz scheduler" );
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
		return this.createLoaderTrigger( jobName, 60 );
	}

	private Trigger createLoaderTrigger( String jobName, Integer interval )
	{

		SimpleScheduleBuilder s = SimpleScheduleBuilder.simpleSchedule( );
		s.repeatForever( );
		s.withIntervalInSeconds( interval );

		TriggerBuilder<Trigger> b = TriggerBuilder.newTrigger( );
		b.withIdentity( jobName, InepLoaderJob.jobGroup );
		b.withSchedule( s );
		return b.build( );
	}

	private JobDetail createSimpleJob( Class<? extends Job> clazz, String description )
	{
		if ( clazz == null ) {
			throw new InvalidParameterException( "createSimpleJob: Class<? extends Job> cannot be null" );
		}

		LOGGER.info( "Creating quartz job for " + clazz.getSimpleName( ) );
		JobBuilder builder = JobBuilder.newJob( clazz );
		builder.withIdentity( clazz.getSimpleName( ), InepLoaderJob.jobGroup );
		builder.withDescription( description );
		builder.storeDurably( );
		return builder.build( );

	}
}
