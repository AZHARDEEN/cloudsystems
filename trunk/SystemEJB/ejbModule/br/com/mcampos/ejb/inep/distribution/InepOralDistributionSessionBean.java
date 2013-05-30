package br.com.mcampos.ejb.inep.distribution;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.inep.entity.InepOralDistribution;

/**
 * Session Bean implementation class InepOralDistributionBean
 */
@Stateless( mappedName = "InepOralDistributionSession", name = "InepOralDistributionSession" )
@LocalBean
public class InepOralDistributionSessionBean extends SimpleSessionBean<InepOralDistribution> implements InepOralDistributionSession, InepOralDistributionLocal
{

	@Override
	protected Class<InepOralDistribution> getEntityClass( )
	{
		return InepOralDistribution.class;
	}

}
