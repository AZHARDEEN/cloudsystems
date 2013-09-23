package br.com.mcampos.ejb.inep.team;

import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.inep.InepDistribution;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepRevisor;
import br.com.mcampos.jpa.inep.InepTask;
import br.com.mcampos.jpa.inep.InepTest;
import br.com.mcampos.sysutils.dto.PrincipalDTO;

@Local
public interface TeamSessionLocal extends BaseCrudSessionInterface<InepRevisor>
{
	List<InepTask> getTasks( @NotNull PrincipalDTO auth );

	List<InepRevisor> getTeam( InepTask task );

	List<InepEvent> getPackages( InepRevisor rev );

	List<InepDistribution> getTests( InepRevisor rev, Integer testStatus );

	InepDistribution getRevision( InepRevisor rev, InepDistribution t );

	InepDistribution updateRevision( InepDistribution rev );

	List<InepEvent> getEvents( PrincipalDTO auth );

	InepRevisor getRevisor( InepEvent event, PrincipalDTO auth );

	List<InepDistribution> getOtherDistributions( InepTest test );

	List<InepRevisor> getOralTeam( InepEvent event );

}
