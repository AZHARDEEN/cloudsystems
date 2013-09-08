package br.com.mcampos.ejb.inep.revisor;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.inep.InepEvent;
import br.com.mcampos.entity.inep.InepRevisor;
import br.com.mcampos.entity.inep.InepTask;
import br.com.mcampos.utils.dto.PrincipalDTO;

@Remote
public interface InepRevisorSession extends BaseCrudSessionInterface<InepRevisor>
{
	public List<InepEvent> getEvents( PrincipalDTO auth );

	public List<InepTask> getTasks( InepEvent event );

	public List<InepRevisor> getAll( InepEvent p );

	public List<InepRevisor> getAll( InepTask p );

	public InepRevisor get( InepEvent event, PrincipalDTO auth );

}
