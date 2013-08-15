package br.com.mcampos.ejb.inep.distribution;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.entity.DistributionStatus;

@Remote
public interface DistributionStatusSession extends BaseSessionInterface<DistributionStatus>
{

}
