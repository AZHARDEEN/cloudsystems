package br.com.mcampos.dto.accounting;

import java.io.Serializable;

public class AccountingNatureDTO implements Serializable
{
    private String id;
    private String description;


    public AccountingNatureDTO()
    {
    }


    public AccountingNatureDTO( String id )
    {
        setId( id );
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    @Override
    public String toString()
    {
        return getDescription();
    }

    @Override
    public boolean equals( Object obj )
    {
        return getId().equals( ( ( AccountingNatureDTO )obj ).getId() );
    }
}
