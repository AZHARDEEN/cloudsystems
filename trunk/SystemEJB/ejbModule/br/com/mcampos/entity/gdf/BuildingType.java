package br.com.mcampos.entity.gdf;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.SimpleCodedTable;

/**
 * The persistent class for the building_type database table.
 * 
 */
@Entity
@Table( name = "building_type", schema = "gdf" )
public class BuildingType extends SimpleCodedTable<BuildingType>
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column( name = "bdt_id_in", nullable = false, unique = true )
	private Integer id;

	@Column( name = "bdt_code_ch", nullable = false, unique = true )
	private String code;

	@Column( name = "bdt_description_ch", nullable = false )
	private String description;

	public BuildingType( )
	{
	}

	@Override
	public Integer getId( )
	{
		return this.id;
	}

	@Override
	public void setId( Integer buildingTypeId )
	{
		this.id = buildingTypeId;
	}

	@Override
	public String getCode( )
	{
		return this.code;
	}

	@Override
	public void setCode( String code )
	{
		this.code = code;
	}

	@Override
	public String getDescription( )
	{
		return this.description;
	}

	@Override
	public void setDescription( String description )
	{
		this.description = description;
	}

}