package br.com.mcampos.dto.user.login;

import br.com.mcampos.dto.core.DisplayNameDTO;
import br.com.mcampos.dto.core.SimpleTableDTO;

public class AccessLogTypeDTO extends SimpleTableDTO
{
    public AccessLogTypeDTO( SimpleTableDTO simpleTableDTO )
    {
        super( simpleTableDTO );
    }

    public AccessLogTypeDTO( Integer integer, String string )
    {
        super( integer, string );
    }

    public AccessLogTypeDTO( Integer integer )
    {
        super( integer );
    }

    public AccessLogTypeDTO()
    {
        super();
    }
}
