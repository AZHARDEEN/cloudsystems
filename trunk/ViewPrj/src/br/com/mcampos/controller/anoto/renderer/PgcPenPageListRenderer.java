package br.com.mcampos.controller.anoto.renderer;

import br.com.mcampos.dto.anoto.PgcPenPageDTO;

import java.text.SimpleDateFormat;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class PgcPenPageListRenderer implements ListitemRenderer
{
    public PgcPenPageListRenderer()
    {
        super();
    }

    public void render( Listitem item, Object data ) throws Exception
    {
        item.setValue( data );
        PgcPenPageDTO dto = ( PgcPenPageDTO )data;

        if ( item.getChildren().size() > 0 )
            item.getChildren().clear();
        item.appendChild( new Listcell( dto.getPenPage().getPage().getPad().getForm().toString() ) );
        item.appendChild( new Listcell( dto.getPenPage().getPage().toString() ) );
        item.appendChild( new Listcell( dto.getPenPage().getPen().toString() ) );

        try {
            SimpleDateFormat df = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );
            item.appendChild( new Listcell( df.format( dto.getPgc().getInsertDate() ) ) );
        }
        catch ( Exception e ) {
            e = null;
        }
    }
}
