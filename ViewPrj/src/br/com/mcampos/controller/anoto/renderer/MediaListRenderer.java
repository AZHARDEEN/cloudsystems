package br.com.mcampos.controller.anoto.renderer;


import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;


public class MediaListRenderer implements ListitemRenderer
{
    public MediaListRenderer()
    {
        super();
    }

    public void render( Listitem item, Object data ) throws Exception
    {
        item.setValue( data );
        item.getChildren().add( new Listcell( data.toString() ) );
    }
}
