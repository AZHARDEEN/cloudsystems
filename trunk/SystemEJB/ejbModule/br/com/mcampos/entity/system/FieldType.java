package br.com.mcampos.entity.system;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.SimpleTable;

/**
 * The persistent class for the field_type database table.
 * 
 */
@Entity
@Table( name = "field_type", schema = "public" )
public class FieldType extends SimpleTable<FieldType> implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column( name = "flt_id_in", unique = true, nullable = false )
	private Integer id;

	@Column( name = "flt_description_ch", nullable = false, length = 64 )
	private String description;

	public FieldType( )
	{
	}

	@Override
	public Integer getId( )
	{
		return id;
	}

	@Override
	public void setId( Integer fltIdIn )
	{
		id = fltIdIn;
	}

	@Override
	public String getDescription( )
	{
		return description;
	}

	@Override
	public void setDescription( String fltDescriptionCh )
	{
		description = fltDescriptionCh;
	}

}