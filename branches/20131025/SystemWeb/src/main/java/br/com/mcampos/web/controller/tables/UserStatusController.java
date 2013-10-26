package br.com.mcampos.web.controller.tables;

import br.com.mcampos.ejb.security.UserStatusSession;
import br.com.mcampos.jpa.security.UserStatus;
import br.com.mcampos.web.core.SimpleTableController;

public class UserStatusController extends SimpleTableController<UserStatusSession, UserStatus>
{
	private static final long serialVersionUID = -5538586357413295856L;

	public UserStatusController( )
	{
		super( );
	}

	@Override
	protected Class<UserStatusSession> getSessionClass( )
	{
		return UserStatusSession.class;
	}
}
