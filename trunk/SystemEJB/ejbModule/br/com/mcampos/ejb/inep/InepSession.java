package br.com.mcampos.ejb.inep;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.dto.inep.CorretorDTO;
import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepRevisor;
import br.com.mcampos.jpa.inep.InepTask;

@Remote
public interface InepSession extends BaseCrudSessionInterface<InepTask>
{
	boolean loadCorretore( CorretorDTO dto );

	void distribute( InepTask task );

	List<InepTask> getTasks( InepEvent event );

	InepRevisor add( InepTask task, String name, String email, String cpf, boolean coordenador );

	public InepRevisor add( InepEvent event, Integer task, String name, String email, String cpf, Integer type );
}
