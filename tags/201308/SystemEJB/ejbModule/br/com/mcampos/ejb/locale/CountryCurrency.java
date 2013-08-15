package br.com.mcampos.ejb.locale;

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

import br.com.mcampos.ejb.locale.country.Country;


@Entity
@NamedQueries( { @NamedQuery( name = "CountryCurrency.findAll", query = "select o from CountryCurrency o" ) } )
@Table( name = "\"country_currency\"" )
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

	public CountryCurrency()
	{
	}

	public Date getFromDate()
	{
		return this.fromDate;
	}

	public void setFromDate( Date ccr_from_dt )
	{
		this.fromDate = ccr_from_dt;
	}

	public Boolean getIsMain()
	{
		return this.isMain;
	}

	public void setIsMain( Boolean ccr_main_bt )
	{
		this.isMain = ccr_main_bt;
	}

	public Date getToDate()
	{
		return this.toDate;
	}

	public void setToDate( Date ccr_to_dt )
	{
		this.toDate = ccr_to_dt;
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

	public Country getCountry()
	{
		return this.country;
	}

	public void setCountry( Country country )
	{
		this.country = country;
		if ( country != null ) {
			this.countryId = country.getCode();
		}
	}

	public Currency getCurrency()
	{
		return this.currency;
	}

	public void setCurrency( Currency currency )
	{
		this.currency = currency;
		if ( currency != null ) {
			this.id = currency.getId();
		}
	}
}
