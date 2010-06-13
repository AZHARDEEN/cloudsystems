package br.com.mcampos.dto.system;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class RevisionStatusDTO extends SimpleTableDTO
{
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
