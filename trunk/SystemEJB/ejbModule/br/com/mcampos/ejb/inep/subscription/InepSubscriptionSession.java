package br.com.mcampos.ejb.inep.subscription;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.inep.InepEvent;
import br.com.mcampos.entity.inep.InepSubscription;
import br.com.mcampos.utils.dto.PrincipalDTO;

@Remote
public interface InepSubscriptionSession extends BaseCrudSessionInterface<InepSubscription>
{
	List<InepEvent> getEvents( PrincipalDTO auth );

	public List<InepSubscription> getAll( InepEvent event );
}
