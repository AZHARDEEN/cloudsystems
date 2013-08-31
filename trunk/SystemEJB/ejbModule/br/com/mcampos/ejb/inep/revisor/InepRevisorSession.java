package br.com.mcampos.ejb.inep.revisor;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepRevisor;
import br.com.mcampos.ejb.inep.entity.InepTask;
import br.com.mcampos.utils.dto.PrincipalDTO;

@Remote
public interface InepRevisorSession extends BaseSessionInterface<InepRevisor>
{
	public List<InepPackage> getEvents( PrincipalDTO auth );

	public List<InepTask> getTasks( InepPackage event );

	public List<InepRevisor> getAll( InepPackage p );

	public List<InepRevisor> getAll( InepTask p );

	public InepRevisor get( InepPackage event, PrincipalDTO auth );

}
