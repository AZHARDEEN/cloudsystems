package br.com.mcampos.ejb.inep.test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.inep.packs.InepPackageSessionLocal;
import br.com.mcampos.ejb.inep.subscription.InepSubscriptionSessionLocal;
import br.com.mcampos.ejb.inep.task.InepTaskSessionLocal;
import br.com.mcampos.jpa.inep.InepDistribution;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepRevisor;
import br.com.mcampos.jpa.inep.InepSubscription;
import br.com.mcampos.jpa.inep.InepTask;
import br.com.mcampos.jpa.inep.InepTest;

/**
 * Session Bean implementation class InepTestSessionBean
 */
@Stateless( name = "InepTestSession", mappedName = "InepTestSession" )
@LocalBean
public class InepTestSessionBean extends SimpleSessionBean<InepTest> implements InepTestSession, InepTestSessionLocal
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7087995828301656886L;

	@EJB
	InepSubscriptionSessionLocal subscriptionSession;

	@EJB
	InepTaskSessionLocal taskSession;

	@EJB
	InepPackageSessionLocal eventSession;

	@Override
	protected Class<InepTest> getEntityClass( )
	{
		return InepTest.class;
	}

	@Override
	public List<InepTest> getTests( InepEvent event )
	{
		if ( event == null ) {
			return Collections.emptyList( );
		}
		return this.findByNamedQuery( InepTest.getAllEventTests, event );
	}

	@Override
	public List<InepTest> getTestsWithVariance( InepEvent event )
	{
		if ( event == null ) {
			return Collections.emptyList( );
		}
		return this.findByNamedQuery( InepTest.getAllTestsWithVariance, event );
	}

	@Override
	public List<InepTest> getTests( InepTask task )
	{
		if ( task == null ) {
			return Collections.emptyList( );
		}
		return this.findByNamedQuery( InepTest.getAllEventTasks, task );
	}

	@Override
	public List<InepTest> getTests( InepRevisor revisor )
	{
		if ( revisor == null ) {
			return Collections.emptyList( );
		}
		return this.findByNamedQuery( InepTest.getAllEventTests, revisor );
	}

	@Override
	public List<InepEvent> getAvailableEvents( PrincipalDTO auth )
	{
		return this.eventSession.getAvailable( auth );
	}

	@Override
	public void setGrade( InepTest test, double grade )
	{
		test.setGrade( new BigDecimal( grade ) );

		if ( !this.hasPendingTask( test.getSubscription( ) ) ) {
			List<InepTest> tests = this.findByNamedQuery( InepTest.getAllSubscription, test.getSubscription( ) );
			double dSubscriptionGrade = 0D;
			for ( InepTest item : tests ) {
				dSubscriptionGrade += item.getGrade( ).doubleValue( );
			}
			dSubscriptionGrade /= 4;
			this.subscriptionSession.setWrittenGrade( test.getSubscription( ), new BigDecimal( dSubscriptionGrade ) );
		}
	}

	public boolean hasPendingTask( InepSubscription s )
	{
		Query query = this.getEntityManager( ).createNamedQuery( InepDistribution.hasPendingDistribution );
		query.setParameter( 1, s );
		Long result = (Long) query.getSingleResult( );
		return result.longValue( ) > 0;
	}

}
