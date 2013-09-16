package br.com.mcampos.ejb.inep.task;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepTask;
import br.com.mcampos.sysutils.dto.PrincipalDTO;

@Remote
public interface InepTaskSession extends BaseCrudSessionInterface<InepTask>
{
	List<InepEvent> getEvents( PrincipalDTO auth );

	List<InepTask> getAll( InepEvent event );
}