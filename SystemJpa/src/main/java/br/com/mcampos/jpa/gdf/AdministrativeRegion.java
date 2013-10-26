package br.com.mcampos.jpa.gdf;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.jpa.SimpleCodedTable;

/**
 * The persistent class for the administrative_region database table.
 * 
 */
@Entity
@Table( name = "administrative_region", schema = "gdf" )
public class AdministrativeRegion extends SimpleCodedTable<AdministrativeRegion>
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column( name = "adr_id_in", nullable = false, unique = true )
	private Integer id;

	@Column( name = "adr_code_ch", nullable = false, unique = true )
	private String code;

	@Column( name = "adr_description_ch", nullable = false )
	private String description;

	public AdministrativeRegion( )
	{
	}

	@Override
	public Integer getId( )
	{
		return this.id;
	}

	@Override
	public void setId( Integer adrIdIn )
	{
		this.id = adrIdIn;
	}

	@Override
	public String getCode( )
	{
		return this.code;
	}

	@Override
	public void setCode( String adrCodeCh )
	{
		this.code = adrCodeCh;
	}

	@Override
	public String getDescription( )
	{
		return this.description;
	}

	@Override
	public void setDescription( String adrDescriptionCh )
	{
		this.description = adrDescriptionCh;
	}
}