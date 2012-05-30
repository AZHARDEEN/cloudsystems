package br.com.mcampos.dto.accounting;


import br.com.mcampos.dto.core.SimpleTableDTO;

import java.util.ArrayList;

public class AccountingEventDTO extends SimpleTableDTO
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AccountingMaskDTO mask;
    private String history;

    private ArrayList<AccountingEventPlanDTO> items = new ArrayList<AccountingEventPlanDTO>();


    AccountingEventDTO( SimpleTableDTO simpleTableDTO )
    {
        super( simpleTableDTO );
    }

    public AccountingEventDTO( Integer integer, String string )
    {
        super( integer, string );
    }

    public AccountingEventDTO( Integer integer )
    {
        super( integer );
    }

    public AccountingEventDTO()
    {
        super();
    }

    public void setMask( AccountingMaskDTO mask )
    {
        this.mask = mask;
    }

    public AccountingMaskDTO getMask()
    {
        return mask;
    }

    public void setHistory( String history )
    {
        this.history = history;
    }

    public String getHistory()
    {
        return history;
    }

    public void setItems( ArrayList<AccountingEventPlanDTO> items )
    {
        this.items = items;
    }

    public ArrayList<AccountingEventPlanDTO> getItems()
    {
        return items;
    }

    public AccountingEventPlanDTO add( AccountingEventPlanDTO item )
    {
        if ( !getItems().contains( item ) ) {
            getItems().add( item );
            item.setParent( this );
        }
        return item;
    }

    public AccountingEventPlanDTO remove( AccountingEventPlanDTO item )
    {
        int nIndex;
        nIndex = getItems().indexOf( item );
        if ( nIndex >= 0 ) {
            item.setParent( null );
            getItems().remove( nIndex );
        }
        return item;
    }
}
