package br.com.mcampos.ejb.inep.distribution;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.entity.InepOralDistribution;
import br.com.mcampos.ejb.inep.entity.InepOralTest;
import br.com.mcampos.ejb.inep.entity.InepRevisor;

@Local
public interface InepOralDistributionLocal extends BaseSessionInterface<InepOralDistribution>
{
	List<InepOralDistribution> getOralTests( InepRevisor revisor );

	InepOralDistribution findOther( InepOralDistribution item );

	List<InepOralDistribution> getOralDistributions( InepOralTest test );
}