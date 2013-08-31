package br.com.mcampos.ejb.inep.test;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.inep.InepOralTest;
import br.com.mcampos.entity.inep.InepPackage;
import br.com.mcampos.entity.inep.InepRevisor;
import br.com.mcampos.entity.inep.InepTask;
import br.com.mcampos.entity.inep.InepTest;

@Local
public interface InepTestSessionLocal extends BaseSessionInterface<InepTest>
{
	List<InepTest> getTests( InepPackage event );

	List<InepTest> getTests( InepTask task );

	List<InepTest> getTests( InepRevisor revisor );

	List<InepTest> getTestsWithVariance( InepPackage event );

	boolean insert( InepOralTest entity, boolean createSubscription );

	void setGrade( InepTest test, double grade );

}
