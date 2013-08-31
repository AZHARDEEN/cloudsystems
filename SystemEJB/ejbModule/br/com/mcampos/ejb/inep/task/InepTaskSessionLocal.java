package br.com.mcampos.ejb.inep.task;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepTask;
import br.com.mcampos.utils.dto.PrincipalDTO;

@Local
public interface InepTaskSessionLocal extends BaseSessionInterface<InepTask>
{
	List<InepPackage> getEvents( PrincipalDTO auth );

	List<InepTask> getAll( InepPackage event );
}
