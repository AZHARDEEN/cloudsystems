package br.com.mcampos.ejb.cloudsystem.resale.entity;


import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;

import java.io.Serializable;

public class ResalePK implements Serializable
{
    private Integer sequence;
    private Integer userId;

    public ResalePK()
    {
    }

    public ResalePK( Company company, Integer sequence )
    {
        this.sequence = sequence;
        this.userId = company.getId();
    }

    public boolean equals( Object other )
    {
        if ( other instanceof ResalePK ) {
            final ResalePK otherResalePK = ( ResalePK )other;
            final boolean areEqual = ( otherResalePK.sequence.equals( sequence ) && otherResalePK.userId.equals( userId ) );
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

    void setSequence( Integer rsl_sequence_in )
    {
        this.sequence = rsl_sequence_in;
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
