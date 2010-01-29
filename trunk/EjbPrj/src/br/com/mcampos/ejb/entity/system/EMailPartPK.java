package br.com.mcampos.ejb.entity.system;

import java.io.Serializable;

public class EMailPartPK implements Serializable
{
    private Integer id;
    private Integer sequencial;

    public EMailPartPK()
    {
    }

    public EMailPartPK( Integer id, Integer sequencial )
    {
        this.id = id;
        this.sequencial = sequencial;
    }

    public boolean equals( Object other )
    {
        if (other instanceof EMailPartPK) {
            final EMailPartPK otherEMailPartPK = (EMailPartPK) other;
            final boolean areEqual = (otherEMailPartPK.id.equals(id) && otherEMailPartPK.sequencial.equals(sequencial));
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Integer getId()
    {
        return id;
    }

    void setId( Integer id )
    {
        this.id = id;
    }

    Integer getSequencial()
    {
        return sequencial;
    }

    void setSequencial( Integer sequencial )
    {
        this.sequencial = sequencial;
    }
}
