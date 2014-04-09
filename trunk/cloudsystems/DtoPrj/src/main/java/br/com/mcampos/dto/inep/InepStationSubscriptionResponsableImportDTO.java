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
		return stationId;
	}

	public void setStationId( String stationId )
	{
		this.stationId = stationId;
	}

	public String getName( )
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getAddress( )
	{
		return address;
	}

	public void setAddress( String address )
	{
		this.address = address;
	}

	public String getDistrict( )
	{
		return district;
	}

	public void setDistrict( String district )
	{
		this.district = district;
	}

	public String getZipCode( )
	{
		return zipCode;
	}

	public void setZipCode( String value )
	{
		if ( !SysUtils.isEmpty( value ) ) {
			value = value.replaceAll( "[\\.-]", "" );
		}
		zipCode = value;
	}

	public String getCity( )
	{
		return City;
	}

	public void setCity( String city )
	{
		City = city;
	}

	public String getState( )
	{
		return State;
	}

	public void setState( String state )
	{
		State = state;
	}

	public String getCountry( )
	{
		return Country;
	}

	public void setCountry( String country )
	{
		Country = country;
	}

	public String getEmail( )
	{
		return email;
	}

	public void setEmail( String email )
	{
		this.email = email;
		if( SysUtils.isEmpty( this.email ) ) {
			this.email = this.email.trim( ).toLowerCase( );
		}
	}

	public String getFone( )
	{
		return fone;
	}

	public void setFone( String value )
	{
		if ( !SysUtils.isEmpty( value ) ) {
			value = value.replaceAll( "[\\.-]", "" );
		}
		fone = value;
	}

	public String getMobile( )
	{
		return mobile;
	}

	public void setMobile( String value )
	{
		if ( !SysUtils.isEmpty( value ) ) {
			value = value.replaceAll( "[\\.-]", "" );
		}
		mobile = value;
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
			setStationId( value );
			break;
		case 1:
			setName( value );
			break;
		case 2:
			setAddress( value );
			break;
		case 3:
			setDistrict( value );
			break;
		case 4:
			setZipCode( value );
			break;
		case 5:
			setCity( value );
			break;
		case 6:
			setState( value );
			break;
		case 7:
			setCountry( value );
			break;
		case 8:
			setEmail( value );
			break;
		case 9:
			setFone( value );
			break;
		case 10:
			setMobile( value );
			break;
		}
	}
}
