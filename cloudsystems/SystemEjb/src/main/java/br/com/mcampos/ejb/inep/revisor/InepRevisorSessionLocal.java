package br.com.mcampos.ejb.inep.revisor;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepRevisor;
import br.com.mcampos.jpa.inep.InepTask;
import br.com.mcampos.jpa.user.Collaborator;

@Local
public interface InepRevisorSessionLocal extends BaseCrudSessionInterface<InepRevisor>
{
	public List<InepEvent> getEvents( PrincipalDTO auth );

	public List<InepTask> getTasks( InepEvent event );

	public List<InepRevisor> getAll( InepEvent p );

	public List<InepRevisor> getAll( InepTask p );

	public InepRevisor get( InepEvent event, PrincipalDTO auth );

	public InepRevisor get( InepEvent event, Collaborator c );

	public List<InepRevisor> getOralCoordinator( InepEvent event );
}
