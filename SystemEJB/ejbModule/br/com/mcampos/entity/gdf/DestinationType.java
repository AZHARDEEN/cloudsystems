package br.com.mcampos.entity.gdf;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.SimpleCodedTable;

/**
 * The persistent class for the destination_type database table.
 * 
 */
@Entity
@Table( name = "destination_type", schema = "gdf" )
public class DestinationType extends SimpleCodedTable<DestinationType>
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column( name = "dtp_id_in", nullable = false, unique = true )
	private Integer id;

	@Column( name = "dtp_code_ch", nullable = false, unique = true )
	private String code;

	@Column( name = "dtp_description_ch", nullable = false )
	private String description;

	public DestinationType( )
	{
	}

	@Override
	public Integer getId( )
	{
		return this.id;
	}

	@Override
	public void setId( Integer dtpIdIn )
	{
		this.id = dtpIdIn;
	}

	@Override
	public String getCode( )
	{
		return this.code;
	}

	@Override
	public void setCode( String dtpCodeCh )
	{
		this.code = dtpCodeCh;
	}

	@Override
	public String getDescription( )
	{
		return this.description;
	}

	@Override
	public void setDescription( String dtpDescriptionCh )
	{
		this.description = dtpDescriptionCh;
	}

}