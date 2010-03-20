package br.com.mcampos.controller.anoto.renderer;


import br.com.mcampos.dto.anoto.PgcPenPageDTO;

import java.text.SimpleDateFormat;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;


public class PgcPenPageListRenderer implements ListitemRenderer
{
    SimpleDateFormat renderedDateFormat;

    public PgcPenPageListRenderer()
    {
        super();
        renderedDateFormat = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );
    }

    public void render( Listitem item, Object data ) throws Exception
    {
        item.setValue( data );
        PgcPenPageDTO dto = ( PgcPenPageDTO )data;

        if ( item.getChildren().size() == 0 ) {
            item.appendChild( new Listcell( ) );
            item.appendChild( new Listcell( ) );
            item.appendChild( new Listcell( ) );
            item.appendChild( new Listcell( ) );
        }
        ((Listcell)item.getChildren().get( 0 )).setLabel( dto.getPenPage().getPage().getPad().getForm().toString() );
        ((Listcell)item.getChildren().get( 1 )).setLabel( dto.getPenPage().getPage().toString() );
        ((Listcell)item.getChildren().get( 2 )).setLabel( dto.getPenPage().getPen().toString() );
        try {
            ((Listcell)item.getChildren().get( 3 )).setLabel( renderedDateFormat.format( dto.getPgc().getInsertDate() ) );
        }
        catch ( Exception e ) {
            e = null;
        }
    }
}
