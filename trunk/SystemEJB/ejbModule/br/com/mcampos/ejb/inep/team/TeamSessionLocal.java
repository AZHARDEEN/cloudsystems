package br.com.mcampos.ejb.inep.team;

import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.inep.InepDistribution;
import br.com.mcampos.entity.inep.InepPackage;
import br.com.mcampos.entity.inep.InepRevisor;
import br.com.mcampos.entity.inep.InepTask;
import br.com.mcampos.entity.inep.InepTest;
import br.com.mcampos.utils.dto.PrincipalDTO;

@Local
public interface TeamSessionLocal extends BaseSessionInterface<InepRevisor>
{
	List<InepTask> getTasks( @NotNull PrincipalDTO auth );

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
