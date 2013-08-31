package br.com.mcampos.ejb.inep.team;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.dto.inep.InepAnaliticoCorrecao;
import br.com.mcampos.dto.inep.InepTaskCounters;
import br.com.mcampos.dto.inep.reporting.BaseSubscriptionDTO;
import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.user.company.collaborator.UserPropertyInterface;
import br.com.mcampos.entity.inep.InepDistribution;
import br.com.mcampos.entity.inep.InepOralDistribution;
import br.com.mcampos.entity.inep.InepOralTest;
import br.com.mcampos.entity.inep.InepPackage;
import br.com.mcampos.entity.inep.InepRevisor;
import br.com.mcampos.entity.inep.InepSubscription;
import br.com.mcampos.entity.inep.InepTask;
import br.com.mcampos.entity.inep.InepTest;
import br.com.mcampos.utils.dto.PrincipalDTO;

@Remote
public interface TeamSession extends BaseSessionInterface<InepRevisor>, UserPropertyInterface
{
	List<InepTask> getTasks( );

	List<InepTask> getTasks( InepPackage evt );

	List<InepRevisor> getTeam( InepTask task );

	List<InepRevisor> getOralTeam( InepPackage evt );

	List<InepPackage> getPackages( InepRevisor rev );

	List<InepDistribution> getTests( InepRevisor rev, Integer testStatus );

	InepDistribution getRevision( InepRevisor rev, InepDistribution t );

	InepDistribution updateRevision( InepDistribution rev );

	List<InepPackage> getEvents( PrincipalDTO auth );

	InepRevisor getRevisor( InepPackage event, PrincipalDTO auth );

	byte[ ] getMedia( InepDistribution item );

	byte[ ] getMedia( InepSubscription s, InepTask t );

	List<InepDistribution> getOtherDistributions( InepTest test );

	InepTaskCounters getCounters( InepRevisor rev );

	public List<InepAnaliticoCorrecao> getAnaliticoCorrecao( InepPackage event );

	public List<InepAnaliticoCorrecao> getAnaliticoCorrecao( InepRevisor revisor );

	public List<InepAnaliticoCorrecao> getAnaliticoCorrecao( InepTask task );

	public List<BaseSubscriptionDTO> report( InepPackage event, Integer report );

	public List<InepAnaliticoCorrecao> getAnaliticoDivergencia( InepPackage event );

	public List<InepAnaliticoCorrecao> getAnaliticoDivergencia( InepTask task );

	public List<Object[ ]> getWorkStatus( InepPackage event );

	public List<Object[ ]> getSubscriptionStatus( InepPackage event );

	public List<InepSubscription> getSubscriptions( InepPackage event, String part );

	public List<InepDistribution> getDistribution( InepSubscription e );

	List<InepTest> getTests( InepSubscription s );

	InepOralTest getOralTest( InepSubscription s );

	List<InepOralDistribution> getOralDistributions( InepOralTest s );

	void resetTasks( InepSubscription s, List<InepTask> tasks );

	void swapTasks( InepSubscription s, InepTask t1, InepTask t2 );
}
