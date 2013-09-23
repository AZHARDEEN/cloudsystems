package br.com.mcampos.ejb.inep.test;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepOralTest;
import br.com.mcampos.jpa.inep.InepRevisor;
import br.com.mcampos.jpa.inep.InepTask;
import br.com.mcampos.jpa.inep.InepTest;

@Local
public interface InepTestSessionLocal extends BaseCrudSessionInterface<InepTest>
{
	List<InepTest> getTests( InepEvent event );

	List<InepTest> getTests( InepTask task );

	List<InepTest> getTests( InepRevisor revisor );

	List<InepTest> getTestsWithVariance( InepEvent event );

	boolean insert( InepOralTest entity, boolean createSubscription );

	void setGrade( InepTest test, double grade );

}
