package br.com.mcampos.controller.anoto.renderer;


import br.com.mcampos.dto.anoto.PenDTO;

import java.io.Serializable;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;


public class AnotoPenListRenderer implements ListitemRenderer, Serializable
{
    public AnotoPenListRenderer()
    {
        super();
    }

    public void render( Listitem listitem, Object object ) throws Exception
    {
        PenDTO dto = ( PenDTO )object;

        if ( dto != null ) {
            listitem.setValue( object );
            listitem.getChildren().add( new Listcell( dto.getId() ) );
        }
    }
}
