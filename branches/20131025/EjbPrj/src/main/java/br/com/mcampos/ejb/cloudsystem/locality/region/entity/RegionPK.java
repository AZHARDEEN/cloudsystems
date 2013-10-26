package br.com.mcampos.ejb.cloudsystem.locality.region.entity;


import java.io.Serializable;

public class RegionPK implements Serializable
{
    private String countryId;
    private Integer id;

    public RegionPK()
    {
    }

    public RegionPK( String ctr_code_ch, Integer reg_id_in )
    {
        this.countryId = ctr_code_ch;
        this.id = reg_id_in;
    }

    public boolean equals( Object other )
    {
        if ( other instanceof RegionPK ) {
            final RegionPK otherRegionPK = ( RegionPK )other;
            final boolean areEqual = ( otherRegionPK.countryId.equals( countryId ) && otherRegionPK.id.equals( id ) );
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    String getCountryId()
    {
        return countryId;
    }

    void setCountryId( String ctr_code_ch )
    {
        this.countryId = ctr_code_ch;
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
