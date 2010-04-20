package br.com.mcampos.dto.system;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class FieldTypeDTO extends SimpleTableDTO
{
    public FieldTypeDTO( SimpleTableDTO simpleTableDTO )
    {
        super( simpleTableDTO );
    }

    public FieldTypeDTO( Integer integer, String string )
    {
        super( integer, string );
    }

    public FieldTypeDTO( Integer integer )
    {
        super( integer );
    }

    public FieldTypeDTO()
    {
        super();
    }
}
