package br.com.imstecnologia.dto.sped;


import br.com.mcampos.dto.core.SimpleTableDTO;

import java.util.Date;

public class BaseSpedDTO extends SimpleTableDTO
{
    private String code;
    private Date from;
    private Date to;

    public BaseSpedDTO( SimpleTableDTO simpleTableDTO )
    {
        super( simpleTableDTO );
    }

    public BaseSpedDTO( Integer integer, String string )
    {
        super( integer, string );
    }

    public BaseSpedDTO( Integer integer )
    {
        super( integer );
    }

    public BaseSpedDTO()
    {
        super();
    }

    public void setCode( String code )
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }

    public void setFrom( Date from )
    {
        this.from = from;
    }

    public Date getFrom()
    {
        return from;
    }

    public void setTo( Date to )
    {
        this.to = to;
    }

    public Date getTo()
    {
        return to;
    }
}
