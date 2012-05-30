package br.com.mcampos.ejb.inep.revisor;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.dto.Authentication;
import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.packs.InepPackage;
import br.com.mcampos.ejb.inep.task.InepTask;

@Local
public interface InepRevisorSessionLocal extends BaseSessionInterface<InepRevisor>
{
	public List<InepPackage> getEvents( Authentication auth );

	public List<InepTask> getTasks( InepPackage event );

	public List<InepRevisor> getAll( InepPackage p );

	public List<InepRevisor> getAll( InepTask p );

	public InepRevisor get( InepPackage event, Authentication auth );
}
