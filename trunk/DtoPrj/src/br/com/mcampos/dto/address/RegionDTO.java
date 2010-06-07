package br.com.mcampos.dto.address;


import br.com.mcampos.dto.core.SimpleTable;

import java.io.Serializable;

public class RegionDTO implements Comparable<RegionDTO>, SimpleTable, Serializable
{
    private String abbreviation;
    private CountryDTO country;
    private Integer id;
    private String description;

    public RegionDTO()
    {
        super();
    }

    public void setAbbreviation( String abbreviation )
    {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation()
    {
        return abbreviation;
    }

    @Override
    public int compareTo( RegionDTO o )
    {
        int nRet = getCountry().compareTo( o.getCountry() );
        if ( nRet != 0 )
            return nRet;
        return getId().compareTo( o.getId() );
    }

    public void setCountry( CountryDTO country )
    {
        this.country = country;
    }

    public CountryDTO getCountry()
    {
        return country;
    }

    @Override
    public RegionDTO setId( Integer id )
    {
        this.id = id;
        return this;
    }

    @Override
    public Integer getId()
    {
        return id;
    }

    @Override
    public RegionDTO setDescription( String description )
    {
        this.description = description;
        return this;
    }

    @Override
    public String getDescription()
    {
        return description;
    }
}
