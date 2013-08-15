package br.com.mcampos.ejb.system.fieldtype;

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
@Table( name = "field_type" )
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

	public Integer getId( )
	{
		return this.id;
	}

	public void setId( Integer fltIdIn )
	{
		this.id = fltIdIn;
	}

	public String getDescription( )
	{
		return this.description;
	}

	public void setDescription( String fltDescriptionCh )
	{
		this.description = fltDescriptionCh;
	}

}