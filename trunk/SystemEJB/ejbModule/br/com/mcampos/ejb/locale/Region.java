package br.com.mcampos.ejb.locale;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.ejb.locale.country.Country;


@Entity
@NamedQueries( { @NamedQuery( name = "Region.findAll", query = "select o from Region o" ) } )
@Table( name = "\"region\"" )
@IdClass( RegionPK.class )
public class Region implements Serializable
{
	private static final long serialVersionUID = 3054324860244010262L;

	@Id
	@Column( name = "ctr_code_ch", nullable = false, insertable = false, updatable = false )
	private String countryId;

	@Id
	@Column( name = "reg_id_in", nullable = false )
	private Integer id;

	@Column( name = "reg_abbreviation_ch", nullable = false )
	private String abbreviation;

	@Column( name = "reg_flag_bin" )
	@Lob
	@Basic( fetch = FetchType.LAZY )
	private Byte[] flag;


	@Column( name = "reg_name_ch", nullable = false )
	private String name;

	@ManyToOne
	@JoinColumn( name = "ctr_code_ch", nullable = false, updatable = true, insertable = true )
	private Country country;

	public Region()
	{
	}

	public String getCountryId()
	{
		return this.countryId;
	}

	public void setCountryId( String ctr_code_ch )
	{
		this.countryId = ctr_code_ch;
	}

	public String getAbbreviation()
	{
		return this.abbreviation;
	}

	public void setAbbreviation( String reg_abbreviation_ch )
	{
		this.abbreviation = reg_abbreviation_ch;
	}

	public Byte[] getFlag()
	{
		return this.flag;
	}

	public void setFlag( Byte[] reg_flag_bin )
	{
		this.flag = reg_flag_bin;
	}

	public Integer getId()
	{
		return this.id;
	}

	public void setId( Integer reg_id_in )
	{
		this.id = reg_id_in;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName( String reg_name_ch )
	{
		this.name = reg_name_ch;
	}

	public Country getCountry()
	{
		return this.country;
	}

	public void setCountry( Country country3 )
	{
		this.country = country3;
		if ( country3 != null ) {
			this.countryId = country3.getCode();
		}
	}
}
