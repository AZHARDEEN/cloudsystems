package br.com.mcampos.controller.admin.security.roles;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

public class RoleComboRenderer implements ComboitemRenderer
{
	public RoleComboRenderer( )
	{
		super( );
	}

	@Override
	public void render( Comboitem item, Object data, int index )
	{
		item.setValue( data );
		if ( data != null )
			item.setLabel( data.toString( ) );
	}
}
