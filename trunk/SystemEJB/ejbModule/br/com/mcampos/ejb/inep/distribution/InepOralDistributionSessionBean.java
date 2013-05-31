package br.com.mcampos.ejb.inep.distribution;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.inep.entity.InepOralDistribution;
import br.com.mcampos.ejb.inep.entity.InepRevisor;

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

	@Override
	public List<InepOralDistribution> getOralTests( InepRevisor revisor )
	{
		return findByNamedQuery( InepOralDistribution.getRevisorOralTests,
				revisor.getId( ).getCompanyId( ),
				revisor.getId( ).getEventId( ),
				revisor.getId( ).getSequence( ) );
	}
}
