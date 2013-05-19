package br.com.mcampos.ejb.inep.oral;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.inep.entity.InepOralTest;
import br.com.mcampos.ejb.inep.entity.InepSubscription;
import br.com.mcampos.ejb.inep.entity.InepSubscriptionPK;
import br.com.mcampos.ejb.inep.subscription.InepSubscriptionSessionLocal;

/**
 * Session Bean implementation class InepOralTestSessionBean
 */
@Stateless( mappedName = "InepOralTestSession", name = "InepOralTestSession" )
@LocalBean
public class InepOralTestSessionBean extends SimpleSessionBean<InepOralTest> implements InepOralTestSession, InepOralTestSessionLocal
{
	@EJB
	private InepSubscriptionSessionLocal subscriptionSession;

	@Override
	protected Class<InepOralTest> getEntityClass( )
	{
		return InepOralTest.class;
	}

	@Override
	public void add( InepOralTest entity, boolean createSubscription )
	{
		InepSubscription s = getSubscription( entity, createSubscription );
		entity.setSubscription( s );
		entity = merge( entity );
		if ( entity.getFinalGrade( ) != null ) {
			s.setOralGrade( entity.getFinalGrade( ) );
		}
	}

	private InepSubscription getSubscription( InepOralTest test, boolean create )
	{
		InepSubscriptionPK key = new InepSubscriptionPK( );
		key.setEventId( test.getId( ).getEventId( ) );
		key.setCompanyId( test.getId( ).getUserId( ) );
		key.setId( test.getId( ).getSubscriptionId( ) );
		InepSubscription entity = subscriptionSession.get( key );
		if ( entity == null && create ) {
			entity = new InepSubscription( );
			entity.setId( key );
			entity = subscriptionSession.merge( entity );
		}
		return entity;
	}

}
