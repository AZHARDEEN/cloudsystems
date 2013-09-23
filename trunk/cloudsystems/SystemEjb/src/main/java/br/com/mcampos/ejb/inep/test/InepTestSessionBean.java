package br.com.mcampos.ejb.inep.test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.inep.media.InepMediaSessionLocal;
import br.com.mcampos.ejb.inep.oral.InepOralTestSessionLocal;
import br.com.mcampos.ejb.inep.packs.InepPackageSessionLocal;
import br.com.mcampos.ejb.inep.subscription.InepSubscriptionSessionLocal;
import br.com.mcampos.ejb.inep.task.InepTaskSessionLocal;
import br.com.mcampos.ejb.media.MediaSessionBeanLocal;
import br.com.mcampos.jpa.inep.InepDistribution;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepMedia;
import br.com.mcampos.jpa.inep.InepOralTest;
import br.com.mcampos.jpa.inep.InepRevisor;
import br.com.mcampos.jpa.inep.InepSubscription;
import br.com.mcampos.jpa.inep.InepSubscriptionPK;
import br.com.mcampos.jpa.inep.InepTask;
import br.com.mcampos.jpa.inep.InepTaskPK;
import br.com.mcampos.jpa.inep.InepTest;
import br.com.mcampos.jpa.inep.InepTestPK;
import br.com.mcampos.jpa.system.Media;

/**
 * Session Bean implementation class InepTestSessionBean
 */
@Stateless( name = "InepTestSession", mappedName = "InepTestSession" )
@LocalBean
public class InepTestSessionBean extends SimpleSessionBean<InepTest> implements InepTestSession, InepTestSessionLocal
{
	@EJB
	InepSubscriptionSessionLocal subscriptionSession;

	@EJB
	InepTaskSessionLocal taskSession;

	@EJB
	InepPackageSessionLocal eventSession;

	@EJB
	InepOralTestSessionLocal oralSession;

	@EJB
	MediaSessionBeanLocal mediaSession;

	@EJB
	InepMediaSessionLocal inepMediaSession;

	@Override
	protected Class<InepTest> getEntityClass( )
	{
		return InepTest.class;
	}

	@Override
	public boolean insert( InepTestPK key, byte[ ] object )
	{
		InepTest entity = get( key );

		InepSubscription subscription = getSubscription( key );
		if ( subscription == null ) {
			subscription = new InepSubscription( );
			subscription.setId( getSubscriptionPK( key ) );
			subscription = subscriptionSession.merge( subscription );
		}
		InepTask task = getTask( key );
		if ( task == null ) {
			return false;
		}
		if ( entity == null ) {
			entity = new InepTest( );
			entity.setSubscription( subscription );
			entity.setTask( task );
			entity = merge( entity );
		}
		Media media = new Media( );
		media.setName( key.getSubscriptionId( ) + "-" + key.getTaskId( ).toString( ) + ".pdf" );
		media.setFormat( "pdf" );
		media.setMimeType( "text/pdf" );
		media.setObject( object );
		media.setInsertDate( new Date( ) );
		media = mediaSession.persist( media );
		InepMedia inepMedia = new InepMedia( entity.getSubscription( ) );
		inepMedia.setMedia( media );
		inepMedia.setTask( task.getId( ).getId( ) );
		entity.getSubscription( ).add( inepMedia );
		inepMediaSession.merge( inepMedia );
		subscriptionSession.merge( subscription );
		return true;
	}

	@Override
	public boolean insert( InepOralTest entity, boolean createSubscription )
	{
		oralSession.add( entity, createSubscription );
		return true;
	}

	private InepSubscription getSubscription( InepTestPK key )
	{
		return subscriptionSession.get( getSubscriptionPK( key ) );

	}

	private InepSubscriptionPK getSubscriptionPK( InepTestPK key )
	{
		InepSubscriptionPK pk = new InepSubscriptionPK( );
		pk.setCompanyId( key.getCompanyId( ) );
		pk.setEventId( key.getEventId( ) );
		pk.setId( key.getSubscriptionId( ) );
		return pk;
	}

	private InepTask getTask( InepTestPK key )
	{
		return taskSession.get( getTaskPK( key ) );

	}

	private InepTaskPK getTaskPK( InepTestPK key )
	{
		InepTaskPK pk = new InepTaskPK( );
		pk.setCompanyId( key.getCompanyId( ) );
		pk.setEventId( key.getEventId( ) );
		pk.setId( key.getTaskId( ) );
		return pk;
	}

	@Override
	public List<InepTest> getTests( InepEvent event )
	{
		if ( event == null ) {
			return Collections.emptyList( );
		}
		return findByNamedQuery( InepTest.getAllEventTests, event );
	}

	@Override
	public List<InepTest> getTestsWithVariance( InepEvent event )
	{
		if ( event == null ) {
			return Collections.emptyList( );
		}
		return findByNamedQuery( InepTest.getAllTestsWithVariance, event );
	}

	@Override
	public List<InepTest> getTests( InepTask task )
	{
		if ( task == null ) {
			return Collections.emptyList( );
		}
		return findByNamedQuery( InepTest.getAllEventTasks, task );
	}

	@Override
	public List<InepTest> getTests( InepRevisor revisor )
	{
		if ( revisor == null ) {
			return Collections.emptyList( );
		}
		return findByNamedQuery( InepTest.getAllEventTests, revisor );
	}

	@Override
	public List<InepEvent> getAvailableEvents( )
	{
		return eventSession.getAvailable( );
	}

	@Override
	public void setGrade( InepTest test, double grade )
	{
		test.setGrade( new BigDecimal( grade ) );

		if ( !hasPendingTask( test.getSubscription( ) ) ) {
			List<InepTest> tests = findByNamedQuery( InepTest.getAllSubscription, test.getSubscription( ) );
			double dSubscriptionGrade = 0D;
			for ( InepTest item : tests ) {
				dSubscriptionGrade += item.getGrade( ).doubleValue( );
			}
			dSubscriptionGrade /= 4;
			subscriptionSession.setWrittenGrade( test.getSubscription( ), new BigDecimal( dSubscriptionGrade ) );
		}
	}

	public boolean hasPendingTask( InepSubscription s )
	{
		Query query = getEntityManager( ).createNamedQuery( InepDistribution.hasPendingDistribution );
		query.setParameter( 1, s );
		Long result = (Long) query.getSingleResult( );
		return result.longValue( ) > 0;
	}

}
