package br.com.mcampos.ejb.inep.distribution;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.entity.inep.InepDistribution;
import br.com.mcampos.entity.inep.InepEvent;
import br.com.mcampos.entity.inep.InepRevisor;
import br.com.mcampos.entity.inep.InepSubscription;
import br.com.mcampos.entity.inep.InepTest;

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
		return findByNamedQuery( InepDistribution.getAllFromRevisor, new DBPaging( 0, 45 ), rev, status );
	}

	@Override
	public List<InepDistribution> get( InepTest test )
	{
		return findByNamedQuery( InepDistribution.getAllFromTest, new DBPaging( 0, 45 ), test );
	}

	@Override
	public List<InepDistribution> getAll( InepEvent event )
	{
		return findByNamedQuery( InepDistribution.getAll, new DBPaging( 0, 45 ), event );
	}

	@Override
	public List<InepDistribution> getVariance( InepEvent event )
	{
		return findByNamedQuery( InepDistribution.getAllVariance, new DBPaging( 0, 45 ), event );
	}

	@Override
	public List<InepDistribution> getVariance( InepTest test )
	{
		return findByNamedQuery( InepDistribution.getAllVarianceFromTest, new DBPaging( 0, 45 ), test );
	}

	@Override
	public List<InepDistribution> getAllforReport( InepEvent event )
	{
		return findByNamedQuery( InepDistribution.getAll, null, event );
	}

	@Override
	public List<InepDistribution> getVarianceForReport( InepEvent event )
	{
		return findByNamedQuery( InepDistribution.getAllVariance, null, event );
	}

	@Override
	public List<InepDistribution> getAll( InepSubscription s )
	{
		return findByNamedQuery( InepDistribution.getAllSubscription, s );
	}

}
