package br.com.mcampos.controller.anoto.renderer;


import br.com.mcampos.dto.anoto.AnotoPageDTO;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class AnotoPageListRenderer implements ListitemRenderer
{
    public AnotoPageListRenderer()
    {
        super();
    }

    public void render( Listitem item, Object data ) throws Exception
    {
        AnotoPageDTO dto = ( AnotoPageDTO )data;

        item.setValue( dto );
        item.getChildren().add( new Listcell( dto.getPageAddress() ) );
    }
}
