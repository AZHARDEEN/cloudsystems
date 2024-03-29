package br.com.mcampos.controller.admin.users.renderer;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import br.com.mcampos.dto.user.ListUserDTO;

public class UserListRenderer implements ListitemRenderer
{
	public UserListRenderer( )
	{
		super( );
	}

	@Override
	public void render( Listitem item, Object data, int index ) throws Exception
	{
		ListUserDTO usr = (ListUserDTO) data;

		item.setValue( usr );
		item.getChildren( ).add( new Listcell( usr.getId( ).toString( ) ) );
		item.getChildren( ).add( new Listcell( usr.getName( ) ) );
		item.getChildren( ).add( new Listcell( usr.getUserType( ).getDescription( ) ) );

		if ( usr.getLastUpdate( ) != null ) {
			SimpleDateFormat df = new SimpleDateFormat( "dd/MM/yyyy" );
			Date dt = new Date( usr.getLastUpdate( ).getTime( ) );
			item.getChildren( ).add( new Listcell( df.format( dt ) ) );
		}
		else
			item.getChildren( ).add( new Listcell( "NULL" ) );
	}
}
