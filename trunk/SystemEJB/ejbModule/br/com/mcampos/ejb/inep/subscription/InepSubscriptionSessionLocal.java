package br.com.mcampos.ejb.inep.subscription;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.dto.Authentication;
import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.packs.InepPackage;

@Local
public interface InepSubscriptionSessionLocal extends BaseSessionInterface<InepSubscription>
{
	List<InepPackage> getEvents( Authentication auth );

	public List<InepSubscription> getAll( InepPackage event );
}
