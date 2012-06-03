package br.com.mcampos.ejb.inep.test;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.inep.entity.InepSubscription;
import br.com.mcampos.ejb.inep.entity.InepSubscriptionPK;
import br.com.mcampos.ejb.inep.entity.InepTask;
import br.com.mcampos.ejb.inep.entity.InepTaskPK;
import br.com.mcampos.ejb.inep.entity.InepTest;
import br.com.mcampos.ejb.inep.entity.InepTestPK;
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
			this.subscriptionSession.merge( subscription );
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

	private InepSubscription getSubscription( InepTestPK key )
	{
		return this.subscriptionSession.get( getSubscriptionPK( key ) );

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
		return this.taskSession.get( getTaskPK( key ) );

	}

	private InepTaskPK getTaskPK( InepTestPK key )
	{
		InepTaskPK pk = new InepTaskPK( );
		pk.setCompanyId( key.getCompanyId( ) );
		pk.setEventId( key.getEventId( ) );
		pk.setId( key.getTaskId( ) );
		return pk;
	}

}
