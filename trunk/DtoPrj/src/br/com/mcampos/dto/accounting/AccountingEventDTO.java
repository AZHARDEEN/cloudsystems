package br.com.mcampos.dto.accounting;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class AccountingEventDTO extends SimpleTableDTO
{
    private AccountingMaskDTO mask;
    private String history;


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
}
