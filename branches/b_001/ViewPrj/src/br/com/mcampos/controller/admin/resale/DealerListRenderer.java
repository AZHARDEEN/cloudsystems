package br.com.mcampos.controller.admin.resale;


import br.com.mcampos.dto.resale.DealerDTO;

import java.io.Serializable;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;


public class DealerListRenderer implements ListitemRenderer, Serializable
{
    public DealerListRenderer()
    {
        super();
    }

    public void render( Listitem item, Object data ) throws Exception
    {
        DealerDTO dto = ( DealerDTO )data;
        item.setValue( item );
        if ( item.getChildren().size() == 0 ) {
            new Listcell( dto.getSequence().toString() ).setParent( item );
            new Listcell( dto.getPerson().toString() ).setParent( item );
            new Listcell( dto.getType().toString() ).setParent( item );
        }
        else {
            Listcell cell = ( Listcell )item.getChildren().get( 0 );
            cell.setLabel( dto.getSequence().toString() );
            cell = ( Listcell )item.getChildren().get( 1 );
            cell.setLabel( dto.getPerson().toString() );
            cell = ( Listcell )item.getChildren().get( 2 );
            cell.setLabel( dto.getType().toString() );
        }
    }
}
