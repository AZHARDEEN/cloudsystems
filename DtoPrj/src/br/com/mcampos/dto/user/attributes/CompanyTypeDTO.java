package br.com.mcampos.dto.user.attributes;

import br.com.mcampos.dto.core.DisplayNameDTO;
import br.com.mcampos.dto.core.SimpleTableDTO;

public class CompanyTypeDTO extends SimpleTableDTO 
{
    public CompanyTypeDTO( SimpleTableDTO simpleTableDTO )
    {
        super( simpleTableDTO );
    }

    public CompanyTypeDTO( Integer integer, String string )
    {
        super( integer, string );
    }

    public CompanyTypeDTO( Integer integer )
    {
        super( integer );
    }

    public CompanyTypeDTO()
    {
        super();
    }
}
