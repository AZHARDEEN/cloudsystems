package br.com.mcampos.ejb.cloudsystem.anoto.pgc.property.entity;

import java.io.Serializable;

public class PgcPropertyPK implements Serializable
{
    private Integer pgcId;
    private Integer id;
    private Integer sequence;

    public PgcPropertyPK()
    {
    }

    public PgcPropertyPK( Integer pgc_id_in, Integer pgp_id_in, Integer pgp_seq_in )
    {
        this.pgcId = pgc_id_in;
        this.id = pgp_id_in;
        this.sequence = pgp_seq_in;
    }

    public boolean equals( Object other )
    {
        if ( other instanceof PgcPropertyPK ) {
            final PgcPropertyPK otherPgcPropertyPK = ( PgcPropertyPK )other;
            final boolean areEqual =
                ( otherPgcPropertyPK.pgcId.equals( pgcId ) && otherPgcPropertyPK.id.equals( id ) && otherPgcPropertyPK.sequence.equals( sequence ) );
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Integer getPgcId()
    {
        return pgcId;
    }

    void setPgcId( Integer pgc_id_in )
    {
        this.pgcId = pgc_id_in;
    }

    Integer getId()
    {
        return id;
    }

    void setId( Integer pgp_id_in )
    {
        this.id = pgp_id_in;
    }

    Integer getSequence()
    {
        return sequence;
    }

    void setSequence( Integer pgp_seq_in )
    {
        this.sequence = pgp_seq_in;
    }
}
