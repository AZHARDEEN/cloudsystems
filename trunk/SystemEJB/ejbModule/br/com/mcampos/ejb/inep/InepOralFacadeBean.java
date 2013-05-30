package br.com.mcampos.ejb.inep;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.dto.inep.InepOralTeamDTO;
import br.com.mcampos.ejb.inep.distribution.DistributionStatusSessionLocal;
import br.com.mcampos.ejb.inep.distribution.InepOralDistributionLocal;
import br.com.mcampos.ejb.inep.entity.DistributionStatus;
import br.com.mcampos.ejb.inep.entity.InepOralDistribution;
import br.com.mcampos.ejb.inep.entity.InepOralTest;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepRevisor;
import br.com.mcampos.ejb.inep.oral.InepOralTestSessionLocal;
import br.com.mcampos.ejb.inep.team.TeamSessionLocal;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;
import br.com.mcampos.sysutils.SysUtils;

/**
 * Session Bean implementation class InepOralFacadeBean
 */
@Stateless( name = "InepOralFacade", mappedName = "InepOralFacade" )
@LocalBean
public class InepOralFacadeBean implements InepOralFacade
{
	@EJB
	InepOralTestSessionLocal oralTestSession;
	@EJB
	TeamSessionLocal teamSession;
	@EJB
	InepOralDistributionLocal oralDistributionSession;
	@EJB
	DistributionStatusSessionLocal statusSession;

	@Override
	public List<InepOralTest> getVarianceOralOnly( Collaborator c, InepPackage pack )
	{
		return oralTestSession.getVarianceOralOnly( pack );
	}

	@Override
	public List<InepPackage> getEvents( Collaborator auth )
	{
		return teamSession.getEvents( auth );
	}

	@Override
	public InepRevisor getRevisor( InepPackage event, Collaborator auth )
	{
		return teamSession.getRevisor( event, auth );
	}

	@Override
	public List<InepOralTeamDTO> getOralTeamToChoice( InepPackage event, Collaborator auth )
	{
		List<InepRevisor> list = teamSession.getOralTeam( event );
		ArrayList<InepOralTeamDTO> retList = null;

		if ( list != null ) {
			retList = new ArrayList<InepOralTeamDTO>( list.size( ) );
			for ( InepRevisor item : list ) {
				InepOralTeamDTO dto = new InepOralTeamDTO( item );
				retList.add( dto );
			}
		}
		return retList;
	}

	@Override
	public void distribute( InepPackage event, Collaborator auth, InepRevisor r1, InepRevisor r2, Set<InepOralTest> tests )
	{
		if ( event == null || auth == null || r1 == null || r2 == null || SysUtils.isEmpty( tests ) )
			throw new InvalidParameterException( );

		for ( InepOralTest test : tests ) {
			InepOralTest merged = oralTestSession.merge( test );
			merged.setStatus( statusSession.get( DistributionStatus.statusDistributed ) );
			oralDistributionSession.merge( new InepOralDistribution( merged, r1 ) );
			oralDistributionSession.merge( new InepOralDistribution( merged, r2 ) );
		}
	}
}
