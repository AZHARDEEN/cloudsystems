package br.com.mcampos.dto.security;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class RoleDTO extends SimpleTableDTO
{
    public RoleDTO( SimpleTableDTO simpleTableDTO )
    {
        super( simpleTableDTO );
    }

    public RoleDTO( Integer integer, String string )
    {
        super( integer, string );
    }

    public RoleDTO( Integer integer )
    {
        super( integer );
    }

    public RoleDTO()
    {
        super();
    }
}
