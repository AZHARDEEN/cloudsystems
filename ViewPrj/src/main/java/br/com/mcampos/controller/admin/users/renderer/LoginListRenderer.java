package br.com.mcampos.controller.admin.users.renderer;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import br.com.mcampos.dto.user.login.ListLoginDTO;

public class LoginListRenderer implements ListitemRenderer
{
	public LoginListRenderer( )
	{
		super( );
	}

	@Override
	public void render( Listitem item, Object data, int index ) throws Exception
	{
		ListLoginDTO usr = (ListLoginDTO) data;

		item.setValue( usr );
		item.getChildren( ).add( new Listcell( usr.getId( ).toString( ) ) );
		item.getChildren( ).add( new Listcell( usr.getName( ) ) );
		if ( usr.getUserStatus( ) != null )
			item.getChildren( ).add( new Listcell( usr.getUserStatus( ).getDescription( ) ) );
	}
}
