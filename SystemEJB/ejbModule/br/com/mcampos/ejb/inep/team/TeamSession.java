package br.com.mcampos.ejb.inep.team;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.dto.inep.InepAnaliticoCorrecao;
import br.com.mcampos.dto.inep.InepTaskCounters;
import br.com.mcampos.dto.inep.relatorios.BaseSubscriptionDTO;
import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.entity.InepDistribution;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepRevisor;
import br.com.mcampos.ejb.inep.entity.InepTask;
import br.com.mcampos.ejb.inep.entity.InepTest;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;

@Remote
public interface TeamSession extends BaseSessionInterface<InepRevisor>
{
	List<InepTask> getTasks( );

	List<InepRevisor> getTeam( InepTask task );

	void distribute( );

	List<InepPackage> getPackages( InepRevisor rev );

	List<InepDistribution> getTests( InepRevisor rev, Integer testStatus );

	InepDistribution getRevision( InepRevisor rev, InepDistribution t );

	InepDistribution updateRevision( InepDistribution rev );

	List<InepPackage> getEvents( Collaborator auth );

	InepRevisor getRevisor( InepPackage event, Collaborator auth );

	byte[ ] getMedia( InepTest test );

	List<InepDistribution> getOtherDistributions( InepTest test );

	InepTaskCounters getCounters( InepRevisor rev );

	public List<InepAnaliticoCorrecao> getAnaliticoCorrecao( InepPackage event );

	public List<InepAnaliticoCorrecao> getAnaliticoCorrecao( InepRevisor revisor );

	public List<InepAnaliticoCorrecao> getAnaliticoCorrecao( InepTask task );

	public List<BaseSubscriptionDTO> report( InepPackage event, Integer report );
}
