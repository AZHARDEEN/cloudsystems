package br.com.mcampos.ejb.inep.distribution;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.inep.entity.DistributionStatus;

/**
 * Session Bean implementation class DistributionStatusSessionBean
 */
@Stateless( name = "DistributionStatusSession", mappedName = "DistributionStatusSession" )
@LocalBean
public class DistributionStatusSessionBean extends SimpleSessionBean<DistributionStatus> implements DistributionStatusSession,
DistributionStatusSessionLocal
{

	@Override
	protected Class<DistributionStatus> getEntityClass( )
	{
		return DistributionStatus.class;
	}

}
