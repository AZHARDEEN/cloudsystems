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

		InepEvent merged = getEventSession( ).get( event.getId( ) );
		if ( merged != null ) {
			list = this.findByNamedQuery( InepSubscription.getAllEventSubs, merged );
		}
		return list;
	}

	private InepPackageSessionLocal getEventSession( )
	{
		return eventSession;
	}

	@Override
	public List<InepEvent> getEvents( PrincipalDTO auth )
	{
		return getEventSession( ).getAll( auth );
	}

	private String treatLikeArgument( String subs )
	{
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
		return subs;
	}

	@Override
	public List<InepSubscription> getAll( PrincipalDTO auth, InepEvent event, String subs )
	{
		List<InepSubscription> list = Collections.emptyList( );

		subs = treatLikeArgument( subs );
		/*
		 * TODO: verificar se o usuário logado no sistena não está vinculado a um posto aplicador
		 */
		list = this.findByNamedQuery( InepSubscription.getAllEventSubsById, event, subs );
		return list;
	}

	@Override
	public List<InepSubscription> getAll( PrincipalDTO auth, InepEvent event, String subs, List<Integer> stations )
	{
		List<InepSubscription> list = Collections.emptyList( );

		subs = treatLikeArgument( subs );
		/*
		 * TODO: verificar se o usuário logado no sistena não está vinculado a um posto aplicador
		 */
		list = this.findByNamedQuery( InepSubscription.getAllEventSubsByIdAndStation, event, subs, stations );
		return list;
	}

	@Override
	public void setOralGrade( InepSubscription s, BigDecimal grade )
	{
		s.setOralGrade( grade );
		s.setStatus( 2 );
		if ( s.getWrittenGrade( ) == null ) {
			return;
		}
		InepOralTest oralTest = oralTestSession.get( s );
		if ( oralTest == null ) {
			return;
		}
		if ( oralTest.getStatusId( ).equals( 5 ) ) {
			verifyVariance( s );
		}
	}

	@Override
	public void setWrittenGrade( InepSubscription s, BigDecimal grade )
	{
		s.setWrittenGrade( grade );
		s.setStatus( 2 );
		if ( s.getOralGrade( ) == null ) {
			return;
		}
		verifyVariance( s );

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
			InepOralTest oralTest = oralTestSession.get( s );
			if ( canPutInVariance( oralTest ) ) {
				oralTest.setStatusId( 12 );
			}
		}
	}

	private boolean canPutInVariance( InepOralTest o )
	{
		if ( o == null ) {
			return false;
		}
		return ( o.getStatusId( ).equals( 1 ) || o.getStatusId( ).equals( 4 ) || o.getStatusId( ).equals( 6 ) );
	}

	@Override
	public InepSubscription get( String id )
	{
		return this.getByNamedQuery( InepSubscription.getBySubscriptionId, id );
	}

}
