package br.com.mcampos.ejb.locale;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.ejb.locale.country.Country;


@Entity
@NamedQueries( { @NamedQuery( name = "CountryName.findAll", query = "select o from CountryName o" ) } )
@Table( name = "\"country_name\"" )
@IdClass( CountryNamePK.class )
public class CountryName implements Serializable
{
	private static final long serialVersionUID = 3181601040895024491L;

	@Id
	@Column( name = "ctr_code_ch", nullable = false, insertable = false, updatable = false )
	private String countryId;

	@Id
	@Column( name = "ctr_locale_ch", nullable = false, insertable = false, updatable = false )
	private String localeCountryId;

	@Column( name = "ctr_alternate_name_ch" )
	private String ctr_alternate_name_ch;

	@Column( name = "ctr_name_ch", nullable = false )
	private String ctr_name_ch;

	@ManyToOne
	@JoinColumn( name = "ctr_locale_ch" )
	private Country localeCountry;

	@ManyToOne
	@JoinColumn( name = "ctr_code_ch" )
	private Country country;

	public CountryName()
	{
	}

	public String getCtr_alternate_name_ch()
	{
		return this.ctr_alternate_name_ch;
	}

	public void setCtr_alternate_name_ch( String ctr_alternate_name_ch )
	{
		this.ctr_alternate_name_ch = ctr_alternate_name_ch;
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

	public String getCtr_name_ch()
	{
		return this.ctr_name_ch;
	}

	public void setCtr_name_ch( String ctr_name_ch )
	{
		this.ctr_name_ch = ctr_name_ch;
	}

	public Country getLocaleCountry()
	{
		return this.localeCountry;
	}

	public void setLocaleCountry( Country country1 )
	{
		this.localeCountry = country1;
		if ( country1 != null ) {
			this.localeCountryId = country1.getCode();
		}
	}

	public Country getCountry()
	{
		return this.country;
	}

	public void setCountry( Country country4 )
	{
		this.country = country4;
		if ( country4 != null ) {
			this.countryId = country4.getCode();
		}
	}
}
