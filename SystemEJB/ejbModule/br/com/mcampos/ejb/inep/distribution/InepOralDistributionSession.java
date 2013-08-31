package br.com.mcampos.ejb.inep.distribution;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.inep.InepOralDistribution;

@Remote
public interface InepOralDistributionSession extends BaseSessionInterface<InepOralDistribution>
{

}
