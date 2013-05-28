package br.com.mcampos.ejb.inep;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.inep.entity.InepOralTest;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepRevisor;
import br.com.mcampos.ejb.inep.oral.InepOralTestSessionLocal;
import br.com.mcampos.ejb.inep.team.TeamSessionLocal;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;

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

}
