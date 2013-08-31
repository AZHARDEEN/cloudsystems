package br.com.mcampos.ejb.inep.distribution;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.inep.InepDistribution;
import br.com.mcampos.entity.inep.InepPackage;
import br.com.mcampos.entity.inep.InepRevisor;
import br.com.mcampos.entity.inep.InepSubscription;
import br.com.mcampos.entity.inep.InepTest;

@Local
public interface DistributionSessionLocal extends BaseSessionInterface<InepDistribution>
{
	List<InepDistribution> get( InepRevisor rev, Integer status );

	List<InepDistribution> get( InepTest test );

	List<InepDistribution> getVariance( InepTest test );

	List<InepDistribution> getAll( InepPackage event );

	List<InepDistribution> getAll( InepSubscription s );

	List<InepDistribution> getAllforReport( InepPackage event );

	List<InepDistribution> getVariance( InepPackage event );

	List<InepDistribution> getVarianceForReport( InepPackage event );

}
