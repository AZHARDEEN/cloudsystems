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
				sched.scheduleJob( createLoaderJob( ), createLoaderTrigger( ) );
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
		JobDetail job = JobBuilder.newJob( InepLoaderJob.class )
				.withIdentity( "loaderJob", "mcampos" ).build( );
		return job;
	}

	private Trigger createLoaderTrigger( )
	{

		SimpleScheduleBuilder s = SimpleScheduleBuilder.simpleSchedule( );
		s.repeatForever( );
		s.withIntervalInSeconds( 60 );

		TriggerBuilder<Trigger> b = TriggerBuilder.newTrigger( );
		b.withIdentity( "loaderTrigger", "mcampos" );
		b.withSchedule( s );
		b.startNow( );
		return b.build( );
	}
}
