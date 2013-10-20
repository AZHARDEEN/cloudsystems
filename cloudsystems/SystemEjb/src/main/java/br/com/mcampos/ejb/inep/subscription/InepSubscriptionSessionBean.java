package br.com.mcampos.ejb.inep.subscription;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.inep.distribution.DistributionStatusSessionLocal;
import br.com.mcampos.ejb.inep.oral.InepOralTestSessionLocal;
import br.com.mcampos.ejb.inep.packs.InepPackageSessionLocal;
import br.com.mcampos.jpa.inep.DistributionStatus;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepOralTest;
import br.com.mcampos.jpa.inep.InepSubscription;

/**
 * Session Bean implementation class InepSubscriptionSessionBean
 */
@Stateless( name = "InepSubscriptionSession", mappedName = "InepSubscriptionSession" )
@LocalBean
public class InepSubscriptionSessionBean extends SimpleSessionBean<InepSubscription> implements InepSubscriptionSession,
		InepSubscriptionSessionLocal
{
	@EJB
	InepPackageSessionLocal eventSession;
	@EJB
	InepOralTestSessionLocal oralTestSession;
	@EJB
	DistributionStatusSessionLocal statusSession;

	@Override
	protected Class<InepSubscription> getEntityClass( )
	{
		return InepSubscription.class;
	}

	@Override
	public List<InepSubscription> getAll( InepEvent event )
	{
		List<InepSubscription> list = Collections.emptyList( );

		InepEvent merged = this.getEventSession( ).get( event.getId( ) );
		if ( merged != null ) {
			list = this.findByNamedQuery( InepSubscription.getAllEventSubs, merged );
		}
		return list;
	}

	private InepPackageSessionLocal getEventSession( )
	{
		return this.eventSession;
	}

	@Override
	public List<InepEvent> getEvents( PrincipalDTO auth )
	{
		return this.getEventSession( ).getAll( auth );
	}

	@Override
	public List<InepSubscription> getAll( PrincipalDTO auth, InepEvent event, String subs )
	{
		List<InepSubscription> list = Collections.emptyList( );

		subs = subs.replaceAll( "\\*", "%" );
		subs = subs.replaceAll( "\\?", "_" );
		int nIndex;
		nIndex = subs.indexOf( '%' );
		if ( nIndex < 0 ) {
			nIndex = subs.indexOf( '_' );
		}
		if ( nIndex < 0 ) {
			subs = "%" + subs + "%";
		}
		/*
		 * TODO: verificar se o usuário logado no sistena não está vinculado a um posto aplicador
		 */
		list = this.findByNamedQuery( InepSubscription.getAllEventSubsById, event, subs );
		return list;
	}

	@Override
	public void setOralGrade( InepSubscription s, BigDecimal grade )
	{
		s.setOralGrade( grade );
		if ( s.getWrittenGrade( ) == null ) {
			return;
		}
		this.verifyVariance( s );
	}

	@Override
	public void setWrittenGrade( InepSubscription s, BigDecimal grade )
	{
		s.setWrittenGrade( grade );
		if ( s.getOralGrade( ) == null ) {
			return;
		}
		this.verifyVariance( s );

	}

	private void verifyVariance( InepSubscription s )
	{
		double oral, written;
		boolean bVariance = false;

		if ( s.getOralGrade( ) == null || s.getWrittenGrade( ) == null ) {
			return;
		}
		oral = s.getOralGrade( ).doubleValue( );
		written = s.getWrittenGrade( ).doubleValue( );
		if ( oral >= written ) {
			return;
		}
		if ( oral < 2.0 && written >= 2.0 ) {
			bVariance = true;
		}
		else if ( oral < 2.76 && written >= 2.76 ) {
			bVariance = true;
		}
		else if ( oral < 3.51 && written >= 3.51 ) {
			bVariance = true;
		}
		else if ( oral < 4.26 && written >= 4.26 ) {
			bVariance = true;
		}
		if ( bVariance ) {
			InepOralTest oralTest = this.oralTestSession.get( s );
			if ( oralTest != null ) {
				oralTest.setStatus( this.statusSession.get( DistributionStatus.statusVariance ) );
				if ( oralTest.getVarianceStatus( ).equals( 0 ) ) {
					oralTest.setVarianceStatus( 11 );
				}
				else {
					oralTest.setVarianceStatus( oralTest.getVarianceStatus( ) + 10 );
				}
			}
		}
	}
}