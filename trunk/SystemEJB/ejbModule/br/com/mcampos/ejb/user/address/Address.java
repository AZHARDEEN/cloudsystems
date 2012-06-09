package br.com.mcampos.ejb.user.address;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.mcampos.ejb.locale.city.City;
import br.com.mcampos.ejb.user.Users;

/**
 * The persistent class for the address database table.
 * 
 */
@Entity
@Table( name = "address" )
public class Address implements Serializable, Comparable<Address>
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AddressPK id;

	@Column( name = "adr_adress_ch", nullable = false, length = 256 )
	private String address;

	@Column( name = "adr_district_ch", length = 64 )
	private String district;

	@Column( name = "adr_from_dt", nullable = false )
	@Temporal( value = TemporalType.DATE )
	private Date fromDate;

	@Column( name = "adr_obs_tx", length = 2147483647 )
	private String obs;

	@Column( name = "adr_to_dt" )
	@Temporal( value = TemporalType.DATE )
	private Date toDate;

	@Column( name = "adr_zip_code_ch", nullable = false, length = 24 )
	private String zip;

	@ManyToOne( optional = false )
	@JoinColumn( name = "cit_id_in", nullable = false, insertable = true, updatable = true )
	private City city;

	@ManyToOne( optional = false )
	@JoinColumn( name = "usr_id_in", insertable = false, updatable = false, nullable = false )
	private Users user;

	@ManyToOne( optional = false )
	@JoinColumn( name = "adt_id_in", insertable = false, updatable = false, nullable = false )
	private AddressType type;

	public Address( )
	{
	}

	public AddressPK getId( )
	{
		if ( this.id == null ) {
			this.id = new AddressPK( );
		}
		return this.id;
	}

	public void setId( AddressPK id )
	{
		this.id = id;
	}

	public String getAddress( )
	{
		return this.address;
	}

	public void setAddress( String adrAdressCh )
	{
		this.address = adrAdressCh;
	}

	public String getDistrict( )
	{
		return this.district;
	}

	public void setDistrict( String adrDistrictCh )
	{
		this.district = adrDistrictCh;
	}

	public Date getFromDate( )
	{
		return this.fromDate;
	}

	public void setFromDate( Date adrFromDt )
	{
		this.fromDate = adrFromDt;
	}

	public String getObs( )
	{
		return this.obs;
	}

	public void setObs( String adrObsTx )
	{
		this.obs = adrObsTx;
	}

	public Date getToDate( )
	{
		return this.toDate;
	}

	public void setToDate( Date adrToDt )
	{
		this.toDate = adrToDt;
	}

	public String getZip( )
	{
		return this.zip;
	}

	public void setZip( String adrZipCodeCh )
	{
		this.zip = adrZipCodeCh;
	}

	public City getCity( )
	{
		return this.city;
	}

	public void setCity( City city )
	{
		this.city = city;
	}

	public Users getUser( )
	{
		return this.user;
	}

	public void setUser( Users user )
	{
		this.user = user;
		if ( getUser( ) != null )
		{
			getId( ).setUserId( getUser( ).getId( ) );
			getUser( ).add( this );
		}
	}

	@Override
	public int compareTo( Address o )
	{
		if ( o == null ) {
			return 1;
		}
		int nRet = 0;
		if ( getUser( ) != null ) {
			nRet = getUser( ).compareTo( o.getUser( ) );
		}
		if ( nRet == 0 && getType( ) != null ) {
			nRet = getType( ).compareTo( o.getType( ) );
		}
		return nRet;
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( obj == null ) {
			return false;
		}
		if ( obj instanceof Address ) {
			Address other = (Address) obj;
			if ( getUser( ) != null && getUser( ).equals( other.getUser( ) ) ) {
				return getType( ).equals( other.getType( ) );
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	public AddressType getType( )
	{
		return this.type;
	}

	public void setType( AddressType type )
	{
		this.type = type;
		if ( getType( ) != null ) {
			getId( ).setAddressType( getType( ).getId( ) );
		}
	}
}