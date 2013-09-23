package br.com.mcampos.dto.system;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class SystemUserPropertyDTO extends SimpleTableDTO
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7561186676671820355L;
	FieldTypeDTO type;

    public SystemUserPropertyDTO( SimpleTableDTO simpleTableDTO )
    {
        super( simpleTableDTO );
    }

    public SystemUserPropertyDTO( Integer integer, String string )
    {
        super( integer, string );
    }

    public SystemUserPropertyDTO( Integer integer )
    {
        super( integer );
    }

    public SystemUserPropertyDTO()
    {
        super();
    }

    public void setType( FieldTypeDTO type )
    {
        this.type = type;
    }

    public FieldTypeDTO getType()
    {
        return type;
    }
}
