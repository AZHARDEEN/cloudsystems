package br.com.mcampos.jpa.locale;

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

@Entity
@NamedQueries( { @NamedQuery( name = "CountryName.findAll", query = "select o from CountryName o" ) } )
@Table( name = "country_name", schema = "public" )
@IdClass( CountryNamePK.class )
public class CountryName implements Serializable
{
	private static final long serialVersionUID = 3181601040895024491L;

	@Id
	@Column( name = "ctr_code_ch", nullable = false, insertable = false, updatable = false )
	private String id;

	@Id
	@Column( name = "ctr_locale_ch", nullable = false, insertable = false, updatable = false )
	private String localeId;

	@Column( name = "ctr_alternate_name_ch" )
	private String alternateName;

	@Column( name = "ctr_name_ch", nullable = false )
	private String name;

	@ManyToOne
	@JoinColumn( name = "ctr_locale_ch" )
	private Country localeCountry;

	@ManyToOne
	@JoinColumn( name = "ctr_code_ch" )
	private Country country;

	public CountryName( )
	{
	}

	public String getAlternateName( )
	{
		return alternateName;
	}

	public void setAlternateName( String name )
	{
		alternateName = name;
	}

	public String getCountryId( )
	{
		return id;
	}

	public void setCountryId( String ctr_code_ch )
	{
		id = ctr_code_ch;
	}

	public String getLocaleCountryId( )
	{
		return localeId;
	}

	public void setLocaleCountryId( String ctr_locale_ch )
	{
		localeId = ctr_locale_ch;
	}

	public String getCtr_name_ch( )
	{
		return name;
	}

	public void setName( String ctr_name_ch )
	{
		name = ctr_name_ch;
	}

	public Country getLocaleCountry( )
	{
		return localeCountry;
	}

	public void setLocaleCountry( Country country1 )
	{
		localeCountry = country1;
		if ( country1 != null ) {
			localeId = country1.getId( );
		}
	}

	public Country getCountry( )
	{
		return country;
	}

	public void setCountry( Country country4 )
	{
		country = country4;
		if ( country4 != null ) {
			id = country4.getId( );
		}
	}
}
