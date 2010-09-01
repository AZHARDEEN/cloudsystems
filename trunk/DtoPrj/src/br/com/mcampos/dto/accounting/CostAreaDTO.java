package br.com.mcampos.dto.accounting;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class CostAreaDTO extends SimpleTableDTO
{
    public CostAreaDTO( SimpleTableDTO simpleTableDTO )
    {
        super( simpleTableDTO );
    }

    public CostAreaDTO( Integer integer, String string )
    {
        super( integer, string );
    }

    public CostAreaDTO( Integer integer )
    {
        super( integer );
    }

    public CostAreaDTO()
    {
        super();
    }
}
