package br.com.mcampos.ejb.system.revisionstatus;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;

/**
 * Session Bean implementation class RevisionStatusSessionBean
 */
@Stateless( name = "RevisionStatusSession", mappedName = "RevisionStatusSession" )
@LocalBean
public class RevisionStatusSessionBean extends SimpleSessionBean<RevisionStatus> implements RevisionStatusSession, RevisionStatusSessionLocal
{
	@Override
	protected Class<RevisionStatus> getEntityClass( )
	{
		return RevisionStatus.class;
	}

}
