package br.com.mcampos.controller.anoto.renderer;


import br.com.mcampos.dto.anoto.PgcPropertyDTO;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class PgcPropertyListRenderer implements ListitemRenderer
{
    public PgcPropertyListRenderer()
    {
        super();
    }

    public void render( Listitem item, Object data ) throws Exception
    {
        item.setValue( data );
        PgcPropertyDTO dto = ( PgcPropertyDTO )data;
        item.getChildren().add( new Listcell( dto.getValue() ) );
    }
}
