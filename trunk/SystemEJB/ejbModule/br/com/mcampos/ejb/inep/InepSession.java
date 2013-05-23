package br.com.mcampos.ejb.inep;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.dto.inep.CorretorDTO;
import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepRevisor;
import br.com.mcampos.ejb.inep.entity.InepTask;

@Remote
public interface InepSession extends BaseSessionInterface<InepTask>
{
	boolean loadCorretore( CorretorDTO dto );

	void distribute( InepTask task );

	List<InepTask> getTasks( InepPackage event );

	InepRevisor add( InepTask task, String name, String email, String cpf, boolean coordenador );
}