package br.com.mcampos.controller.user;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import br.com.mcampos.controller.commom.AbstractLisrRenderer;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.sysutils.SysUtils;

public class UserListRenderer extends AbstractLisrRenderer
{
	public UserListRenderer( )
	{
		super( );
	}

	@Override
	public void render( Listitem item, Object data, int index ) throws Exception
	{
		item.setValue( data );
		if ( data == null )
			return;
		ListUserDTO u = (ListUserDTO) data;
		Listcell cellId, cellDescription;

		String name = SysUtils.isEmpty( u.getNickName( ) ) ? u.getName( ) : u.getNickName( );
		createCells( item );
		cellId = (Listcell) item.getChildren( ).get( 0 );
		cellDescription = (Listcell) item.getChildren( ).get( 1 );
		if ( cellId != null )
			cellId.setLabel( u.getId( ).toString( ) );
		if ( cellDescription != null )
			cellDescription.setLabel( name );
	}
}
