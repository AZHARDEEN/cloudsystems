package br.com.mcampos.controller.admin.resale;


import br.com.mcampos.dto.resale.ResaleDTO;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class ResaleListRenderer implements ListitemRenderer
{
    public ResaleListRenderer()
    {
        super();
    }

    public void render( Listitem item, Object data ) throws Exception
    {
        ResaleDTO dto = ( ResaleDTO )data;
        item.setValue( item );
        if ( item.getChildren().size() == 0 ) {
            new Listcell( dto.getCode() ).setParent( item );
            new Listcell( dto.getResale().getClient().getNickName() ).setParent( item );
        }
        else {
            Listcell cell = ( Listcell )item.getChildren().get( 0 );
            cell.setLabel( dto.getCode() );
            cell = ( Listcell )item.getChildren().get( 1 );
            cell.setLabel( dto.getResale().getClient().getNickName() );
        }
    }
}
