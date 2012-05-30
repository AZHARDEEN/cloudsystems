package br.com.mcampos.ejb.cloudsystem.resale.dealer.entity;


import br.com.mcampos.ejb.cloudsystem.resale.entity.Resale;

import java.io.Serializable;

public class DealerPK implements Serializable
{
    private Integer sequence;
    private Integer resaleSequence;
    private Integer userId;

    public DealerPK()
    {
    }

    public DealerPK( Resale resale, Integer sequence )
    {
        setUserId( resale.getCompany().getId() );
        setResaleSequence( resale.getSequence() );
        setSequence( sequence );
    }

    public boolean equals( Object other )
    {
        if ( other instanceof DealerPK ) {
            final DealerPK otherDealerPK = ( DealerPK )other;
            final boolean areEqual =
                ( otherDealerPK.sequence.equals( sequence ) && otherDealerPK.resaleSequence.equals( resaleSequence ) &&
                  otherDealerPK.userId.equals( userId ) );
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Integer getSequence()
    {
        return sequence;
    }

    void setSequence( Integer dea_sequence_in )
    {
        this.sequence = dea_sequence_in;
    }

    Integer getResaleSequence()
    {
        return resaleSequence;
    }

    void setResaleSequence( Integer rsl_sequence_in )
    {
        this.resaleSequence = rsl_sequence_in;
    }

    Integer getUserId()
    {
        return userId;
    }

    void setUserId( Integer usr_id_in )
    {
        this.userId = usr_id_in;
    }
}
