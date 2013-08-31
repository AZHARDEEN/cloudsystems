package br.com.mcampos.entity.system;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import br.com.mcampos.ejb.core.SimpleTable;

/**
 * The persistent class for the operator database table.
 * 
 */
@Entity
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
		return this.id;
	}

	@Override
	public void setId( Integer oprIdIn )
	{
		this.id = oprIdIn;
	}

	@Override
	public String getDescription( )
	{
		return this.description;
	}

	@Override
	public void setDescription( String oprDescriptionCh )
	{
		this.description = oprDescriptionCh;
	}

	public String getSymbol( )
	{
		return this.symbol;
	}

	public void setSymbol( String oprSimbolCh )
	{
		this.symbol = oprSimbolCh;
	}

}