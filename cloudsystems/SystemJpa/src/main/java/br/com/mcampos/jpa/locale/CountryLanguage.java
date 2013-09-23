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
		return this.isMain;
	}

	public void setIsMain( Boolean main )
	{
		this.isMain = main;
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

	public Country getCountry( )
	{
		return this.country;
	}

	public void setCountry( Country country2 )
	{
		this.country = country2;
		if ( country2 != null ) {
			this.countryId = country2.getId( );
		}
	}
}
