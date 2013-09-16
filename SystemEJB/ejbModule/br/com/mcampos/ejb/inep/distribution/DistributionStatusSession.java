package br.com.mcampos.ejb.inep.distribution;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.inep.DistributionStatus;

@Remote
public interface DistributionStatusSession extends BaseCrudSessionInterface<DistributionStatus>
{

}
