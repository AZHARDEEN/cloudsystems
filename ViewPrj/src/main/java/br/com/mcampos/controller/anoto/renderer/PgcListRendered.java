package br.com.mcampos.controller.anoto.renderer;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import br.com.mcampos.dto.anoto.PGCDTO;

public class PgcListRendered implements ListitemRenderer
{
	public PgcListRendered( )
	{
		super( );
	}

	@Override
	public void render( Listitem item, Object data, int index )
	{
		item.setValue( data );
		PGCDTO dto = (PGCDTO) data;

		Listcell cell = new Listcell( dto.getMedia( ).getName( ) );
		item.appendChild( cell );
	}
}
