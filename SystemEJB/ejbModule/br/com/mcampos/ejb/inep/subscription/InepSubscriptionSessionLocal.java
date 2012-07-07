package br.com.mcampos.ejb.inep.subscription;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepSubscription;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;

@Local
public interface InepSubscriptionSessionLocal extends BaseSessionInterface<InepSubscription>
{
	List<InepPackage> getEvents( Collaborator auth );

	public List<InepSubscription> getAll( InepPackage event );

	public List<InepSubscription> getAll( InepPackage event, String subs );

}
