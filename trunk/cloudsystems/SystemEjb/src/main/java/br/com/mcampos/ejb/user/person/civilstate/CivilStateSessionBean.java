package br.com.mcampos.ejb.user.person.civilstate;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.jpa.user.CivilState;

@Stateless( name = "CivilStateSession", mappedName = "CivilStateSession" )
public class CivilStateSessionBean extends SimpleSessionBean<CivilState> implements CivilStateSession, CivilStateSessionLocal
{
	@Resource
	SessionContext sessionContext;

	@Override
	protected Class<CivilState> getEntityClass( )
	{
		return CivilState.class;
	}
}
