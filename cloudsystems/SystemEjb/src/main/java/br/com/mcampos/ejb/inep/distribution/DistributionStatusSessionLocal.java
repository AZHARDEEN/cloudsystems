package br.com.mcampos.ejb.inep.distribution;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.inep.DistributionStatus;

@Local
public interface DistributionStatusSessionLocal extends BaseCrudSessionInterface<DistributionStatus>
{

}
