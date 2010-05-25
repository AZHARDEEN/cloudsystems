package br.com.mcampos.controller.anoto.renderer;


import br.com.mcampos.dto.anoto.FormDTO;

import java.io.Serializable;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;


public class AnotoFormListRenderer implements ListitemRenderer, Serializable
{
    public AnotoFormListRenderer()
    {
        super();
    }

    public void render( Listitem listitem, Object object ) throws Exception
    {
        FormDTO dto = ( FormDTO )object;

        if ( dto != null ) {
            listitem.setValue( object );
            listitem.getChildren().clear();
            listitem.getChildren().add( new Listcell( dto.getApplication() ) );
            listitem.getChildren().add( new Listcell( dto.getDescription() ) );
        }
    }
}
