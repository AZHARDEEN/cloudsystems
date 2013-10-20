package br.com.mcampos.ejb.inep.subscription;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepSubscription;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;

@Remote
public interface InepSubscriptionSession extends BaseSessionInterface<InepSubscription>
{
	List<InepPackage> getEvents( Collaborator auth );

	public List<InepSubscription> getAll( InepPackage event );
}