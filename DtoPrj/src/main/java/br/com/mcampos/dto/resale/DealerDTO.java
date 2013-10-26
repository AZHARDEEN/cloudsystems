package br.com.mcampos.dto.resale;


import br.com.mcampos.dto.user.ListUserDTO;

import java.io.Serializable;

public class DealerDTO implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4761925578324308602L;
	private Integer sequence;
    private ResaleDTO resale;
    private ListUserDTO person;
    private DealerTypeDTO type;
    private String penId;


    public DealerDTO()
    {

    }

    public void setSequence( Integer sequence )
    {
        this.sequence = sequence;
    }

    public Integer getSequence()
    {
        return sequence;
    }

    public void setResale( ResaleDTO resale )
    {
        this.resale = resale;
    }

    public ResaleDTO getResale()
    {
        return resale;
    }

    public void setPerson( ListUserDTO dealer )
    {
        this.person = dealer;
    }

    public ListUserDTO getPerson()
    {
        return person;
    }

    public void setType( DealerTypeDTO type )
    {
        this.type = type;
    }

    public DealerTypeDTO getType()
    {
        return type;
    }

    @Override
    public String toString()
    {
        if ( getType().getId().equals( DealerTypeDTO.typeDealer ) )
            return getPerson().toString();
        else
            return getPerson().toString() + " - " + getType().toString();
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj == null )
            return false;

        if ( obj instanceof Integer ) {
            return getPerson().getId().equals( ( ( Integer )obj ) );
        }
        else if ( obj instanceof DealerDTO ) {
            return getPerson().equals( ( ( DealerDTO )obj ).getPerson() );
        }
        else
            return false;
    }

    public void setPenId( String penId )
    {
        this.penId = penId;
    }

    public String getPenId()
    {
        return penId;
    }
}
