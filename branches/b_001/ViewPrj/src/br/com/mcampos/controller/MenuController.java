package br.com.mcampos.controller;


import br.com.mcampos.controller.core.BaseController;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menuitem;


public class MenuController extends BaseController
{
	private Menuitem loginMenuItem;
	private Menuitem registerMenuItem;
	private Menuitem resentEmailMenuItem;
	private Menuitem validateEmailMenuItem;
	private Menu loginMenu;

	public MenuController()
	{
		super();
	}

	public MenuController( char c )
	{
		super( c );
	}

	@Override
	public void doAfterCompose( Component comp ) throws Exception
	{
		super.doAfterCompose( comp );
		setLabel( loginMenuItem );
		setLabel( registerMenuItem );
		setLabel( resentEmailMenuItem );
		setLabel( validateEmailMenuItem );
		setLabel( loginMenu );
	}
}
