package br.com.mcampos.ejb.inep.task;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepTask;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;

@Remote
public interface InepTaskSession extends BaseSessionInterface<InepTask>
{
	List<InepPackage> getEvents( Collaborator auth );

	List<InepTask> getAll( InepPackage event );
}
