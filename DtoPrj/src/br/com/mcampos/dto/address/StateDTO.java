package br.com.mcampos.dto.address;

import br.com.mcampos.dto.core.DisplayNameDTO;

public class StateDTO extends DisplayNameDTO implements Comparable< StateDTO >
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1046569607923921204L;
	private Integer countryId;
    private Integer regionId;
    private String abbreviation;
    private Integer id;
    private String name;

    public StateDTO()
    {
        super();
    }
    
    public void setCountryId( Integer countryId )
    {
        this.countryId = countryId;
    }

    public Integer getCountryId()
    {
        return countryId;
    }

    public void setRegionId( Integer regionId )
    {
        this.regionId = regionId;
    }

    public Integer getRegionId()
    {
        return regionId;
    }

    public void setAbbreviation( String abbreviation )
    {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation()
    {
        return abbreviation;
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


    public String getDisplayName()
    {
        return getAbbreviation() + " - " + getName();
    }

    public int compareTo( StateDTO target )
    {
        int nRet;
        //this optimization is usually worthwhile, and can
        //always be added
        if ( this == target )
            return 0;
        nRet = getCountryId().compareTo( target.getCountryId() ); 
        if ( nRet != 0 )
            return nRet;
        nRet = getRegionId().compareTo( target.getRegionId() );
        if ( nRet != 0 )
            return nRet;
        return getId().compareTo( target.getId() );
    }
}
