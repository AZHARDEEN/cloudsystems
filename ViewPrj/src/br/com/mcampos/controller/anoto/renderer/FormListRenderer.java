package br.com.mcampos.controller.anoto.renderer;

import br.com.mcampos.dto.anode.FormDTO;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class FormListRenderer implements ListitemRenderer
{
    protected boolean draggable;

    public FormListRenderer()
    {
        super();
    }

    public void render( Listitem item, Object data ) throws Exception
    {
        FormDTO dto = ( FormDTO )data;

        item.setValue( dto );
        item.getChildren().add( new Listcell( dto.getIp() ) );
        item.getChildren().add( new Listcell( dto.getDescription() ) );
        if ( isDraggable() )
            item.setDraggable( "true" );
    }

    public FormListRenderer setDraggable( boolean draggable )
    {
        this.draggable = draggable;
        return this;
    }

    public boolean isDraggable()
    {
        return draggable;
    }
}
