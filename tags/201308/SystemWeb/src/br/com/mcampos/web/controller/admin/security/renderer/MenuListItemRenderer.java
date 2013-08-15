package br.com.mcampos.web.controller.admin.security.renderer;

import org.zkoss.zul.Listitem;

import br.com.mcampos.ejb.security.menu.Menu;

public class MenuListItemRenderer extends PopupItemRenderer<Menu>
{

	public MenuListItemRenderer( String menuPopup )
	{
		super( menuPopup );
	}

	@Override
	public void render( Listitem item, Menu data, int index ) throws Exception
	{
		super.render( item, data, index );
		addCell( item, data.getId( ).toString( ) );
		addCell( item, data.getDescription( ) );
		addCell( item, data.getUrl( ) );
	}

}
