package br.com.mcampos.ejb.locale;

import java.io.Serializable;

public class CountryLanguagePK implements Serializable
{
	private static final long serialVersionUID = -2770972583765504515L;
	private String countryId;
	private Integer id;

	public CountryLanguagePK()
	{
	}

	public CountryLanguagePK( String ctr_code_ch, Integer lng_id_in )
	{
		this.countryId = ctr_code_ch;
		this.id = lng_id_in;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( other instanceof CountryLanguagePK ) {
			final CountryLanguagePK otherCountryLanguagePK = ( CountryLanguagePK ) other;
			final boolean areEqual = ( otherCountryLanguagePK.countryId.equals( this.countryId ) && otherCountryLanguagePK.id.equals( this.id ) );
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

	public Integer getId()
	{
		return this.id;
	}

	public void setId( Integer lng_id_in )
	{
		this.id = lng_id_in;
	}
}
