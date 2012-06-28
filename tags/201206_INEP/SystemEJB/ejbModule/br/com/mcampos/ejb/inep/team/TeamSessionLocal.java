package br.com.mcampos.ejb.inep.team;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.entity.InepDistribution;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepRevisor;
import br.com.mcampos.ejb.inep.entity.InepTask;
import br.com.mcampos.ejb.inep.entity.InepTest;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;

@Local
public interface TeamSessionLocal extends BaseSessionInterface<InepRevisor>
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

	List<InepDistribution> getOtherDistributions( InepTest test );

}
