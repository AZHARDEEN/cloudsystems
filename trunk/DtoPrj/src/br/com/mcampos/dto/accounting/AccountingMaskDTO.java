package br.com.mcampos.dto.accounting;

import java.io.Serializable;


public class AccountingMaskDTO implements Serializable
{
    private String description;
    private Integer id;
    private String mask;


    public AccountingMaskDTO()
    {
        super();
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public void setMask( String mask )
    {
        this.mask = mask;
    }

    public String getMask()
    {
        return mask;
    }

    @Override
    public String toString()
    {
        return getDescription();
    }
}
