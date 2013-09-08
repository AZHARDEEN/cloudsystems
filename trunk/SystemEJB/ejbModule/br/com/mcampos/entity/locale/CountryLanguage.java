package br.com.mcampos.entity.locale;

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
@NamedQueries( { @NamedQuery( name = "CountryLanguage.findAll", query = "select o from CountryLanguage o" ) } )
@Table( name = "country_language", schema = "public" )
@IdClass( CountryLanguagePK.class )
public class CountryLanguage implements Serializable
{

	private static final long serialVersionUID = 4083672679704453040L;

	@Id
	@Column( name = "ctr_code_ch", nullable = false, insertable = false, updatable = false )
	private String countryId;

	@Id
	@Column( name = "lng_id_in", nullable = false )
	private Integer id;

	@Column( name = "clg_main_bt" )
	private Boolean isMain;

	@ManyToOne
	@JoinColumn( name = "ctr_code_ch" )
	private Country country;

	public CountryLanguage( )
	{
	}

	public Boolean getIsMain( )
	{
		return isMain;
	}

	public void setIsMain( Boolean clg_main_bt )
	{
		isMain = clg_main_bt;
	}

	public String getCountryId( )
	{
		return countryId;
	}

	public void setCountryId( String ctr_code_ch )
	{
		countryId = ctr_code_ch;
	}

	public Integer getId( )
	{
		return id;
	}

	public void setId( Integer lng_id_in )
	{
		id = lng_id_in;
	}

	public Country getCountry( )
	{
		return country;
	}

	public void setCountry( Country country2 )
	{
		country = country2;
		if ( country2 != null ) {
			countryId = country2.getId( );
		}
	}
}
