package br.com.mcampos.ejb.inep.subscription;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.dto.Authentication;
import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.packs.InepPackage;

@Remote
public interface InepSubscriptionSession extends BaseSessionInterface<InepSubscription>
{
	List<InepPackage> getEvents( Authentication auth );

	public List<InepSubscription> getAll( InepPackage event );
}
