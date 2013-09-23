package br.com.mcampos.ejb.security;

import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.jpa.security.UserStatus;

@Stateless( name = "UserStatusSession", mappedName = "UserStatusSession" )
public class UserStatusSessionBean extends SimpleSessionBean<UserStatus> implements UserStatusSession, UserStatusSessionLocal
{
	@Override
	protected Class<UserStatus> getEntityClass( )
	{
		return UserStatus.class;
	}
}
