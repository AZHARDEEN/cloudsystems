package br.com.mcampos.ejb.user.person.gender;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.entity.user.Gender;

@Stateless( name = "GenderSession", mappedName = "GenderSession" )
public class GenderSessionBean extends SimpleSessionBean<Gender> implements GenderSession, GenderSessionLocal
{
	@Resource
	SessionContext sessionContext;

	@Override
	protected Class<Gender> getEntityClass( )
	{
		return Gender.class;
	}
}
