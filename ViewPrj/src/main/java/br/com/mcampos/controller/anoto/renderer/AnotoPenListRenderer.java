package br.com.mcampos.controller.anoto.renderer;

import java.io.Serializable;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import br.com.mcampos.dto.anoto.PenDTO;

public class AnotoPenListRenderer implements ListitemRenderer, Serializable
{
	public AnotoPenListRenderer( )
	{
		super( );
	}

	@Override
	public void render( Listitem listitem, Object object, int index ) throws Exception
	{
		PenDTO dto = (PenDTO) object;

		if ( dto != null ) {
			listitem.setValue( object );
			listitem.getChildren( ).add( new Listcell( dto.getId( ) ) );
			listitem.getChildren( ).add( new Listcell( dto.getUser( ) != null ? dto.getUser( ).getName( ) : "-" ) );

		}
	}
}
