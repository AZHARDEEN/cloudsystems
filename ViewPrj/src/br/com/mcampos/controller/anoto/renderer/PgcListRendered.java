package br.com.mcampos.controller.anoto.renderer;


import br.com.mcampos.dto.anoto.PGCDTO;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class PgcListRendered implements ListitemRenderer
{
    public PgcListRendered()
    {
        super();
    }

    public void render( Listitem item, Object data )
    {
        item.setValue( data );
        PGCDTO dto = ( PGCDTO )data;

        item.appendChild( new Listcell( dto.getMedia().getName() ) );
    }
}
