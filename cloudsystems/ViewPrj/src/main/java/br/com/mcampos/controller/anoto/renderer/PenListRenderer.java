package br.com.mcampos.controller.anoto.renderer;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import br.com.mcampos.dto.anoto.PenDTO;

public class PenListRenderer implements ListitemRenderer
{
	protected boolean draggable;

	public PenListRenderer( )
	{
		super( );
	}

	@Override
	public void render( Listitem item, Object data, int index ) throws Exception
	{
		if ( item == null )
			return;
		PenDTO dto = (PenDTO) data;
		item.setValue( data );

		item.getChildren( ).add( new Listcell( dto.getId( ) ) );
		if ( isDraggable( ) )
			item.setDraggable( "true" );
	}

	public PenListRenderer setDraggable( boolean draggable )
	{
		this.draggable = draggable;
		return this;
	}

	public boolean isDraggable( )
	{
		return draggable;
	}
}
