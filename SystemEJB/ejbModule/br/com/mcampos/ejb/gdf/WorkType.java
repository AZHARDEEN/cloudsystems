package br.com.mcampos.ejb.gdf;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.SimpleCodedTable;

/**
 * The persistent class for the work_type database table.
 * 
 */
@Entity
@Table( name = "work_type", schema = "gdf" )
public class WorkType extends SimpleCodedTable<WorkType>
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column( name = "wkt_id_in", nullable = false, unique = true )
	private Integer id;

	@Column( name = "wkt_code_ch", nullable = false, unique = true )
	private String code;

	@Column( name = "wkt_description_ch", nullable = false )
	private String description;

	public WorkType( )
	{
	}

	@Override
	public Integer getId( )
	{
		return this.id;
	}

	@Override
	public void setId( Integer wktIdIn )
	{
		this.id = wktIdIn;
	}

	@Override
	public String getCode( )
	{
		return this.code;
	}

	@Override
	public void setCode( String wktCodeCh )
	{
		this.code = wktCodeCh;
	}

	@Override
	public String getDescription( )
	{
		return this.description;
	}

	@Override
	public void setDescription( String wktDescriptionCh )
	{
		this.description = wktDescriptionCh;
	}

}