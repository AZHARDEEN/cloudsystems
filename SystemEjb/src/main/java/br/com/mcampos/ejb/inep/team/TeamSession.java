package br.com.mcampos.ejb.inep.team;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.dto.inep.InepAnaliticoCorrecao;
import br.com.mcampos.dto.inep.InepTaskCounters;
import br.com.mcampos.dto.inep.reporting.BaseSubscriptionDTO;
import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.ejb.user.company.collaborator.UserPropertyInterface;
import br.com.mcampos.jpa.inep.InepDistribution;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepOralDistribution;
import br.com.mcampos.jpa.inep.InepOralTest;
import br.com.mcampos.jpa.inep.InepRevisor;
import br.com.mcampos.jpa.inep.InepSubscription;
import br.com.mcampos.jpa.inep.InepTask;
import br.com.mcampos.jpa.inep.InepTest;

@Remote
public interface TeamSession extends BaseCrudSessionInterface<InepRevisor>, UserPropertyInterface
{
	List<InepTask> getTasks( PrincipalDTO auth );

	List<InepTask> getTasks( InepEvent evt );

	List<InepRevisor> getTeam( InepTask task );

	List<InepRevisor> getOralTeam( InepEvent evt );

	List<InepEvent> getPackages( InepRevisor rev );

	List<InepDistribution> getTests( InepRevisor rev, Integer testStatus );

	InepDistribution getRevision( InepRevisor rev, InepDistribution t );

	InepDistribution updateRevision( InepDistribution rev );

	List<InepEvent> getEvents( PrincipalDTO auth );

	InepRevisor getRevisor( InepEvent event, PrincipalDTO auth );

	byte[ ] getMedia( InepDistribution item );

	byte[ ] getMedia( InepSubscription s, InepTask t );

	List<InepDistribution> getOtherDistributions( InepTest test );

	InepTaskCounters getCounters( InepRevisor rev );

	public List<InepAnaliticoCorrecao> getAnaliticoCorrecao( InepEvent event );

	public List<InepAnaliticoCorrecao> getAnaliticoCorrecao( InepRevisor revisor );

	public List<InepAnaliticoCorrecao> getAnaliticoCorrecao( InepTask task );

	public List<BaseSubscriptionDTO> report( InepEvent event, Integer report );

	public List<InepAnaliticoCorrecao> getAnaliticoDivergencia( InepEvent event );

	public List<InepAnaliticoCorrecao> getAnaliticoDivergencia( InepTask task );

	public List<Object[ ]> getWorkStatus( InepEvent event );

	public List<Object[ ]> getSubscriptionStatus( InepEvent event );

	public List<InepSubscription> getSubscriptions( PrincipalDTO auth, InepEvent event, String part );

	public List<InepDistribution> getDistribution( InepSubscription e );

	List<InepTest> getTests( PrincipalDTO auth, InepSubscription s );

	InepOralTest getOralTest( InepSubscription s );

	List<InepOralDistribution> getOralDistributions( InepOralTest s );

	void resetTasks( PrincipalDTO auth, InepSubscription s, List<InepTask> tasks );

	void swapTasks( InepSubscription s, InepTask t1, InepTask t2 );
}
