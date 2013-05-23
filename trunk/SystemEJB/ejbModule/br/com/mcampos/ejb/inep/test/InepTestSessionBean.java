package br.com.mcampos.ejb.inep.test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.inep.entity.InepOralTest;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepRevisor;
import br.com.mcampos.ejb.inep.entity.InepSubscription;
import br.com.mcampos.ejb.inep.entity.InepSubscriptionPK;
import br.com.mcampos.ejb.inep.entity.InepTask;
import br.com.mcampos.ejb.inep.entity.InepTaskPK;
import br.com.mcampos.ejb.inep.entity.InepTest;
import br.com.mcampos.ejb.inep.entity.InepTestPK;
import br.com.mcampos.ejb.inep.oral.InepOralTestSessionLocal;
import br.com.mcampos.ejb.inep.packs.InepPackageSessionLocal;
import br.com.mcampos.ejb.inep.subscription.InepSubscriptionSessionLocal;
import br.com.mcampos.ejb.inep.task.InepTaskSessionLocal;
import br.com.mcampos.ejb.media.Media;

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

	@Override
	protected Class<InepTest> getEntityClass( )
	{
		return InepTest.class;
	}

	@Override
	public boolean insert( InepTestPK key, byte[ ] object )
	{
		InepTest entity = get( key );

		if ( entity != null ) {
			return false;
		}
		InepSubscription subscription = getSubscription( key );
		if ( subscription == null ) {
			subscription = new InepSubscription( );
			subscription.setId( getSubscriptionPK( key ) );
			subscriptionSession.merge( subscription );
		}
		InepTask task = getTask( key );
		if ( task == null ) {
			return false;
		}
		entity = new InepTest( );
		entity.setSubscription( subscription );
		entity.setTask( task );
		Media media = new Media( );
		media.setName( key.getSubscriptionId( ) + "-" + key.getTaskId( ).toString( ) + ".pdf" );
		media.setFormat( "pdf" );
		media.setMimeType( "text/pdf" );
		media.setObject( object );
		getEntityManager( ).persist( media );
		entity.setMedia( media );
		merge( entity );
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
	public List<InepTest> getTests( InepPackage event )
	{
		if ( event == null ) {
			return Collections.emptyList( );
		}
		return findByNamedQuery( InepTest.getAllEventTests, event );
	}

	@Override
	public List<InepTest> getTestsWithVariance( InepPackage event )
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
	public List<InepPackage> getAvailableEvents( )
	{
		return eventSession.getAvailable( );
	}

	@Override
	public void setGrade( InepTest test, double grade )
	{
		test.setGrade( new BigDecimal( grade ) );

		List<InepTest> tests = findByNamedQuery( InepTest.getAllSubscription, test.getSubscription( ) );
		double dSubscriptionGrade = 0D;
		for ( InepTest item : tests ) {
			dSubscriptionGrade += item.getGrade( ).doubleValue( );
		}
		dSubscriptionGrade /= 4;
		test.getSubscription( ).setWrittenGrade( new BigDecimal( dSubscriptionGrade ) );
	}

}