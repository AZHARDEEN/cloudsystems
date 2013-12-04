package br.com.mcampos.ejb.inep.oral;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepOralTest;
import br.com.mcampos.jpa.inep.InepSubscription;

@Local
public interface InepOralTestSessionLocal extends BaseCrudSessionInterface<InepOralTest>
{
	InepOralTest add( InepOralTest entity, boolean createSubscription );

	List<InepOralTest> getVarianceOralOnly( InepEvent pack );

	List<InepOralTest> getVarianceWrittenOnly( InepEvent pack );

	void setAgreementGrade( InepOralTest test, Integer grade, boolean isCoordinator );

	InepOralTest get( InepSubscription s );

	InepOralTest setStatus( InepOralTest newEntity );

}
