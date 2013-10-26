package br.com.mcampos.dto.resale;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class DealerTypeDTO extends SimpleTableDTO
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3492007721608854405L;
	public final static Integer typeDealer = 1;

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
