package br.com.mcampos.controller.anoto.renderer;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import br.com.mcampos.dto.anoto.FormDTO;

public class FormListRenderer implements ListitemRenderer
{
	protected boolean draggable;

	public FormListRenderer( )
	{
		super( );
	}

	@Override
	public void render( Listitem item, Object data, int index ) throws Exception
	{
		FormDTO dto = (FormDTO) data;

		item.setValue( dto );
		item.getChildren( ).add( new Listcell( dto.getApplication( ) ) );
		item.getChildren( ).add( new Listcell( dto.getDescription( ) ) );
		if ( isDraggable( ) )
			item.setDraggable( "true" );
	}

	public FormListRenderer setDraggable( boolean draggable )
	{
		this.draggable = draggable;
		return this;
	}

	public boolean isDraggable( )
	{
		return draggable;
	}
}
