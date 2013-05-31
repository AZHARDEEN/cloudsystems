package br.com.mcampos.ejb.inep.distribution;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.ejb.inep.entity.InepOralDistribution;
import br.com.mcampos.ejb.inep.entity.InepRevisor;
import br.com.mcampos.sysutils.SysUtils;

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

	@Override
	public InepOralDistribution findOther( InepOralDistribution item )
	{
		List<InepOralDistribution> others = findByNamedQuery( InepOralDistribution.getOther, item.getTest( ), item.getId( ).getCollaboratorId( ) );
		if ( SysUtils.isEmpty( others ) )
			return null;
		for ( InepOralDistribution other : others ) {
			if ( other.getNota( ) != null )
				return other;
		}
		return null;
	}
}
