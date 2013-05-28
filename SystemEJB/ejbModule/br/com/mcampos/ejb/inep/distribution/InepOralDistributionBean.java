package br.com.mcampos.ejb.inep.distribution;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;

/**
 * Session Bean implementation class InepOralDistributionBean
 */
@Stateless( mappedName = "InepOralDistribution", name = "InepOralDistribution" )
@LocalBean
public class InepOralDistributionBean extends SimpleSessionBean<InepOralDistribution> implements InepOralDistribution, InepOralDistributionLocal
{

	@Override
	protected Class<InepOralDistribution> getEntityClass( )
	{
		return InepOralDistribution.class;
	}

}
