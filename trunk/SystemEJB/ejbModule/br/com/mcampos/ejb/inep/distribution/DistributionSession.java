package br.com.mcampos.ejb.inep.distribution;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.inep.InepDistribution;
import br.com.mcampos.entity.inep.InepRevisor;

@Remote
public interface DistributionSession extends BaseCrudSessionInterface<InepDistribution>
{
	List<InepDistribution> get( InepRevisor rev, Integer status );
}
