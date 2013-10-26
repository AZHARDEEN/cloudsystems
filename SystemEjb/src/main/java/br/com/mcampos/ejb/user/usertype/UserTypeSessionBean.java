package br.com.mcampos.ejb.user.usertype;


import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.jpa.user.UserType;


@Stateless( name = "UserTypeSession", mappedName = "UserTypeSession" )
public class UserTypeSessionBean extends SimpleSessionBean<UserType> implements UserTypeSession, UserTypeSessionLocal
{
	@Override
	protected Class<UserType> getEntityClass()
	{
		return UserType.class;
	}
}
