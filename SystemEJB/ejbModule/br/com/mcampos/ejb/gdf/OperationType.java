package br.com.mcampos.ejb.gdf;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.SimpleCodedTable;

/**
 * The persistent class for the operation_type database table.
 * 
 */
@Entity
@Table( name = "operation_type", schema = "gdf" )
public class OperationType extends SimpleCodedTable<OperationType>
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column( name = "opt_id_in", nullable = false, unique = true )
	private Integer id;

	@Column( name = "opt_code_ch", nullable = false, unique = true )
	private String code;

	@Column( name = "opt_description_ch", nullable = false )
	private String description;

	public OperationType( )
	{
	}

	@Override
	public Integer getId( )
	{
		return this.id;
	}

	@Override
	public void setId( Integer optIdIn )
	{
		this.id = optIdIn;
	}

	@Override
	public String getCode( )
	{
		return this.code;
	}

	@Override
	public void setCode( String optCodeCh )
	{
		this.code = optCodeCh;
	}

	@Override
	public String getDescription( )
	{
		return this.description;
	}

	@Override
	public void setDescription( String optDescriptionCh )
	{
		this.description = optDescriptionCh;
	}

}