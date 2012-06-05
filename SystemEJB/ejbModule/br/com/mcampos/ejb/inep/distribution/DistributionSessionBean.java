package br.com.mcampos.ejb.inep.distribution;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.inep.entity.InepDistribution;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepRevisor;
import br.com.mcampos.ejb.inep.entity.InepTest;

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
	public List<InepDistribution> get( InepRevisor rev, Integer status )
	{
		return findByNamedQuery( InepDistribution.getAllFromRevisor, rev, status );
	}

	@Override
	public List<InepDistribution> get( InepTest test )
	{
		return findByNamedQuery( InepDistribution.getAllFromTest, test );
	}

	@Override
	public List<InepDistribution> getAll( InepPackage event )
	{
		return findByNamedQuery( InepDistribution.getAll, event );
	}

	@Override
	public List<InepDistribution> getVariance( InepPackage event )
	{
		return findByNamedQuery( InepDistribution.getAllVariance, event );
	}

}
