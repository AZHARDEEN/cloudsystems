package br.com.mcampos.jpa.locale;

import java.io.Serializable;

public class StatePK implements Serializable
{
	private static final long serialVersionUID = 7824208857913973416L;
	private String countryId;
	private Integer regionId;
	private Integer id;

	public StatePK( )
	{
	}

	public StatePK( String code, Integer region, Integer state )
	{
		this.countryId = code;
		this.regionId = region;
		this.id = state;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( other instanceof StatePK ) {
			final StatePK otherStatePK = (StatePK) other;
			return ( otherStatePK.countryId.equals( this.countryId ) && otherStatePK.regionId.equals( this.regionId ) && otherStatePK.id.equals( this.id ) );
		}
		return false;
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.getCountryId( ).hashCode( );
		hash = hash * prime + this.getRegionId( ).hashCode( );
		hash = hash * prime + this.getId( ).hashCode( );

		return hash;
	}

	public String getCountryId( )
	{
		return this.countryId;
	}

	public void setCountryId( String code )
	{
		this.countryId = code;
	}

	public Integer getRegionId( )
	{
		return this.regionId;
	}

	public void setRegionId( Integer region )
	{
		this.regionId = region;
	}

	public Integer getId( )
	{
		return this.id;
	}

	public void setId( Integer state )
	{
		this.id = state;
	}
}
