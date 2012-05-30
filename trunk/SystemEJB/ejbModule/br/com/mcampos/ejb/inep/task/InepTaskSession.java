package br.com.mcampos.ejb.inep.task;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.dto.Authentication;
import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.packs.InepPackage;

@Remote
public interface InepTaskSession extends BaseSessionInterface<InepTask>
{
	List<InepPackage> getEvents( Authentication auth );

	List<InepTask> getAll( InepPackage event );
}
