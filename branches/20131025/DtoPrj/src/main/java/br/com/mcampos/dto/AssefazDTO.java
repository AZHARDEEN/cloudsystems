package br.com.mcampos.dto;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Date;

import br.com.mcampos.sysutils.SysUtils;


public class AssefazDTO implements Serializable
{
	private static final long serialVersionUID = -6310392594072337248L;

	private AssefazID id;
	private Date birthDate;
	private String Name;
	private String cpf;
	private String address;
	private String district;
	private String city;
	private String state;
	private String zipCode;
	private String phone;
	private String email;
	private Double payment;
	private String cause;
	private Integer lineNumber;
	private String record;

	private boolean valid;

	public AssefazDTO( )
	{
		setValid( false );
	}

	public AssefazDTO( String line )
	{
		set( line );
	}

	public AssefazID getId( )
	{
		if ( this.id == null ) {
			this.id = new AssefazID( );
		}
		return this.id;
	}

	public void setId( AssefazID id )
	{
		this.id = id;
	}

	public Date getBirthDate( )
	{
		return this.birthDate;
	}

	public void setBirthDate( Date birthDate )
	{
		this.birthDate = birthDate;
	}

	public String getName( )
	{
		return this.Name;
	}

	public void setName( String name )
	{
		if ( name != null ) {
			this.Name = name.trim( );
		}
		else {
			this.Name = null;
		}

	}

	public String getCpf( )
	{
		return this.cpf;
	}

	public void setCpf( String cpf )
	{
		if ( cpf != null ) {
			this.cpf = cpf.trim( );
		}
		else {
			this.cpf = null;
		}
	}

	public String getAddress( )
	{
		return this.address;
	}

	public void setAddress( String address )
	{
		if ( address != null ) {
			this.address = address.trim( );
		}
		else {
			this.address = null;
		}
	}

	public String getDistrict( )
	{
		return this.district;
	}

	public void setDistrict( String district )
	{
		if ( district != null ) {
			this.district = district.trim( );
		}
		else {
			this.district = null;
		}
	}

	public String getCity( )
	{
		return this.city;
	}

	public void setCity( String city )
	{
		this.city = ( city != null ? city.trim( ) : null );
	}

	public String getState( )
	{
		return this.state;
	}

	public void setState( String state )
	{
		this.state = ( state != null ? state.trim( ) : null );
	}

	public String getZipCode( )
	{
		return this.zipCode;
	}

	public void setZipCode( String zipCode )
	{
		this.zipCode = ( zipCode != null ? zipCode.trim( ) : null );
	}

	public String getPhone( )
	{
		return this.phone;
	}

	public void setPhone( String phone )
	{
		this.phone = ( phone != null ? phone.trim( ) : null );
	}

	public String getEmail( )
	{
		return this.email;
	}

	public void setEmail( String email )
	{
		this.email = ( email != null ? email.trim( ).toLowerCase( ) : null );
	}

	public Double getPayment( )
	{
		return this.payment;
	}

	public void setPayment( Double payment )
	{
		this.payment = payment;
	}

	public void set( String line )
	{
		if ( SysUtils.isEmpty( line ) ) {
			invalidate( "Empty Record" );
			return;
		}
		setRecord( line );
		try {
			String[ ] fields = line.split( ";" );
			if ( fields.length < 12 ) {
				invalidate( "Invalid number of records. Expected 12 found " + fields.length );
				return;
			}
			getId( ).set( fields[ 0 ] );
			setBirthDate( fields[ 1 ] );
			setName( fields[ 2 ] );
			setCpf( fields[ 3 ] );
			setAddress( fields[ 4 ] );
			setDistrict( fields[ 5 ] );
			setCity( fields[ 6 ] );
			setZipCode( fields[ 7 ] );
			setState( fields[ 8 ] );
			setPhone( fields[ 9 ] );
			setEmail( fields[ 10 ] );
			setPayment( fields[ 11 ] );
			setValid( validate( ) );
		}
		catch ( Exception e )
		{
			String message;

			message = e.getMessage( );
			invalidate( message );
		}
	}

	private boolean validate( )
	{
		if ( SysUtils.isEmpty( getName( ) ) ) {
			invalidate( "Name is empty" );
			return false;
		}

		if ( getId( ).getSequence( ).equals( 1 ) ) {
			if ( SysUtils.isEmpty( getCpf( ) ) ) {
				invalidate( "Assefaz Sequence is 1 and CPF is invalid" );
				return false;
			}
		}
		if ( getPayment( ) == null || getPayment( ).equals( 0 ) ) {
			invalidate( "Payment is invalid" );
			return false;
		}
		return true;
	}

	private void setBirthDate( String value )
	{
		setBirthDate( SysUtils.parseDate( value, "dd-MMM-yy" ) );
	}

	private void setPayment( String value ) throws Exception
	{
		try {
			NumberFormat nf_br = NumberFormat.getInstance( SysUtils.getLocalePtBR( ) );

			Number n = nf_br.parse( value );
			setPayment( n.doubleValue( ) );
		}
		catch ( Exception e )
		{
			this.payment = null;
			throw new Exception( "Payment is invalid: " + value );
		}
	}

	public boolean isValid( )
	{
		return this.valid;
	}

	public void setValid( boolean valid )
	{
		this.valid = valid;
	}

	public void invalidate( String cause )
	{
		setValid( false );
		setCause( cause );
	}

	public String getCause( )
	{
		return this.cause;
	}

	public void setCause( String cause )
	{
		this.cause = cause;
	}

	public Integer getLineNumber( )
	{
		return this.lineNumber;
	}

	public void setLineNumber( Integer lineNumber )
	{
		this.lineNumber = lineNumber;
	}

	public String getRecord( )
	{
		return this.record;
	}

	public void setRecord( String record )
	{
		this.record = record;
	}
}
