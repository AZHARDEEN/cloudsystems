package br.com.mcampos.ejb.cloudsystem.account.costcenter.entity;

import java.io.Serializable;

public class CostAreaPK implements Serializable
{
    private Integer id;
    private Integer companyId;

    public CostAreaPK()
    {
    }

    public CostAreaPK( Integer car_id_in, Integer usr_id_in )
    {
        this.id = car_id_in;
        this.companyId = usr_id_in;
    }

    public boolean equals( Object other )
    {
        if ( other instanceof CostAreaPK ) {
            final CostAreaPK otherCostAreaPK = ( CostAreaPK )other;
            final boolean areEqual = ( otherCostAreaPK.id.equals( id ) && otherCostAreaPK.companyId.equals( companyId ) );
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

    void setId( Integer car_id_in )
    {
        this.id = car_id_in;
    }

    Integer getCompanyId()
    {
        return companyId;
    }

    void setCompanyId( Integer usr_id_in )
    {
        this.companyId = usr_id_in;
    }
}
