package br.com.mcampos.controller.accounting;


import br.com.mcampos.dto.accounting.AccountingEventPlanDTO;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class AccountingEventPlanListRenderer implements ListitemRenderer
{
    public AccountingEventPlanListRenderer()
    {
        super();
    }

    public void render( Listitem item, Object data ) throws Exception
    {
        item.setValue( data );
        AccountingEventPlanDTO dto = ( AccountingEventPlanDTO )data;
        if ( item.getChildren() != null )
            item.getChildren().clear();
        new Listcell( dto.getPlan().toString() ).setParent( item );
        new Listcell( dto.getNature().toString() ).setParent( item );
        new Listcell( dto.getRateType().toString() ).setParent( item );
        new Listcell( dto.getRate().toString() ).setParent( item );
    }
}
