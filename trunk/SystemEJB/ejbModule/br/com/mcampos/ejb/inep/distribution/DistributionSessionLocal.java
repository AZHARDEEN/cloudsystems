package br.com.mcampos.ejb.inep.distribution;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.inep.InepDistribution;
import br.com.mcampos.entity.inep.InepEvent;
import br.com.mcampos.entity.inep.InepRevisor;
import br.com.mcampos.entity.inep.InepSubscription;
import br.com.mcampos.entity.inep.InepTest;

@Local
public interface DistributionSessionLocal extends BaseCrudSessionInterface<InepDistribution>
{
	List<InepDistribution> get( InepRevisor rev, Integer status );

	List<InepDistribution> get( InepTest test );

	List<InepDistribution> getVariance( InepTest test );

	List<InepDistribution> getAll( InepEvent event );

	List<InepDistribution> getAll( InepSubscription s );

	List<InepDistribution> getAllforReport( InepEvent event );

	List<InepDistribution> getVariance( InepEvent event );

	List<InepDistribution> getVarianceForReport( InepEvent event );

}
