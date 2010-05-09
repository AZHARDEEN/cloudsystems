package br.com.mcampos.ejb.cloudsystem.anoto.pgc.property;

import java.io.Serializable;

public class PgcPropertyPK implements Serializable
{
    private Integer pgc_id_in;
    private Integer pgp_id_in;
    private Integer pgp_seq_in;

    public PgcPropertyPK()
    {
    }

    public PgcPropertyPK( Integer pgc_id_in, Integer pgp_id_in, Integer pgp_seq_in )
    {
        this.pgc_id_in = pgc_id_in;
        this.pgp_id_in = pgp_id_in;
        this.pgp_seq_in = pgp_seq_in;
    }

    public boolean equals( Object other )
    {
        if (other instanceof PgcPropertyPK) {
            final PgcPropertyPK otherPgcPropertyPK = (PgcPropertyPK) other;
            final boolean areEqual =
                (otherPgcPropertyPK.pgc_id_in.equals(pgc_id_in) && otherPgcPropertyPK.pgp_id_in.equals(pgp_id_in) && otherPgcPropertyPK.pgp_seq_in.equals(pgp_seq_in));
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Integer getPgc_id_in()
    {
        return pgc_id_in;
    }

    void setPgc_id_in( Integer pgc_id_in )
    {
        this.pgc_id_in = pgc_id_in;
    }

    Integer getPgp_id_in()
    {
        return pgp_id_in;
    }

    void setPgp_id_in( Integer pgp_id_in )
    {
        this.pgp_id_in = pgp_id_in;
    }

    Integer getPgp_seq_in()
    {
        return pgp_seq_in;
    }

    void setPgp_seq_in( Integer pgp_seq_in )
    {
        this.pgp_seq_in = pgp_seq_in;
    }
}
