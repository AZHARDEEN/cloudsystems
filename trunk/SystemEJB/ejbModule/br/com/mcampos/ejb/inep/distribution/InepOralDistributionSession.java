package br.com.mcampos.ejb.inep.distribution;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.entity.InepOralDistribution;

@Remote
public interface InepOralDistributionSession extends BaseSessionInterface<InepOralDistribution>
{

}
