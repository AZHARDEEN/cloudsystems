package br.com.mcampos.jpa.gdf;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.jpa.SimpleCodedTable;

/**
 * The persistent class for the project_type database table.
 * 
 */
@Entity
@Table( name = "project_type", schema = "gdf" )
public class ProjectType extends SimpleCodedTable<ProjectType>
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column( name = "pjt_id_in", nullable = false, unique = true )
	private Integer id;

	@Column( name = "pjt_code_ch", nullable = false, unique = true )
	private String code;

	@Column( name = "pjt_description_ch", nullable = false )
	private String description;

	public ProjectType( )
	{
	}

	@Override
	public Integer getId( )
	{
		return this.id;
	}

	@Override
	public void setId( Integer pjtIdIn )
	{
		this.id = pjtIdIn;
	}

	@Override
	public String getCode( )
	{
		return this.code;
	}

	@Override
	public void setCode( String pjtCodeCh )
	{
		this.code = pjtCodeCh;
	}

	@Override
	public String getDescription( )
	{
		return this.description;
	}

	@Override
	public void setDescription( String pjtDescriptionCh )
	{
		this.description = pjtDescriptionCh;
	}

}