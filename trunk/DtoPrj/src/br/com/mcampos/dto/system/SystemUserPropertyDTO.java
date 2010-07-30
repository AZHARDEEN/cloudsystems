package br.com.mcampos.dto.system;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class SystemUserPropertyDTO extends SimpleTableDTO
{
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
