package br.com.mcampos.dto.address;


import br.com.mcampos.dto.core.DisplayNameDTO;

import java.io.Serializable;

public class CityDTO extends DisplayNameDTO implements Comparable<CityDTO>, Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = 6761503201293735988L;
    private Boolean countryCapital;
    private Integer id;
    private String description;
    private Boolean stateCapital;

    private StateDTO state;

    public CityDTO()
    {
        super();
    }

    public void setCountryCapital( Boolean countryCapital )
    {
        this.countryCapital = countryCapital;
    }

    public Boolean getCountryCapital()
    {
        return countryCapital;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public void setDescription( String name )
    {
        this.description = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setStateCapital( Boolean stateCapital )
    {
        this.stateCapital = stateCapital;
    }

    public Boolean getStateCapital()
    {
        return stateCapital;
    }

    @Override
    public String getDisplayName()
    {
        return getDescription();
    }

    @Override
    public int compareTo( CityDTO target )
    {
        return getId().compareTo( target.getId() );
    }

    public void setState( StateDTO state )
    {
        this.state = state;
    }

    public StateDTO getState()
    {
        return state;
    }

    @Override
    public String toString()
    {
        return getDescription();
    }
}
