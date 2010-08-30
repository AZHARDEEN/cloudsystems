package br.com.mcampos.controller.accounting;


import br.com.mcampos.dto.accounting.AccountingMaskDTO;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class AccountingMaskListRenderer implements ListitemRenderer
{
    public AccountingMaskListRenderer()
    {
        super();
    }

    public void render( Listitem item, Object data ) throws Exception
    {
        item.setValue( data );
        AccountingMaskDTO dto = ( AccountingMaskDTO )data;
        if ( item.getChildren() != null )
            item.getChildren().clear();
        new Listcell( dto.getId().toString() ).setParent( item );
        new Listcell( dto.getDescription() ).setParent( item );
        new Listcell( dto.getMask() ).setParent( item );
    }
}
