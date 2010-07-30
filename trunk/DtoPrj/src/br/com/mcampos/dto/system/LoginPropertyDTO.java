package br.com.mcampos.dto.system;

import java.io.Serializable;

public class LoginPropertyDTO implements Serializable
{
    SystemUserPropertyDTO property;
    String value;

    public LoginPropertyDTO()
    {
        super();
    }

    public LoginPropertyDTO( SystemUserPropertyDTO property )
    {
        super();
        setProperty( property );
    }

    public void setProperty( SystemUserPropertyDTO property )
    {
        this.property = property;
    }

    public SystemUserPropertyDTO getProperty()
    {
        return property;
    }

    public void setValue( String value )
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}
