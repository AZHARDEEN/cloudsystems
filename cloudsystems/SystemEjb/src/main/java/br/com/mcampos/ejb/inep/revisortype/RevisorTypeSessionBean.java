package br.com.mcampos.ejb.inep.revisortype;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.jpa.inep.RevisorType;

/**
 * Session Bean implementation class RevisorTypeSessionBean
 */
@Stateless( mappedName = "RevisorTypeSession", name = "RevisorTypeSession" )
@LocalBean
public class RevisorTypeSessionBean extends SimpleSessionBean<RevisorType> implements RevisorTypeSession, RevisorTypeSessionLocal
{
	@Override
	protected Class<RevisorType> getEntityClass( )
	{
		return RevisorType.class;
	}

}
