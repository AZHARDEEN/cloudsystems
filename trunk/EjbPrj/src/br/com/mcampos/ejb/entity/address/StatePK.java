package br.com.mcampos.ejb.entity.address;

import java.io.Serializable;

public class StatePK implements Serializable
{
    private Integer countryId;
    private Integer regionId;
    private Integer id;

    public StatePK()
    {
    }

    public StatePK( Integer ctr_id_in, Integer reg_id_in, Integer sta_id_in )
    {
        this.countryId = ctr_id_in;
        this.regionId = reg_id_in;
        this.id = sta_id_in;
    }

    public boolean equals( Object other )
    {
        if (other instanceof StatePK) {
            final StatePK otherStatePK = (StatePK) other;
            final boolean areEqual =
                (otherStatePK.countryId.equals(countryId) && otherStatePK.regionId.equals(regionId) && otherStatePK.id.equals(id));
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Integer getCountryId()
    {
        return countryId;
    }

    void setCountryId( Integer ctr_id_in )
    {
        this.countryId = ctr_id_in;
    }

    Integer getRegionId()
    {
        return regionId;
    }

    void setRegionId( Integer reg_id_in )
    {
        this.regionId = reg_id_in;
    }

    Integer getId()
    {
        return id;
    }

    void setId( Integer sta_id_in )
    {
        this.id = sta_id_in;
    }
}
