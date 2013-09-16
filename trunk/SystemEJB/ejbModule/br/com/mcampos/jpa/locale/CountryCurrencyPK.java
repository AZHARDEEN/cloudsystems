package br.com.mcampos.jpa.locale;

import java.io.Serializable;

public class CountryCurrencyPK implements Serializable
{
	private static final long serialVersionUID = 3205908793281885858L;
	private String countryId;
	private Integer id;

	public CountryCurrencyPK()
	{
	}

	public CountryCurrencyPK( String ctr_code_ch, Integer cur_id_in )
	{
		this.countryId = ctr_code_ch;
		this.id = cur_id_in;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( other instanceof CountryCurrencyPK ) {
			final CountryCurrencyPK otherCountryCurrencyPK = ( CountryCurrencyPK ) other;
			final boolean areEqual = ( otherCountryCurrencyPK.countryId.equals( this.countryId ) && otherCountryCurrencyPK.id.equals( this.id ) );
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

	public void setId( Integer cur_id_in )
	{
		this.id = cur_id_in;
	}
}
