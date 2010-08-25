package br.com.mcampos.controller.accounting;


import br.com.mcampos.dto.accounting.AccountingPlanDTO;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class AccountingPlanListRenderer implements ListitemRenderer
{
    public AccountingPlanListRenderer()
    {
        super();
    }

    public void render( Listitem item, Object data ) throws Exception
    {
        item.setValue( data );
        AccountingPlanDTO dto = ( AccountingPlanDTO )data;
        if ( item.getChildren() != null )
            item.getChildren().clear();
        new Listcell( dto.getNumber() ).setParent( item );
        new Listcell( dto.getDescription() ).setParent( item );
    }
}
