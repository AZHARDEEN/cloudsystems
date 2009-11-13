package br.com.mcampos.dto.address;

import br.com.mcampos.dto.core.DisplayNameDTO;

import java.io.Serializable;

public class CityDTO extends DisplayNameDTO implements Comparable< CityDTO >
{
    private Boolean countryCapital;
    private Integer id;
    private String name;
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

    public void setName( String name )
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setStateCapital( Boolean stateCapital )
    {
        this.stateCapital = stateCapital;
    }

    public Boolean getStateCapital()
    {
        return stateCapital;
    }
    
    public String getDisplayName()
    {
        return getName();
    }

    public void setState( StateDTO state )
    {
        this.state = state;
    }

    public StateDTO getState()
    {
        return state;
    }

    public int compareTo( CityDTO target )
    {
        return getId().compareTo(target.getId());
    }
}
