package br.com.mcampos.ejb.inep;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.BaseSessionBean;
import br.com.mcampos.ejb.inep.packs.InepPackageSessionLocal;
import br.com.mcampos.ejb.inep.subscription.InepSubscriptionSessionLocal;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepSubscription;
import br.com.mcampos.sysutils.SysUtils;

/**
 * Brief Session Bean implementation class StationSessionBean Esta classe é o facade das telas do posto aplicador.
 */
@Stateless( name = "StationSession", mappedName = "StationSession" )
public class StationSessionBean extends BaseSessionBean implements StationSession
{
	private static final long serialVersionUID = 7677179078573223942L;
	@EJB
	private InepPackageSessionLocal eventSession;

	@EJB
	private InepSubscriptionSessionLocal subscriptionSession;

	@Override
	/**
	 * Brief Obtem o evento ativo e corrente para a empresa atual
	 * É esperado que exista apenas um evento ativo no período
	 * @Param auth Todo e qualquer tipo principal é o usuário logado no sistema.
	 */
	public InepEvent getCurrentEvent( PrincipalDTO auth )
	{
		List<InepEvent> events;

		events = this.eventSession.getAvailable( auth );
		if ( SysUtils.isEmpty( events ) ) {
			return null;
		}
		if ( events.size( ) > 1 ) {
			throw new RuntimeException( "Too Many Events" );
		}
		return events.get( 0 );
	}

	@Override
	public List<InepSubscription> getSubscriptions( PrincipalDTO auth, InepEvent evt, String part )
	{
		return this.subscriptionSession.getAll( auth, evt, part );
	}
}
