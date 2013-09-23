package br.com.mcampos.controller.anoto.renderer;

import java.io.Serializable;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import br.com.mcampos.dto.anoto.FormDTO;

public class AnotoFormListRenderer implements ListitemRenderer, Serializable
{
	public AnotoFormListRenderer( )
	{
		super( );
	}

	@Override
	public void render( Listitem listitem, Object object, int index ) throws Exception
	{
		FormDTO dto = (FormDTO) object;

		if ( dto != null ) {
			listitem.setValue( object );
			listitem.getChildren( ).clear( );
			listitem.getChildren( ).add( new Listcell( dto.getApplication( ) ) );
			listitem.getChildren( ).add( new Listcell( dto.getDescription( ) ) );
		}
	}
}
