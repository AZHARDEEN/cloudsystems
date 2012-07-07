package br.com.mcampos.ejb.inep.subscription;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepSubscription;
import br.com.mcampos.ejb.inep.packs.InepPackageSessionLocal;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;

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
		return this.eventSession;
	}

	@Override
	public List<InepPackage> getEvents( Collaborator auth )
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

}
