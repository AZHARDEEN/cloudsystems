package br.com.mcampos.ejb.inep.task;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.inep.InepEvent;
import br.com.mcampos.entity.inep.InepTask;
import br.com.mcampos.utils.dto.PrincipalDTO;

@Remote
public interface InepTaskSession extends BaseCrudSessionInterface<InepTask>
{
	List<InepEvent> getEvents( PrincipalDTO auth );

	List<InepTask> getAll( InepEvent event );
}
