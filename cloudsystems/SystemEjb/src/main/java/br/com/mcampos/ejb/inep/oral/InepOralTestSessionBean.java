package br.com.mcampos.ejb.inep.oral;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.inep.distribution.DistributionStatusSessionLocal;
import br.com.mcampos.ejb.inep.subscription.InepSubscriptionSessionLocal;
import br.com.mcampos.jpa.inep.DistributionStatus;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepOralTest;
import br.com.mcampos.jpa.inep.InepSubscription;
import br.com.mcampos.jpa.inep.InepSubscriptionPK;

/**
 * Session Bean implementation class InepOralTestSessionBean
 */
@Stateless( mappedName = "InepOralTestSession", name = "InepOralTestSession" )
@LocalBean
public class InepOralTestSessionBean extends SimpleSessionBean<InepOralTest> implements InepOralTestSession, InepOralTestSessionLocal
{
	private static final long serialVersionUID = -4861196998777088222L;

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
	public InepOralTest add( InepOralTest entity, boolean createSubscription )
	{
		InepSubscription s = this.getSubscription( entity, createSubscription );
		entity.setSubscription( s );
		entity.setStatusId( 1 );
		entity = this.merge( entity );
		if ( entity.getFinalGrade( ) != null && entity.getStatusId( ).equals( DistributionStatus.statusDistributed ) ) {
			this.subscriptionSession.setOralGrade( s, entity.getFinalGrade( ) );
		}
		if ( entity.getStatusId( ).equals( DistributionStatus.statusDistributed ) ) {
			entity.setVarianceStatus( 0 );
		}
		else {
			entity.setVarianceStatus( 1 );
		}
		return entity;
	}

	private InepSubscription getSubscription( InepOralTest test, boolean create )
	{
		InepSubscriptionPK key = new InepSubscriptionPK( );
		key.setEventId( test.getId( ).getEventId( ) );
		key.setCompanyId( test.getId( ).getCompanyId( ) );
		key.setId( test.getId( ).getSubscriptionId( ) );
		InepSubscription entity = this.subscriptionSession.get( key );
		if ( entity == null && create ) {
			entity = new InepSubscription( );
			entity.setId( key );
			entity = this.subscriptionSession.merge( entity );
		}
		return entity;
	}

	@Override
	public InepOralTest merge( InepOralTest newEntity )
	{
		newEntity = this.setStatus( super.merge( newEntity ) );
		return newEntity;
	}

	@Override
	public InepOralTest setStatus( InepOralTest newEntity )
	{
		if ( newEntity == null ) {
			return null;
		}
		if ( newEntity.getStatusId( ).equals( 1 )
				&& newEntity.getInterviewGrade( ) != null && newEntity.getObserverGrade( ) != null ) {
			double grade1, grade2;

			grade1 = newEntity.getInterviewGrade( ).doubleValue( );
			grade2 = newEntity.getObserverGrade( ).doubleValue( );
			double variance = Math.abs( grade1 - grade2 );
			if ( variance >= 1.5 || ( grade1 >= 2.0 && grade2 < 2.0 ) || ( grade2 >= 2.0 && grade1 < 2.0 ) ) {
				newEntity.setStatusId( 2 );
			}
		}
		return newEntity;
	}

	@Override
	public List<InepOralTest> getVarianceOralOnly( InepEvent pack )
	{
		return this.findByNamedQuery( InepOralTest.getVarianceOralOnly, pack );
	}

	@Override
	public void setAgreementGrade( InepOralTest test, Integer grade, boolean isCoordinator )
	{
		int nIndex = 0;
		if ( test.getStatusId( ).compareTo( 10 ) > 0 ) {
			nIndex = 10;
		}
		test.setStatusId( 4 + nIndex );
		if ( isCoordinator == false ) {
			test.getSubscription( ).setAgreementGrade( new BigDecimal( grade ) );
			test.setAgreementGrade( grade );
			double variance;
			if ( nIndex == 0 ) {
				variance = test.getFinalGrade( ).doubleValue( );
			}
			else {
				variance = grade;
			}
			if ( test.getSubscription( ).getWrittenGrade( ) != null && grade >= test.getSubscription( ).getWrittenGrade( ).doubleValue( ) ) {
				// Se a nota da prova oral for maior que a nota da prova escrita, nao ha discrepancia
				variance = 0;
			}
			else {
				variance = Math.abs( variance - ( (double) grade ) );
			}
			if ( nIndex > 0 && test.getAgreement2Grade( ) != null ) {
				variance = 0;
			}
			if ( variance >= 1.5 ) {
				test.setStatusId( 5 + nIndex );
			}
		}
		else {
			test.setAgreement2Grade( new BigDecimal( grade ) );
			test.getSubscription( ).setAgreementGrade( new BigDecimal( grade ) );
			test.setStatusId( 6 + nIndex );
		}
		this.subscriptionSession.setOralGrade( test.getSubscription( ), new BigDecimal( grade ) );
	}

	@Override
	public InepOralTest get( InepSubscription s )
	{
		return this.getByNamedQuery( InepOralTest.getBySubscription, s );
	}

	@Override
	public List<InepOralTest> getVarianceWrittenOnly( InepEvent pack )
	{
		return this.findByNamedQuery( InepOralTest.getVarianceOralWritten, pack );
	}
}
