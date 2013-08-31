package br.com.mcampos.ejb.inep.distribution;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.inep.DistributionStatus;

@Local
public interface DistributionStatusSessionLocal extends BaseSessionInterface<DistributionStatus>
{

}
