package br.com.mcampos.ejb.inep.team;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.dto.Authentication;
import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.entity.InepDistribution;
import br.com.mcampos.ejb.inep.packs.InepPackage;
import br.com.mcampos.ejb.inep.revisor.InepRevisor;
import br.com.mcampos.ejb.inep.task.InepTask;
import br.com.mcampos.ejb.inep.test.InepTest;

@Local
public interface TeamSessionLocal extends BaseSessionInterface<InepRevisor>
{
	List<InepTask> getTasks( );
	List<InepRevisor> getTeam( InepTask task );

	void distribute( );

	List<InepPackage> getPackages( InepRevisor rev );

	List<InepDistribution> getTests( InepPackage event, Authentication auth );

	InepDistribution getRevision( InepRevisor rev, InepDistribution t );

	InepDistribution updateRevision( InepDistribution rev );

	List<InepPackage> getEvents( Authentication auth );

	InepRevisor getRevisor( InepPackage event, Authentication auth );

	List<InepDistribution> getOtherDistributions( InepTest test );


}
