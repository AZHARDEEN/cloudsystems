package br.com.mcampos.dto.anode;

import br.com.mcampos.dto.core.SimpleTableDTO;
import br.com.mcampos.sysutils.SysUtils;

import java.security.InvalidParameterException;

public class FormDTO extends SimpleTableDTO
{
    String application;


    public FormDTO( SimpleTableDTO simpleTableDTO )
    {
        super( simpleTableDTO );
    }

    public FormDTO( Integer integer, String string )
    {
        super( integer, string );
    }

    public FormDTO( Integer integer )
    {
        super( integer );
    }

    public FormDTO()
    {
        super();
    }

    public FormDTO setIp( String ip )
    {
        if ( SysUtils.isEmpty( ip ) )
            throw new InvalidParameterException( "O ip do formulário não pode ser nulo ou vazio" );
        this.application = ip;
        return this;
    }

    public String getApplication()
    {
        return application;
    }
}
