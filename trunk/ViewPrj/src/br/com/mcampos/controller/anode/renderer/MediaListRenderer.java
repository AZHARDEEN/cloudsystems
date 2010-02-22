package br.com.mcampos.controller.anode.renderer;

import br.com.mcampos.dto.anode.FormDTO;

import br.com.mcampos.dto.system.MediaDTO;

import java.util.List;

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
        MediaDTO dto = ( MediaDTO )data;

        item.setValue( dto );
        item.getChildren().add( new Listcell( dto.getName() ) );
        item.getChildren().add( new Listcell( dto.getFormat() ) );
        item.getChildren().add( new Listcell( dto.getSize().toString() ) );
    }
}
