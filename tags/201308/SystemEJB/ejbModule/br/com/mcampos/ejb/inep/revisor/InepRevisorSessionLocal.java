package br.com.mcampos.ejb.inep.revisor;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepRevisor;
import br.com.mcampos.ejb.inep.entity.InepTask;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;

@Local
public interface InepRevisorSessionLocal extends BaseSessionInterface<InepRevisor>
{
	public List<InepPackage> getEvents( Collaborator auth );

	public List<InepTask> getTasks( InepPackage event );

	public List<InepRevisor> getAll( InepPackage p );

	public List<InepRevisor> getAll( InepTask p );

	public InepRevisor get( InepPackage event, Collaborator auth );

	public List<InepRevisor> getOralCoordinator( InepPackage event );
}
