package br.com.mcampos.ejb.inep.oral;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.entity.InepOralTest;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepSubscription;

@Local
public interface InepOralTestSessionLocal extends BaseSessionInterface<InepOralTest>
{
	void add( InepOralTest entity, boolean createSubscription );

	List<InepOralTest> getVarianceOralOnly( InepPackage pack );

	void setAgreementGrade( InepOralTest test, Integer grade, boolean isCoordinator );

	InepOralTest get( InepSubscription s );

}
