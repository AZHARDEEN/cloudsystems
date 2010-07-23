package br.com.mcampos.dto.resale;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class DealerTypeDTO extends SimpleTableDTO
{
    public DealerTypeDTO( SimpleTableDTO simpleTableDTO )
    {
        super( simpleTableDTO );
    }

    public DealerTypeDTO( Integer integer, String string )
    {
        super( integer, string );
    }

    public DealerTypeDTO( Integer integer )
    {
        super( integer );
    }

    public DealerTypeDTO()
    {
        super();
    }
}
