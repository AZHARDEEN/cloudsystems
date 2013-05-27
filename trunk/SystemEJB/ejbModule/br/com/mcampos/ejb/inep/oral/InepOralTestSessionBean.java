package br.com.mcampos.ejb.inep.oral;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.inep.distribution.DistributionStatusSessionLocal;
import br.com.mcampos.ejb.inep.entity.DistributionStatus;
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

	@EJB
	private DistributionStatusSessionLocal statusSession;

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
		entity.setStatus( statusSession.get( DistributionStatus.statusVariance ) );
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

	@Override
	public InepOralTest merge( InepOralTest newEntity )
	{
		newEntity = setStatus( super.merge( newEntity ) );
		return newEntity;
	}

	private InepOralTest setStatus( InepOralTest newEntity )
	{
		if ( newEntity == null )
			return null;
		if ( newEntity.getStatus( ).getId( ).equals( DistributionStatus.statusDistributed ) ) {
			double variance = newEntity.getInterviewGrade( ) != null ? newEntity.getInterviewGrade( ).doubleValue( ) : 0.0;
			variance -= newEntity.getObserverGrade( ) != null ? newEntity.getObserverGrade( ).doubleValue( ) : 0.0;
			variance = Math.abs( variance );
			if ( variance >= 1.5 )
				newEntity.setStatus( statusSession.get( DistributionStatus.statusVariance ) );
		}
		return newEntity;
	}
}
