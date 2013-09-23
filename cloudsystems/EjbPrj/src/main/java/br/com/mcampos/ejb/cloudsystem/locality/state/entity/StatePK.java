package br.com.mcampos.ejb.cloudsystem.locality.state.entity;


import br.com.mcampos.dto.address.StateDTO;

import java.io.Serializable;

import java.security.InvalidParameterException;


public class StatePK implements Serializable
{
    private String countryId;
    private Integer regionId;
    private Integer id;

    public StatePK()
    {
    }

    public StatePK( String ctr_code_ch, Integer reg_id_in, Integer sta_id_in )
    {
        setCountryId( ctr_code_ch );
        setRegionId( reg_id_in );
        setId( sta_id_in );
    }

    public StatePK( StateDTO dto )
    {
        if ( dto == null )
            throw new InvalidParameterException( "StateDTO could not be null " );
        setCountryId( dto.getRegion().getCountry().getId() );
        setRegionId( dto.getRegion().getId() );
        setId( dto.getId() );
    }

    public boolean equals( Object other )
    {
        if ( other instanceof StatePK ) {
            final StatePK otherStatePK = ( StatePK )other;
            final boolean areEqual =
                ( otherStatePK.countryId.equals( countryId ) && otherStatePK.regionId.equals( regionId ) && otherStatePK.id.equals( id ) );
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
