package br.com.mcampos.jpa.locale;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries( { @NamedQuery( name = "CountryCurrency.findAll", query = "select o from CountryCurrency o" ) } )
@Table( name = "country_currency", schema = "public" )
@IdClass( CountryCurrencyPK.class )
public class CountryCurrency implements Serializable
{
	private static final long serialVersionUID = -6950398861210210826L;

	@Id
	@Column( name = "ctr_code_ch", nullable = false, insertable = false, updatable = false )
	private String countryId;

	@Id
	@Column( name = "cur_id_in", nullable = false, insertable = false, updatable = false )
	private Integer id;

	@Column( name = "ccr_from_dt" )
	@Temporal( TemporalType.DATE )
	private Date fromDate;

	@Column( name = "ccr_main_bt" )
	private Boolean isMain;

	@Column( name = "ccr_to_dt" )
	@Temporal( TemporalType.DATE )
	private Date toDate;

	@ManyToOne
	@JoinColumn( name = "ctr_code_ch" )
	private Country country;

	@ManyToOne
	@JoinColumn( name = "cur_id_in" )
	private Currency currency;

	public CountryCurrency( )
	{
	}

	public Date getFromDate( )
	{
		return fromDate;
	}

	public void setFromDate( Date date )
	{
		fromDate = date;
	}

	public Boolean getIsMain( )
	{
		return isMain;
	}

	public void setIsMain( Boolean main )
	{
		isMain = main;
	}

	public Date getToDate( )
	{
		return toDate;
	}

	public void setToDate( Date date )
	{
		toDate = date;
	}

	public String getCountryId( )
	{
		return countryId;
	}

	public void setCountryId( String code )
	{
		countryId = code;
	}

	public Integer getId( )
	{
		return id;
	}

	public void setId( Integer key )
	{
		id = key;
	}

	public Country getCountry( )
	{
		return country;
	}

	public void setCountry( Country country )
	{
		this.country = country;
		if ( country != null ) {
			countryId = country.getId( );
		}
	}

	public Currency getCurrency( )
	{
		return currency;
	}

	public void setCurrency( Currency currency )
	{
		this.currency = currency;
		if ( currency != null ) {
			id = currency.getId( );
		}
	}
}
