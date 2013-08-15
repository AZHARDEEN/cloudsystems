package br.com.mcampos.ejb.locale.country;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.BasicEntityRenderer;

@Entity
@NamedQueries( { @NamedQuery( name = "Country.findAll", query = "select o from Country o" ) } )
@Table( name = "\"country\"" )
public class Country implements BasicEntityRenderer<Country>
{
	private static final long serialVersionUID = 1105238821135821643L;

	@Id
	@Column( name = "ctr_code_ch", nullable = false )
	private String code;

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
		return this.code3;
	}

	public void setCode3( String ctr_code3_ch )
	{
		this.code3 = ctr_code3_ch;
	}

	public String getCode( )
	{
		return this.code;
	}

	public void setCode( String ctr_code_ch )
	{
		this.code = ctr_code_ch;
	}

	public Byte[ ] getFlag( )
	{
		return this.flag;
	}

	public void setFlag( Byte[ ] ctr_flag_bin )
	{
		this.flag = ctr_flag_bin;
	}

	public Integer getNumericCode( )
	{
		return this.numericCode;
	}

	public void setNumericCode( Integer ctr_num_code_in )
	{
		this.numericCode = ctr_num_code_in;
	}

	@Override
	public String getField( Integer field )
	{
		switch ( field ) {
		case 0:
			return getCode( );
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
			return getCode( ).compareTo( object.getCode( ) );
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
		return getCode( ) + "[" + getCode3( ) + "]";
	}

}
