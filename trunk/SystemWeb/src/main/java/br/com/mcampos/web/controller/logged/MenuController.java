package br.com.mcampos.web.controller.logged;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Menubar;

import br.com.mcampos.web.core.BaseLoggedController;

public class MenuController extends BaseLoggedController<Menubar>
{
	private static final long serialVersionUID = 4428298543865976909L;
	public static final String attrMenu = "dtoMenuObject";

	@Listen( "#mnuLogout" )
	public void onLogout( Event evt )
	{
		Sessions.getCurrent( ).invalidate( );
		Executions.sendRedirect( "/index.zul" );
	}

}
