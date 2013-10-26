package br.com.mcampos.jpa.system;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.jpa.SimpleTable;

/**
 * The persistent class for the operator database table.
 * 
 */
@Entity
@Table( name = "operator", schema = "public" )
public class Operator extends SimpleTable<Operator> implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column( name = "opr_id_in" )
	private Integer id;

	@Column( name = "opr_description_ch" )
	private String description;

	@Column( name = "opr_simbol_ch" )
	private String symbol;

	public Operator( )
	{
	}

	@Override
	public Integer getId( )
	{
		return id;
	}

	@Override
	public void setId( Integer oprIdIn )
	{
		id = oprIdIn;
	}

	@Override
	public String getDescription( )
	{
		return description;
	}

	@Override
	public void setDescription( String oprDescriptionCh )
	{
		description = oprDescriptionCh;
	}

	public String getSymbol( )
	{
		return symbol;
	}

	public void setSymbol( String oprSimbolCh )
	{
		symbol = oprSimbolCh;
	}

}