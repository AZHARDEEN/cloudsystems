package br.com.mcampos.web.controller;


import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;

import br.com.mcampos.web.core.BaseController;

public class LogoutController extends BaseController
{
	private static final long serialVersionUID = -8869244107283274915L;

	@Override
	public void doAfterCompose( Component comp ) throws Exception
	{
		super.doAfterCompose( comp );
		Sessions.getCurrent().invalidate();
		Executions.sendRedirect( "/index.zul" );
	}
}
