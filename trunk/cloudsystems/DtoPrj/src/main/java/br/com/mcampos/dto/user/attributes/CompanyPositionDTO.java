package br.com.mcampos.dto.user.attributes;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class CompanyPositionDTO extends SimpleTableDTO
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2194317094509955923L;

	public CompanyPositionDTO( SimpleTableDTO simpleTableDTO )
    {
        super( simpleTableDTO );
    }

    public CompanyPositionDTO( Integer integer, String string )
    {
        super( integer, string );
    }

    public CompanyPositionDTO( Integer integer )
    {
        super( integer );
    }

    public CompanyPositionDTO()
    {
        super();
    }
}
