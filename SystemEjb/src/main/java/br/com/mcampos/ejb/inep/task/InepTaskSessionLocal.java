package br.com.mcampos.ejb.inep.task;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepTask;

@Local
public interface InepTaskSessionLocal extends BaseCrudSessionInterface<InepTask>
{
	List<InepEvent> getEvents( PrincipalDTO auth );

	List<InepTask> getAll( InepEvent event );

	void remove( InepEvent event );
}
