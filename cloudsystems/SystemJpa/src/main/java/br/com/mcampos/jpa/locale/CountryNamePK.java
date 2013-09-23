package br.com.mcampos.jpa.locale;

import java.io.Serializable;

public class CountryNamePK implements Serializable
{
	private static final long serialVersionUID = 5708805760362180632L;
	private String countryId;
	private String localeCountryId;

	public CountryNamePK()
	{
	}

	public CountryNamePK( String ctr_code_ch, String ctr_locale_ch )
	{
		this.countryId = ctr_code_ch;
		this.localeCountryId = ctr_locale_ch;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( other instanceof CountryNamePK ) {
			final CountryNamePK otherCountryNamePK = ( CountryNamePK ) other;
			final boolean areEqual = ( otherCountryNamePK.countryId.equals( this.countryId ) && otherCountryNamePK.localeCountryId.equals( this.localeCountryId ) );
			return areEqual;
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		return super.hashCode();
	}

	public String getCountryId()
	{
		return this.countryId;
	}

	public void setCountryId( String ctr_code_ch )
	{
		this.countryId = ctr_code_ch;
	}

	public String getLocaleCountryId()
	{
		return this.localeCountryId;
	}

	public void setLocaleCountryId( String ctr_locale_ch )
	{
		this.localeCountryId = ctr_locale_ch;
	}
}
