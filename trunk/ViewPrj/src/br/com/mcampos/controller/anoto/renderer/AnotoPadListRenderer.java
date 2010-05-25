package br.com.mcampos.controller.anoto.renderer;


import br.com.mcampos.dto.anoto.AnotoPageDTO;

import java.io.Serializable;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;


public class AnotoPadListRenderer implements ListitemRenderer, Serializable
{
    public AnotoPadListRenderer()
    {
        super();
    }

    public void render( Listitem listitem, Object object ) throws Exception
    {
        AnotoPageDTO dto = ( AnotoPageDTO )object;

        if ( dto != null ) {
            listitem.setValue( object );
            listitem.getChildren().add( new Listcell( dto.getPageAddress() ) );
            listitem.getChildren().add( new Listcell( dto.getDescription() ) );
        }
    }
}
