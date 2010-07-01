package br.com.mcampos.controller.anoto.renderer;


import br.com.mcampos.dto.anoto.AnotoResultList;

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
        AnotoResultList dto = ( AnotoResultList )data;
        int nIndex = 0;

        if ( item.getChildren().size() == 0 ) {
            item.appendChild( new Listcell() );
            item.appendChild( new Listcell() );
            item.appendChild( new Listcell() );
            item.appendChild( new Listcell() );
            item.appendChild( new Listcell() );
            item.appendChild( new Listcell() );
            item.appendChild( new Listcell() );
            item.appendChild( new Listcell() );
            item.appendChild( new Listcell() );
            item.appendChild( new Listcell() );
            item.appendChild( new Listcell() );
            item.appendChild( new Listcell() );
        }

        ( ( Listcell )item.getChildren().get( nIndex++ ) ).setLabel( "" + ( item.getListbox().getIndexOfItem( item ) + 1 ) );
        ( ( Listcell )item.getChildren().get( nIndex++ ) ).setLabel( dto.getForm().toString() );
        Integer aux = dto.getPgcPage().getBookId() + 1;
        ( ( Listcell )item.getChildren().get( nIndex++ ) ).setLabel( aux.toString() );
        aux = dto.getPgcPage().getPageId() + 1;
        ( ( Listcell )item.getChildren().get( nIndex++ ) ).setLabel( aux.toString() );
        ( ( Listcell )item.getChildren().get( nIndex++ ) ).setLabel( dto.getPen().toString() );
        try {
            ( ( Listcell )item.getChildren().get( nIndex++ ) ).setLabel( renderedDateFormat.format( dto.getPgcPage().getPgc().getInsertDate() ) );
        }
        catch ( Exception e ) {
            e = null;
        }
        ( ( Listcell )item.getChildren().get( nIndex++ ) ).setLabel( dto.getUserName() );
        ( ( Listcell )item.getChildren().get( nIndex++ ) ).setLabel( dto.getEmail() );
        ( ( Listcell )item.getChildren().get( nIndex++ ) ).setLabel( dto.getCellNumber() );
        ( ( Listcell )item.getChildren().get( nIndex++ ) ).setLabel( dto.getLatitude() );
        ( ( Listcell )item.getChildren().get( nIndex++ ) ).setLabel( dto.getLongitude() );
        ( ( Listcell )item.getChildren().get( nIndex++ ) ).setLabel( dto.getBarcodeValue() );
    }
}
