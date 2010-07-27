package br.com.mcampos.controller.anoto.renderer;


import br.com.mcampos.dto.anoto.AnotoResultList;
import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.sysutils.SysUtils;

import java.text.SimpleDateFormat;

import java.util.List;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;


public class EmbratelResultListRenderer implements ListitemRenderer
{
    SimpleDateFormat renderedDateFormat;

    public EmbratelResultListRenderer()
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
        }

        ( ( Listcell )item.getChildren().get( nIndex++ ) ).setLabel( "" + ( item.getListbox().getIndexOfItem( item ) + 1 ) );
        try {
            ( ( Listcell )item.getChildren().get( nIndex++ ) )
                .setLabel( renderedDateFormat.format( dto.getPgcPage().getPgc().getInsertDate() ) );
        }
        catch ( Exception e ) {
            e = null;
        }
        ( ( Listcell )item.getChildren().get( nIndex++ ) ).setLabel( dto.getUserName() );
        ( ( Listcell )item.getChildren().get( nIndex++ ) ).setLabel( dto.getCellNumber() );
        ( ( Listcell )item.getChildren().get( nIndex++ ) ).setLabel( dto.getAttach() ? "SIM" : "" );

        showFormFields( item, dto.getFields() );
    }

    private void showFormFields( Listitem item, List<PgcFieldDTO> fields )
    {
        if ( SysUtils.isEmpty( fields ) )
            return;
        Listhead head = item.getListbox().getListhead();
        if ( head.getChildren().size() < 6 ) {
            for ( PgcFieldDTO field : fields ) {
                if ( field.getName().equals( "CEP" ) )
                    continue;
                Listheader h = new Listheader( field.getName() );
                h.setWidth( ( field.getName().length() * 10 ) + "px" );
                head.appendChild( h );
            }
        }
        for ( PgcFieldDTO field : fields ) {
            if ( field.getName().equals( "CEP" ) )
                continue;
            Listcell cell = new Listcell( field.getValue() );
            item.appendChild( cell );
        }
    }
}
