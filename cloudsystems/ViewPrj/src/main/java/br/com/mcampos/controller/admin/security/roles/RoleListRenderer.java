package br.com.mcampos.controller.admin.security.roles;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import br.com.mcampos.controller.commom.AbstractLisrRenderer;
import br.com.mcampos.dto.security.RoleDTO;

public class RoleListRenderer extends AbstractLisrRenderer
{
	public RoleListRenderer( )
	{
		super( );
	}

	@Override
	public void render( Listitem item, Object data, int index ) throws Exception
	{
		item.setValue( data );
		RoleDTO dto = (RoleDTO) data;
		if ( dto != null ) {
			item.getChildren( ).add( new Listcell( dto.getId( ).toString( ) ) );
			item.getChildren( ).add( new Listcell( dto.getDescription( ) ) );
			item.getChildren( ).add( new Listcell( ( dto.getParent( ) != null ) ? dto.getParent( ).toString( ) : " " ) );
		}
	}
}
