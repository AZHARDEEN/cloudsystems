package br.com.mcampos.ejb.inep.distribution;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.entity.InepDistribution;
import br.com.mcampos.ejb.inep.entity.InepRevisor;
import br.com.mcampos.ejb.inep.entity.InepTest;

@Local
public interface DistributionSessionLocal extends BaseSessionInterface<InepDistribution>
{
	List<InepDistribution> get( InepRevisor rev, Integer status );

	List<InepDistribution> get( InepTest test );

}
