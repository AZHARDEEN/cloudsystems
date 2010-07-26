package br.com.mcampos.dto.resale;


import br.com.mcampos.dto.user.ClientDTO;

import java.io.Serializable;

public class ResaleDTO implements Serializable
{
    private String code;
    private Integer sequence;
    private ClientDTO resale;

    public ResaleDTO()
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

    public void setSequence( Integer sequence )
    {
        this.sequence = sequence;
    }

    public Integer getSequence()
    {
        return sequence;
    }

    public void setResale( ClientDTO resale )
    {
        this.resale = resale;
    }

    public ClientDTO getResale()
    {
        return resale;
    }

    @Override
    public String toString()
    {
        return getResale().getClient().toString();
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj instanceof Integer ) {
            return getSequence().equals( ( ( Integer )obj ) );
        }
        else if ( obj instanceof ResaleDTO ) {
            return getSequence().equals( ( ( ResaleDTO )obj ).getSequence() );
        }
        else
            return false;
    }
}
