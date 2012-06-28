package br.com.mcampos.ejb.inep.distribution;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.inep.entity.DistributionStatus;

@Local
public interface DistributionStatusSessionLocal extends BaseSessionInterface<DistributionStatus>
{

}
