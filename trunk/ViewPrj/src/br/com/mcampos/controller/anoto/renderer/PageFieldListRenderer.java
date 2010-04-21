package br.com.mcampos.controller.anoto.renderer;


import br.com.mcampos.dto.anoto.AnotoPageFieldDTO;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class PageFieldListRenderer implements ListitemRenderer
{
    public PageFieldListRenderer()
    {
        super();
    }

    public void render( Listitem item, Object data ) throws Exception
    {
        item.setValue ( data );
        AnotoPageFieldDTO dto = (AnotoPageFieldDTO) data;
        item.getChildren().add( new Listcell( dto.getName() ) );
    }
}
