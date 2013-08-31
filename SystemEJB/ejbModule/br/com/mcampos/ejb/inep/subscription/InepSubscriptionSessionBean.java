package br.com.mcampos.ejb.inep.subscription;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.inep.distribution.DistributionStatusSessionLocal;
import br.com.mcampos.ejb.inep.oral.InepOralTestSessionLocal;
import br.com.mcampos.ejb.inep.packs.InepPackageSessionLocal;
import br.com.mcampos.entity.inep.DistributionStatus;
import br.com.mcampos.entity.inep.InepOralTest;
import br.com.mcampos.entity.inep.InepPackage;
import br.com.mcampos.entity.inep.InepSubscription;
import br.com.mcampos.utils.dto.PrincipalDTO;

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
	public List<InepSubscription> getAll( InepPackage event )
	{
		List<InepSubscription> list = Collections.emptyList( );

		InepPackage merged = getEventSession( ).get( event.getId( ) );
		if ( merged != null ) {
			list = findByNamedQuery( InepSubscription.getAllEventSubs, merged );
		}
		return list;
	}

	private InepPackageSessionLocal getEventSession( )
	{
		return eventSession;
	}

	@Override
	public List<InepPackage> getEvents( PrincipalDTO auth )
	{
		return getEventSession( ).getAll( auth );
	}

	@Override
	public List<InepSubscription> getAll( InepPackage event, String subs )
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
		list = findByNamedQuery( InepSubscription.getAllEventSubsById, event, subs );
		return list;
	}

	@Override
	public void setOralGrade( InepSubscription s, BigDecimal grade )
	{
		s.setOralGrade( grade );
		if ( s.getWrittenGrade( ) == null )
			return;
		verifyVariance( s );
	}

	@Override
	public void setWrittenGrade( InepSubscription s, BigDecimal grade )
	{
		s.setWrittenGrade( grade );
		if ( s.getOralGrade( ) == null )
			return;
		verifyVariance( s );

	}

	private void verifyVariance( InepSubscription s )
	{
		double oral, written;
		boolean bVariance = false;

		if ( s.getOralGrade( ) == null || s.getWrittenGrade( ) == null )
			return;
		oral = s.getOralGrade( ).doubleValue( );
		written = s.getWrittenGrade( ).doubleValue( );
		if ( oral >= written )
			return;
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
			if ( oralTest != null ) {
				oralTest.setStatus( statusSession.get( DistributionStatus.statusVariance ) );
				if ( oralTest.getVarianceStatus( ).equals( 0 ) )
					oralTest.setVarianceStatus( 11 );
				else
					oralTest.setVarianceStatus( oralTest.getVarianceStatus( ) + 10 );
			}
		}
	}
}
