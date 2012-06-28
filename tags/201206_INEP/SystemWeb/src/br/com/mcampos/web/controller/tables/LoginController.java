package br.com.mcampos.web.controller.tables;

import br.com.mcampos.ejb.security.Login;
import br.com.mcampos.ejb.security.LoginSession;
import br.com.mcampos.web.core.listbox.ReadOnlyListboxController;

public class LoginController extends ReadOnlyListboxController<LoginSession, Login>
{
	private static final long serialVersionUID = -6113220478051882814L;

	@Override
	protected Class<LoginSession> getSessionClass( )
	{
		return LoginSession.class;
	}
}
