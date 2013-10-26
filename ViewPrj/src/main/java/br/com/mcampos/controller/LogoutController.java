package br.com.mcampos.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;

import br.com.mcampos.controller.core.BaseController;

public class LogoutController extends BaseController
{
	public LogoutController( )
	{
		super( );
	}

	@Override
	public void doAfterCompose( Component comp ) throws Exception
	{
		super.doAfterCompose( comp );
		Sessions.getCurrent( ).invalidate( );
		Executions.sendRedirect( "/index.zul" );
	}
}
