package br.com.mcampos.ejb.inep.distribution;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.entity.InepOralDistribution;

@Local
public interface InepOralDistributionLocal extends BaseSessionInterface<InepOralDistribution>
{

}
