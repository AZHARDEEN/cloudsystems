package br.com.mcampos.dto.resale;


import br.com.mcampos.dto.user.ListUserDTO;

import java.io.Serializable;

public class DealerDTO implements Serializable
{
    private Integer sequence;
    private ResaleDTO resale;
    private ListUserDTO dealer;
    private DealerTypeDTO type;


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

    public void setDealer( ListUserDTO dealer )
    {
        this.dealer = dealer;
    }

    public ListUserDTO getDealer()
    {
        return dealer;
    }

    public void setType( DealerTypeDTO type )
    {
        this.type = type;
    }

    public DealerTypeDTO getType()
    {
        return type;
    }
}
