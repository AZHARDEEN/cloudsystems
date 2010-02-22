package br.com.mcampos.controller.anoto.renderer;

import br.com.mcampos.dto.anode.PenDTO;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class PenListRenderer implements ListitemRenderer
{
    protected boolean draggable;

    public PenListRenderer()
    {
        super();
    }

    public void render( Listitem item, Object data ) throws Exception
    {
        if ( item == null )
            return;
        PenDTO dto = ( PenDTO )data;
        item.setValue( data );

        item.getChildren().add( new Listcell( dto.getId() ) );
        if ( isDraggable() )
            item.setDraggable( "true" );
    }

    public PenListRenderer setDraggable( boolean draggable )
    {
        this.draggable = draggable;
        return this;
    }

    public boolean isDraggable()
    {
        return draggable;
    }
}
