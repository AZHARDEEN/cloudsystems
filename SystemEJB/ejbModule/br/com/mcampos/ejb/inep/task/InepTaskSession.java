package br.com.mcampos.ejb.inep.task;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.inep.InepPackage;
import br.com.mcampos.entity.inep.InepTask;
import br.com.mcampos.utils.dto.PrincipalDTO;

@Remote
public interface InepTaskSession extends BaseSessionInterface<InepTask>
{
	List<InepPackage> getEvents( PrincipalDTO auth );

	List<InepTask> getAll( InepPackage event );
}
