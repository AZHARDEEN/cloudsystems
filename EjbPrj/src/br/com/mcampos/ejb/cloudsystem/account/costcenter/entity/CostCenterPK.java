package br.com.mcampos.ejb.cloudsystem.account.costcenter.entity;

import java.io.Serializable;

public class CostCenterPK implements Serializable
{
    private Integer areaId;
    private Integer id;
    private Integer companyId;

    public CostCenterPK()
    {
    }

    public CostCenterPK( CostArea area, Integer id )
    {
        setId( id );
        setCompanyId( area.getCompany().getId() );
        setAreaId( area.getId() );
    }

    public boolean equals( Object other )
    {
        if ( other instanceof CostCenterPK ) {
            final CostCenterPK otherCostCenterPK = ( CostCenterPK )other;
            final boolean areEqual =
                ( otherCostCenterPK.areaId.equals( areaId ) && otherCostCenterPK.id.equals( id ) && otherCostCenterPK.companyId.equals( companyId ) );
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Integer getAreaId()
    {
        return areaId;
    }

    void setAreaId( Integer car_id_in )
    {
        this.areaId = car_id_in;
    }

    Integer getId()
    {
        return id;
    }

    void setId( Integer cct_id_in )
    {
        this.id = cct_id_in;
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
