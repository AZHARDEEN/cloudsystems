package br.com.mcampos.ejb.inep.subscription;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.dto.Authentication;
import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.inep.packs.InepPackage;
import br.com.mcampos.ejb.inep.packs.InepPackageSessionLocal;

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
	public List<InepPackage> getEvents( Authentication auth )
	{
		return getEventSession( ).getAll( auth );
	}

}
