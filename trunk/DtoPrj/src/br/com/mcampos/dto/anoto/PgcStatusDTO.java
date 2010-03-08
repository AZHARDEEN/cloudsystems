package br.com.mcampos.dto.anoto;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class PgcStatusDTO extends SimpleTableDTO
{
    public static final int statusOk = 1;

    public PgcStatusDTO( SimpleTableDTO simpleTableDTO )
    {
        super( simpleTableDTO );
    }

    public PgcStatusDTO( Integer integer, String string )
    {
        super( integer, string );
    }

    public PgcStatusDTO( Integer integer )
    {
        super( integer );
    }

    public PgcStatusDTO()
    {
        super();
    }
}
