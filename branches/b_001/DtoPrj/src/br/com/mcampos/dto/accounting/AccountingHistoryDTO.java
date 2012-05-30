package br.com.mcampos.dto.accounting;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class AccountingHistoryDTO extends SimpleTableDTO
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -865934433777980024L;
	private String history;

    public AccountingHistoryDTO( SimpleTableDTO simpleTableDTO )
    {
        super( simpleTableDTO );
    }

    public AccountingHistoryDTO( Integer integer, String string )
    {
        super( integer, string );
    }

    public AccountingHistoryDTO( Integer integer )
    {
        super( integer );
    }

    public AccountingHistoryDTO()
    {
        super();
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
