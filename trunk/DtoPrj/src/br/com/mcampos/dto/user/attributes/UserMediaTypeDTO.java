package br.com.mcampos.dto.user.attributes;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class UserMediaTypeDTO extends SimpleTableDTO
{
    public UserMediaTypeDTO( SimpleTableDTO simpleTableDTO )
    {
        super( simpleTableDTO );
    }

    public UserMediaTypeDTO( Integer integer, String string )
    {
        super( integer, string );
    }

    public UserMediaTypeDTO( Integer integer )
    {
        super( integer );
    }

    public UserMediaTypeDTO()
    {
        super();
    }
}
