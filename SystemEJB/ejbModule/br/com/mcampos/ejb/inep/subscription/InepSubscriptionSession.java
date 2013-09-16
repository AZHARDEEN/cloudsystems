package br.com.mcampos.ejb.inep.subscription;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepSubscription;
import br.com.mcampos.sysutils.dto.PrincipalDTO;

@Remote
public interface InepSubscriptionSession extends BaseCrudSessionInterface<InepSubscription>
{
	List<InepEvent> getEvents( PrincipalDTO auth );

	public List<InepSubscription> getAll( InepEvent event );
}
