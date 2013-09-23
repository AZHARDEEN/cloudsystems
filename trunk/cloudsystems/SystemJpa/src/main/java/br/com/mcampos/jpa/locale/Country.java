package br.com.mcampos.jpa.locale;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.jpa.BasicEntityRenderer;

@Entity
@NamedQueries( { @NamedQuery( name = "Country.findAll", query = "select o from Country o" ) } )
@Table( name = "country", schema = "public" )
public class Country implements BasicEntityRenderer<Country>
{
	private static final long serialVersionUID = 1105238821135821643L;

	@Id
	@Column( name = "ctr_code_ch", nullable = false )
	private String id;

	@Column( name = "ctr_code3_ch" )
	private String code3;

	@Column( name = "ctr_flag_bin" )
	@Lob
	@Basic( fetch = FetchType.LAZY )
	private Byte[ ] flag;

	@Column( name = "ctr_num_code_in" )
	private Integer numericCode;

	public Country( )
	{
	}

	public String getCode3( )
	{
		return code3;
	}

	public void setCode3( String param )
	{
		code3 = param;
	}

	@Override
	public String getId( )
	{
		return id;
	}

	public void setId( String param )
	{
		id = param;
	}

	public Byte[ ] getFlag( )
	{
		return flag;
	}

	public void setFlag( Byte[ ] bin )
	{
		flag = bin.clone( );
	}

	public Integer getNumericCode( )
	{
		return numericCode;
	}

	public void setNumericCode( Integer code )
	{
		numericCode = code;
	}

	@Override
	public String getField( Integer field )
	{
		switch ( field ) {
		case 0:
			return getId( );
		case 1:
			return getCode3( );
		case 2:
			return getNumericCode( ).toString( );
		}
		return "";
	}

	@Override
	public int compareTo( Country object, Integer field )
	{
		switch ( field ) {
		case 0:
			return getId( ).compareTo( object.getId( ) );
		case 1:
			return getCode3( ).compareTo( object.getCode3( ) );
		case 2:
			return getNumericCode( ).compareTo( object.getNumericCode( ) );
		}
		return 0;
	}

	@Override
	public String toString( )
	{
		return getId( ) + "[" + getCode3( ) + "]";
	}

}
