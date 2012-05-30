package br.com.mcampos.ejb.user.address;


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

import br.com.mcampos.ejb.locale.City;
import br.com.mcampos.ejb.user.Users;


@Entity
@NamedQueries( { @NamedQuery( name = "Address.findAll", query = "select o from Address o" ) } )
@Table( name = "\"address\"" )
@IdClass( AddressPK.class )
public class Address implements Serializable
{
	private static final long serialVersionUID = 346524316312636700L;

	@Id
	@Column( name = "adt_id_in", nullable = false, insertable = false, updatable = false )
	private Integer addressTypeId;

	@Id
	@Column( name = "usr_id_in", nullable = false, insertable = false, updatable = false )
	private Integer userId;

	@Id
	@Column( name = "adr_to_dt", nullable = true, insertable = true, updatable = true )
	@Temporal( value = TemporalType.DATE )
	private Date toDate;


	@Column( name = "adr_adress_ch", nullable = false )
	private String address;

	@Column( name = "adr_district_ch" )
	private String district;

	@Column( name = "adr_from_dt", nullable = false )
	@Temporal( value = TemporalType.DATE )
	private Date fromDate;


	@Column( name = "adr_obs_tx" )
	private String obs;


	@Column( name = "adr_zip_code_ch", nullable = false )
	private String zip;

	@ManyToOne
	@JoinColumn( name = "cit_id_in", nullable = false, insertable = true, updatable = true )
	private City city;

	@ManyToOne
	@JoinColumn( name = "usr_id_in" )
	private Users user;

	@ManyToOne
	@JoinColumn( name = "adt_id_in", insertable = true, updatable = true, nullable = false )
	private AddressType addressType;

	public Address()
	{
	}

	public String getAddress()
	{
		return this.address;
	}

	public void setAddress( String adr_adress_ch )
	{
		this.address = adr_adress_ch;
	}

	public String getDistrict()
	{
		return this.district;
	}

	public void setDistrict( String adr_district_ch )
	{
		this.district = adr_district_ch;
	}

	public Date getFromDate()
	{
		return this.fromDate;
	}

	public void setFromDate( Date adr_from_dt )
	{
		this.fromDate = adr_from_dt;
	}

	public String getObs()
	{
		return this.obs;
	}

	public void setObs( String adr_obs_tx )
	{
		this.obs = adr_obs_tx;
	}

	public Date getToDate()
	{
		return this.toDate;
	}

	public void setToDate( Date adr_to_dt )
	{
		this.toDate = adr_to_dt;
	}

	public String getZip()
	{
		return this.zip;
	}

	public void setZip( String adr_zip_code_ch )
	{
		this.zip = adr_zip_code_ch;
	}

	public Integer getAddressTypeId()
	{
		return this.addressTypeId;
	}

	public void setAddressTypeId( Integer adt_id_in )
	{
		this.addressTypeId = adt_id_in;
	}

	public City getCity()
	{
		return this.city;
	}

	public void setCity( City city )
	{
		this.city = city;
	}

	public Integer getUserId()
	{
		return this.userId;
	}

	public void setUserId( Integer usr_id_in )
	{
		this.userId = usr_id_in;
	}

	public Users getUser()
	{
		return this.user;
	}

	public void setUser( Users users2 )
	{
		this.user = users2;
		if ( users2 != null ) {
			this.userId = users2.getId();
		}
	}

	public AddressType getAddressType()
	{
		return this.addressType;
	}

	public void setAddressType( AddressType addressType )
	{
		this.addressType = addressType;
		if ( addressType != null ) {
			this.addressTypeId = addressType.getId();
		}
	}
}
