package br.com.mcampos.ejb.entity.address;

import java.io.Serializable;

public class RegionPK implements Serializable
{
    private Integer countryId;
    private Integer id;

    public RegionPK()
    {
    }

    public RegionPK( Integer ctr_id_in, Integer reg_id_in )
    {
        this.countryId = ctr_id_in;
        this.id = reg_id_in;
    }

    public boolean equals( Object other )
    {
        if (other instanceof RegionPK) {
            final RegionPK otherRegionPK = (RegionPK) other;
            final boolean areEqual =
                (otherRegionPK.countryId.equals(countryId) && otherRegionPK.id.equals(id));
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

    Integer getId()
    {
        return id;
    }

    void setId( Integer reg_id_in )
    {
        this.id = reg_id_in;
    }
}
