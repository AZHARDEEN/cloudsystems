package br.com.mcampos.controller.anoto.renderer;


import br.com.mcampos.dto.anoto.AnotoResultList;
import br.com.mcampos.dto.anoto.PgcFieldDTO;
import br.com.mcampos.sysutils.SysUtils;

import java.text.SimpleDateFormat;

import java.util.List;

import org.zkoss.zul.Listcell;
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

        if ( item.getChildren().size() == 0 ) {
            item.getChildren().clear();
        }
        new Listcell( "" + ( item.getListbox().getIndexOfItem( item ) + 1 ) ).setParent( item );
        try {
            new Listcell( renderedDateFormat.format( dto.getPgcPage().getPgc().getInsertDate() ) ).setParent( item );
        }
        catch ( Exception e ) {
            e = null;
        }
        new Listcell( dto.getUserName() ).setParent( item );
        new Listcell( dto.getCellNumber() ).setParent( item );
        new Listcell( dto.getAttach() ? "SIM" : "" ).setParent( item );
        new Listcell( dto.getPgcPage().getRevisionStatus().getId().equals( 1 ) ? "" : "SIM" ).setParent( item );

        showFormFields( item, dto.getFields() );
    }

    private void showFormFields( Listitem item, List<PgcFieldDTO> fields )
    {
        if ( SysUtils.isEmpty( fields ) )
            return;
        for ( PgcFieldDTO field : fields ) {
            if ( field.getName().equals( "CEP" ) )
                continue;
            new Listcell( field.getValue() ).setParent( item );
        }
    }
}
