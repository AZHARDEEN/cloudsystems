package br.com.mcampos.web.controller.admin.security.renderer;

import org.zkoss.zul.Listitem;

import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.listbox.BaseListRenderer;

public abstract class PopupItemRenderer<T> extends BaseListRenderer<T>
{
	private String menuPopup = null;

	public PopupItemRenderer( String menuPopup )
	{
		super( );
		setMenuPopup( menuPopup );
	}

	protected String getMenuPopup( )
	{
		return this.menuPopup;
	}

	protected void setMenuPopup( String menuPopup )
	{
		this.menuPopup = menuPopup;
	}

	@Override
	public void render( Listitem item, T data, int index ) throws Exception
	{
		super.render( item, data, index );
		if ( SysUtils.isEmpty( getMenuPopup( ) ) == false )
		{
			setContext( item );
		}
	}

	protected void setContext( Listitem item )
	{
		item.setContext( getMenuPopup( ) );
	}
}
