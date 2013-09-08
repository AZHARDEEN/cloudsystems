package br.com.mcampos.ejb.inep.distribution;

import java.util.List;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.inep.InepOralDistribution;
import br.com.mcampos.entity.inep.InepOralTest;
import br.com.mcampos.entity.inep.InepRevisor;

@Local
public interface InepOralDistributionLocal extends BaseCrudSessionInterface<InepOralDistribution>
{
	List<InepOralDistribution> getOralTests( InepRevisor revisor );

	InepOralDistribution findOther( InepOralDistribution item );

	List<InepOralDistribution> getOralDistributions( InepOralTest test );
}
