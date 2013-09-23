package br.com.mcampos.jpa.locale;

import java.io.Serializable;

public class RegionPK implements Serializable
{
	private static final long serialVersionUID = -5173394130371907731L;
	private String countryId;
	private Integer id;

	public RegionPK( )
	{
	}

	public RegionPK( String country, Integer region )
	{
		this.countryId = country;
		this.id = region;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( other instanceof RegionPK ) {
			final RegionPK otherRegionPK = (RegionPK) other;
			return ( otherRegionPK.countryId.equals( this.countryId ) && otherRegionPK.id.equals( this.id ) );
		}
		return false;
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.getCountryId( ).hashCode( );
		hash = hash * prime + this.getId( ).hashCode( );

		return hash;
	}

	public String getCountryId( )
	{
		return this.countryId;
	}

	public void setCountryId( String country )
	{
		this.countryId = country;
	}

	public Integer getId( )
	{
		return this.id;
	}

	public void setId( Integer region )
	{
		this.id = region;
	}
}
