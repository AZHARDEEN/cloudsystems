package br.com.mcampos.web.renderer;

import org.zkoss.zul.Listitem;

import br.com.mcampos.entity.security.Role;

public class RoleListItemRenderer extends PopupItemRenderer<Role>
{

	public RoleListItemRenderer( String menuPopup )
	{
		super( menuPopup );
	}

	@Override
	public void render( Listitem item, Role data, int index ) throws Exception
	{
		super.render( item, data, index );
		addCell( item, data.getId( ).toString( ) );
		addCell( item, data.getDescription( ) );
	}

}
