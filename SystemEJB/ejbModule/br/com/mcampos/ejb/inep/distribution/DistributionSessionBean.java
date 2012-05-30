package br.com.mcampos.ejb.inep.distribution;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.inep.entity.InepDistribution;
import br.com.mcampos.ejb.inep.revisor.InepRevisor;

/**
 * Session Bean implementation class DistributionSessionBean
 */
@Stateless( name = "DistributionSession", mappedName = "DistributionSession" )
@LocalBean
public class DistributionSessionBean extends SimpleSessionBean<InepDistribution> implements DistributionSession,
DistributionSessionLocal
{
	@Override
	protected Class<InepDistribution> getEntityClass( )
	{
		return InepDistribution.class;
	}

	@Override
	public List<InepDistribution> get( InepRevisor rev )
	{
		return findByNamedQuery( InepDistribution.getAllFromRevisor, rev );
	}

}
