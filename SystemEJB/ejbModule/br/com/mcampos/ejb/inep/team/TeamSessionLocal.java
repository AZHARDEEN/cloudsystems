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
import br.com.mcampos.utils.dto.PrincipalDTO;

@Local
public interface TeamSessionLocal extends BaseSessionInterface<InepRevisor>
{
	List<InepTask> getTasks( );

	List<InepRevisor> getTeam( InepTask task );

	List<InepPackage> getPackages( InepRevisor rev );

	List<InepDistribution> getTests( InepRevisor rev, Integer testStatus );

	InepDistribution getRevision( InepRevisor rev, InepDistribution t );

	InepDistribution updateRevision( InepDistribution rev );

	List<InepPackage> getEvents( PrincipalDTO auth );

	InepRevisor getRevisor( InepPackage event, PrincipalDTO auth );

	List<InepDistribution> getOtherDistributions( InepTest test );

	List<InepRevisor> getOralTeam( InepPackage event );

}
