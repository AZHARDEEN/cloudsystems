package br.com.mcampos.controller;

import org.zkoss.zul.Div;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menuitem;

import br.com.mcampos.controller.core.BaseController;

public class MenuController extends BaseController<Div>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4117574258310350141L;
	private Menuitem loginMenuItem;
	private Menuitem registerMenuItem;
	private Menuitem resentEmailMenuItem;
	private Menuitem validateEmailMenuItem;
	private Menu loginMenu;

	public MenuController( )
	{
		super( );
	}

	@Override
	public void doAfterCompose( Div comp ) throws Exception
	{
		super.doAfterCompose( comp );
		setLabel( loginMenuItem );
		setLabel( registerMenuItem );
		setLabel( resentEmailMenuItem );
		setLabel( validateEmailMenuItem );
		setLabel( loginMenu );
	}
}
