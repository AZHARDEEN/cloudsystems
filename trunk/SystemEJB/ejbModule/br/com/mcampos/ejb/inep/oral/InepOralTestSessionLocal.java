package br.com.mcampos.ejb.inep.oral;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.inep.InepOralTest;
import br.com.mcampos.entity.inep.InepEvent;
import br.com.mcampos.entity.inep.InepSubscription;

@Local
public interface InepOralTestSessionLocal extends BaseCrudSessionInterface<InepOralTest>
{
	void add( InepOralTest entity, boolean createSubscription );

	List<InepOralTest> getVarianceOralOnly( InepEvent pack );

	void setAgreementGrade( InepOralTest test, Integer grade, boolean isCoordinator );

	InepOralTest get( InepSubscription s );

}
