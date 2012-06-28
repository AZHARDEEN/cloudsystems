package br.com.mcampos.ejb.inep.distribution;

import java.util.List;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.entity.InepDistribution;
import br.com.mcampos.ejb.inep.entity.InepRevisor;

@Remote
public interface DistributionSession extends BaseSessionInterface<InepDistribution>
{
	List<InepDistribution> get( InepRevisor rev, Integer status );
}
