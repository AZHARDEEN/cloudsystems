package br.com.mcampos.dto.system;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class RevisionStatusDTO extends SimpleTableDTO
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2905986941543617230L;

	public RevisionStatusDTO( SimpleTableDTO simpleTableDTO )
    {
        super( simpleTableDTO );
    }

    public RevisionStatusDTO( Integer integer, String string )
    {
        super( integer, string );
    }

    public RevisionStatusDTO( Integer integer )
    {
        super( integer );
    }

    public RevisionStatusDTO()
    {
        super();
    }
}
