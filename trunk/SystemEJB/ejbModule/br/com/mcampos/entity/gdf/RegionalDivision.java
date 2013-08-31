package br.com.mcampos.entity.gdf;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.SimpleCodedTable;

/**
 * The persistent class for the regional_division database table.
 * 
 */
@Entity
@Table( name = "regional_division", schema = "gdf" )
public class RegionalDivision extends SimpleCodedTable<RegionalDivision>
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column( name = "dre_id_in", nullable = false, unique = true )
	private Integer id;

	@Column( name = "dre_code_ch", nullable = false, unique = true )
	private String code;

	@Column( name = "dre_description_ch", nullable = false )
	private String description;

	public RegionalDivision( )
	{
	}

	@Override
	public Integer getId( )
	{
		return this.id;
	}

	@Override
	public void setId( Integer dreIdIn )
	{
		this.id = dreIdIn;
	}

	@Override
	public String getCode( )
	{
		return this.code;
	}

	@Override
	public void setCode( String dreCodeCh )
	{
		this.code = dreCodeCh;
	}

	@Override
	public String getDescription( )
	{
		return this.description;
	}

	@Override
	public void setDescription( String dreDescriptionCh )
	{
		this.description = dreDescriptionCh;
	}

}