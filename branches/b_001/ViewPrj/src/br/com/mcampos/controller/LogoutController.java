package br.com.mcampos.controller;

import br.com.mcampos.controller.core.BaseController;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;

public class LogoutController extends BaseController
{
    public LogoutController()
    {
        super();
    }

    public LogoutController( char c )
    {
        super( c );
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        Sessions.getCurrent().invalidate();
        Executions.sendRedirect( "/index.zul" );
    }
}
