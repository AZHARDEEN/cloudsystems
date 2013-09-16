package br.com.mcampos.jpa.inep;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.jpa.SimpleTable;

/**
 * The persistent class for the revisor_type database table.
 * 
 */
@Entity
@Table( name = "revisor_type", schema = "inep" )
public class RevisorType extends SimpleTable<RevisorType> implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static final Integer typeWritten = 1;
	public static final Integer typeOral = 2;

	@Id
	@Column( name = "irt_id_in" )
	private Integer id;

	@Column( name = "irt_description_ch" )
	private String description;

	public RevisorType( )
	{
	}

	@Override
	public Integer getId( )
	{
		return id;
	}

	@Override
	public void setId( Integer irtIdIn )
	{
		id = irtIdIn;
	}

	@Override
	public String getDescription( )
	{
		return description;
	}

	@Override
	public void setDescription( String irtDescriptionCh )
	{
		description = irtDescriptionCh;
	}

}