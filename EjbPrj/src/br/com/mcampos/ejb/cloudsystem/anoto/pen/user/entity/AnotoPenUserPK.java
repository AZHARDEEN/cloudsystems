package br.com.mcampos.ejb.cloudsystem.anoto.pen.user.entity;

import java.io.Serializable;

public class AnotoPenUserPK implements Serializable
{
    private Integer sequence;
    private String penId;

    public AnotoPenUserPK()
    {
    }

    public AnotoPenUserPK( AnotoPenUser entity )
    {
        this.sequence = entity.getSequence();
        this.penId = entity.getPenId();

    }

    public boolean equals( Object other )
    {
        if ( other instanceof AnotoPenUserPK ) {
            final AnotoPenUserPK otherAnotoPenUserPK = ( AnotoPenUserPK )other;
            final boolean areEqual =
                ( otherAnotoPenUserPK.sequence.equals( sequence ) && otherAnotoPenUserPK.penId.equals( penId ) );
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

    void setSequence( Integer apu_seq_in )
    {
        this.sequence = apu_seq_in;
    }

    String getPenId()
    {
        return penId;
    }

    void setPenId( String pen_id_ch )
    {
        this.penId = pen_id_ch;
    }
}
