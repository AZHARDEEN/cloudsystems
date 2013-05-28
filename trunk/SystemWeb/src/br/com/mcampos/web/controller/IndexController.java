package br.com.mcampos.web.controller;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Window;

import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.mdi.BaseMDIController;

public class IndexController extends BaseMDIController
{
	private static final long serialVersionUID = -7093712320566410636L;

	public IndexController( )
	{
		super( );
	}

	@Listen( "menuitem" )
	public void onMenuitemClick( Event evt )
	{
		Menuitem menuItem;

		if ( evt.getTarget( ) instanceof Menuitem ) {
			menuItem = (Menuitem) evt.getTarget( );
			if ( SysUtils.isEmpty( menuItem.getValue( ) ) == false ) {
				loadPage( menuItem.getValue( ), true );
			}
		}
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		loadPage( "/public/login.zul", true );
	}
}
