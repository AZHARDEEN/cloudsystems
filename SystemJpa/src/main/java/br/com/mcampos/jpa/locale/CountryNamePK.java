package br.com.mcampos.jpa.locale;

import java.io.Serializable;

public class CountryNamePK implements Serializable
{
	private static final long serialVersionUID = 5708805760362180632L;
	private String id;
	private String localeId;

	public CountryNamePK( )
	{
	}

	public CountryNamePK( String id, String locale )
	{
		this.id = id;
		this.localeId = locale;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( other instanceof CountryNamePK ) {
			final CountryNamePK otherCountryNamePK = (CountryNamePK) other;
			final boolean areEqual = ( otherCountryNamePK.id.equals( this.id ) && otherCountryNamePK.localeId.equals( this.localeId ) );
			return areEqual;
		}
		return false;
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.getCountryId( ).hashCode( );
		hash = hash * prime + this.getLocaleCountryId( ).hashCode( );

		return hash;
	}

	public String getCountryId( )
	{
		return this.id;
	}

	public void setCountryId( String id )
	{
		this.id = id;
	}

	public String getLocaleCountryId( )
	{
		return this.localeId;
	}

	public void setLocaleCountryId( String locale )
	{
		this.localeId = locale;
	}
}
