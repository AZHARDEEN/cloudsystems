package br.com.mcampos.dto.inep;

import java.io.Serializable;

import br.com.mcampos.sysutils.SysUtils;

public class InepStationSubscriptionResponsableImportDTO implements Serializable
{
	private static final long serialVersionUID = 9018693633263790170L;

	private String stationId;
	private String name;
	private String address;
	private String district;
	private String zipCode;
	private String City;
	private String State;
	private String Country;
	private String email;
	private String fone;
	private String mobile;

	public String getStationId( )
	{
		return this.stationId;
	}

	public void setStationId( String stationId )
	{
		this.stationId = stationId;
	}

	public String getName( )
	{
		return this.name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getAddress( )
	{
		return this.address;
	}

	public void setAddress( String address )
	{
		this.address = address;
	}

	public String getDistrict( )
	{
		return this.district;
	}

	public void setDistrict( String district )
	{
		this.district = district;
	}

	public String getZipCode( )
	{
		return this.zipCode;
	}

	public void setZipCode( String value )
	{
		if ( !SysUtils.isEmpty( value ) ) {
			value = value.replaceAll( "[\\.-]", "" );
		}
		this.zipCode = value;
	}

	public String getCity( )
	{
		return this.City;
	}

	public void setCity( String city )
	{
		this.City = city;
	}

	public String getState( )
	{
		return this.State;
	}

	public void setState( String state )
	{
		this.State = state;
	}

	public String getCountry( )
	{
		return this.Country;
	}

	public void setCountry( String country )
	{
		this.Country = country;
	}

	public String getEmail( )
	{
		return this.email;
	}

	public void setEmail( String email )
	{
		this.email = email;
	}

	public String getFone( )
	{
		return this.fone;
	}

	public void setFone( String value )
	{
		if ( !SysUtils.isEmpty( value ) ) {
			value = value.replaceAll( "[\\.-]", "" );
		}
		this.fone = value;
	}

	public String getMobile( )
	{
		return this.mobile;
	}

	public void setMobile( String value )
	{
		if ( !SysUtils.isEmpty( value ) ) {
			value = value.replaceAll( "[\\.-]", "" );
		}
		this.mobile = value;
	}

	public void set( int columnIndex, String value )
	{
		if ( !SysUtils.isEmpty( value ) ) {
			if ( value.equalsIgnoreCase( "N/A" ) ) {
				value = "";
			}
		}
		value = SysUtils.trim( value );
		switch ( columnIndex ) {
		case 0:
			this.setStationId( value );
			break;
		case 1:
			this.setName( value );
			break;
		case 2:
			this.setAddress( value );
			break;
		case 3:
			this.setDistrict( value );
			break;
		case 4:
			this.setZipCode( value );
			break;
		case 5:
			this.setCity( value );
			break;
		case 6:
			this.setState( value );
			break;
		case 7:
			this.setCountry( value );
			break;
		case 8:
			this.setEmail( value );
			break;
		case 9:
			this.setFone( value );
			break;
		case 10:
			this.setMobile( value );
			break;
		}
	}
}
