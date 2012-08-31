package br.com.mcampos.ejb.gdf;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.SimpleCodedTable;

/**
 * The persistent class for the service_order_type database table.
 * 
 */
@Entity
@Table( name = "service_order_type", schema = "gdf" )
public class ServiceOrderType extends SimpleCodedTable<ServiceOrderType>
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column( name = "sot_id_in", nullable = false, unique = true )
	private Integer id;

	@Column( name = "sot_code_ch", nullable = false, unique = true )
	private String code;

	@Column( name = "sot_description_ch", nullable = false )
	private String description;

	public ServiceOrderType( )
	{
	}

	@Override
	public Integer getId( )
	{
		return this.id;
	}

	@Override
	public void setId( Integer sotIdIn )
	{
		this.id = sotIdIn;
	}

	@Override
	public String getCode( )
	{
		return this.code;
	}

	@Override
	public void setCode( String sotCodeCh )
	{
		this.code = sotCodeCh;
	}

	@Override
	public String getDescription( )
	{
		return this.description;
	}

	@Override
	public void setDescription( String sotDescriptionCh )
	{
		this.description = sotDescriptionCh;
	}

}