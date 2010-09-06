package br.com.mcampos.dto.accounting;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class AccountingRateTypeDTO extends SimpleTableDTO
{
    public AccountingRateTypeDTO( SimpleTableDTO simpleTableDTO )
    {
        super( simpleTableDTO );
    }

    public AccountingRateTypeDTO( Integer integer, String string )
    {
        super( integer, string );
    }

    public AccountingRateTypeDTO( Integer integer )
    {
        super( integer );
    }

    public AccountingRateTypeDTO()
    {
        super();
    }
}
