package br.com.mcampos.ejb.inep;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.inep.media.InepMediaSessionLocal;
import br.com.mcampos.ejb.inep.oral.InepOralTestSessionLocal;
import br.com.mcampos.ejb.inep.packs.InepPackageSessionLocal;
import br.com.mcampos.ejb.inep.subscription.InepSubscriptionSessionLocal;
import br.com.mcampos.ejb.inep.task.InepTaskSessionLocal;
import br.com.mcampos.ejb.inep.test.InepTestSessionLocal;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepOralTest;
import br.com.mcampos.jpa.inep.InepSubscription;
import br.com.mcampos.jpa.inep.InepSubscriptionPK;
import br.com.mcampos.jpa.inep.InepTask;
import br.com.mcampos.jpa.inep.InepTaskPK;
import br.com.mcampos.jpa.inep.InepTest;
import br.com.mcampos.jpa.inep.InepTestPK;

/**
 * Session Bean implementation class InepLoaderSessionBean
 */
@Stateless( mappedName = "InepLoaderSession" )
public class InepLoaderSessionBean implements InepLoaderSession
{
	@EJB
	private InepSubscriptionSessionLocal subscriptionSession;

	@EJB
	private InepPackageSessionLocal eventSession;

	@EJB
	private InepTestSessionLocal testSession;

	@EJB
	private InepTaskSessionLocal taskSession;

	@EJB
	private InepMediaSessionLocal inepMediaSession;

	@EJB
	private InepOralTestSessionLocal oralSession;

	@Override
	public boolean insert( InepTestPK key, byte[ ] object )
	{

		InepTask task = this.getTask( key );
		if ( task == null ) {
			/*
			 * Task must exists
			 */
			return false;
		}
		InepSubscription subscription = this.getSubscription( key );
		InepTest entity = this.getTest( key, subscription, task );
		this.inepMediaSession.addPDF( entity, subscription.getId( ) + "_" + entity.getId( ).getTaskId( ), object );
		return true;
	}

	private InepSubscription getSubscription( InepTestPK key )
	{
		if ( key == null || key.getCompanyId( ) == null || key.getEventId( ) == null || key.getSubscriptionId( ) == null ) {
			return null;
		}
		InepSubscriptionPK k = new InepSubscriptionPK( );
		k.setCompanyId( key.getCompanyId( ) );
		k.setEventId( key.getEventId( ) );
		k.setId( key.getSubscriptionId( ) );
		InepSubscription subscription = this.subscriptionSession.get( key );
		if ( subscription == null ) {
			subscription = new InepSubscription( );
			subscription.setId( k );
			subscription = this.subscriptionSession.add( subscription );
		}
		return subscription;
	}

	private InepTest getTest( InepTestPK key, InepSubscription subscription, InepTask task )
	{
		InepTest entity = this.testSession.get( key );
		if ( entity == null ) {
			entity = new InepTest( );
			entity.setSubscription( subscription );
			entity.setTask( task );
			entity = this.testSession.add( entity );
		}
		return entity;
	}

	private InepTask getTask( InepTestPK key )
	{
		InepTaskPK k = new InepTaskPK( );
		k.setCompanyId( key.getCompanyId( ) );
		k.setEventId( key.getEventId( ) );
		k.setId( key.getTaskId( ) );
		return this.taskSession.get( key );
	}

	@Override
	public List<InepEvent> getAvailableEvents( )
	{
		return this.eventSession.getAvailable( );
	}

	@Override
	public boolean insert( InepOralTest entity, boolean createSubscription )
	{
		this.oralSession.add( entity, createSubscription );
		return true;
	}

}
