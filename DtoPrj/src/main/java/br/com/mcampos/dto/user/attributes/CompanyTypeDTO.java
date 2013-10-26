package br.com.mcampos.dto.user.attributes;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class CompanyTypeDTO extends SimpleTableDTO 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -808264388565088627L;

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
