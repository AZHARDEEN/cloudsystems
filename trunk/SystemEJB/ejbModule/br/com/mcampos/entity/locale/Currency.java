package br.com.mcampos.entity.locale;

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

	public void setCode( String cur_code_ch )
	{
		code = cur_code_ch;
	}

	public Integer getId( )
	{
		return id;
	}

	public void setId( Integer cur_id_in )
	{
		id = cur_id_in;
	}

	public String getName( )
	{
		return name;
	}

	public void setName( String cur_name_ch )
	{
		name = cur_name_ch;
	}

	public String getSymbol( )
	{
		return symbol;
	}

	public void setSymbol( String cur_symbol_ch )
	{
		symbol = cur_symbol_ch;
	}

}
