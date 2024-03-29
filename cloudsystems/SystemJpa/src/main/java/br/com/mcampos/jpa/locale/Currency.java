package br.com.mcampos.jpa.locale;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery( name = "Currency.findAll", query = "select o from Currency o" ) } )
@Table( name = "currency", schema = "public" )
public class Currency implements Serializable
{
	private static final long serialVersionUID = -3375725451724480604L;

	@Id
	@Column( name = "cur_id_in", nullable = false )
	private Integer id;

	@Column( name = "cur_code_ch", nullable = false )
	private String code;

	@Column( name = "cur_name_ch", nullable = false )
	private String name;

	@Column( name = "cur_symbol_ch" )
	private String symbol;

	public Currency( )
	{
	}

	public String getCode( )
	{
		return code;
	}

	public void setCode( String value )
	{
		code = value;
	}

	public Integer getId( )
	{
		return id;
	}

	public void setId( Integer value )
	{
		id = value;
	}

	public String getName( )
	{
		return name;
	}

	public void setName( String value )
	{
		name = value;
	}

	public String getSymbol( )
	{
		return symbol;
	}

	public void setSymbol( String value )
	{
		symbol = value;
	}

}
