package br.com.mcampos.web.controller;


import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Window;

import br.com.mcampos.web.core.BaseController;

public class LogoutController extends BaseController<Window>
{
	private static final long serialVersionUID = -8869244107283274915L;

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		Sessions.getCurrent().invalidate();
		Executions.sendRedirect( "/index.zul" );
	}
}
