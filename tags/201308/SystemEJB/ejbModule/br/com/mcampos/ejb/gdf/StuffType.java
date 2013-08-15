package br.com.mcampos.ejb.gdf;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.SimpleCodedTable;

/**
 * The persistent class for the stuff_type database table.
 * 
 */
@Entity
@Table( name = "stuff_type", schema = "gdf" )
public class StuffType extends SimpleCodedTable<StuffType>
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column( name = "sft_id_in", nullable = false, unique = true )
	private Integer id;

	@Column( name = "sft_code_ch", nullable = false, unique = true )
	private String code;

	@Column( name = "sft_description_ch", nullable = false )
	private String description;

	public StuffType( )
	{
	}

	@Override
	public Integer getId( )
	{
		return this.id;
	}

	@Override
	public void setId( Integer sftIdIn )
	{
		this.id = sftIdIn;
	}

	@Override
	public String getCode( )
	{
		return this.code;
	}

	@Override
	public void setCode( String sftCodeCh )
	{
		this.code = sftCodeCh;
	}

	@Override
	public String getDescription( )
	{
		return this.description;
	}

	@Override
	public void setDescription( String sftDescriptionCh )
	{
		this.description = sftDescriptionCh;
	}

}