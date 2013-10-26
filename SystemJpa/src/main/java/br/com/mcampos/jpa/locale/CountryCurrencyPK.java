package br.com.mcampos.jpa.locale;

import java.io.Serializable;

public class CountryCurrencyPK implements Serializable
{
	private static final long serialVersionUID = 3205908793281885858L;
	private String countryId;
	private Integer id;

	public CountryCurrencyPK( )
	{
	}

	public CountryCurrencyPK( String countryId, Integer id )
	{
		this.countryId = countryId;
		this.id = id;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( other instanceof CountryCurrencyPK ) {
			final CountryCurrencyPK otherCountryCurrencyPK = (CountryCurrencyPK) other;
			return ( otherCountryCurrencyPK.countryId.equals( this.countryId ) && otherCountryCurrencyPK.id.equals( this.id ) );
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

	public void setCountryId( String id )
	{
		this.countryId = id;
	}

	public Integer getId( )
	{
		return this.id;
	}

	public void setId( Integer id )
	{
		this.id = id;
	}
}
