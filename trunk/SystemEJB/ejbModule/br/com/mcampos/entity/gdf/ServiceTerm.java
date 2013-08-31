package br.com.mcampos.entity.gdf;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.mcampos.ejb.core.SimpleCodedTable;

/**
 * The persistent class for the service_term database table.
 * 
 */
@Entity
@Table( name = "service_term", schema = "gdf" )
public class ServiceTerm extends SimpleCodedTable<ServiceTerm>
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column( name = "svt_id_in", nullable = false, unique = true )
	private Integer id;

	@Column( name = "svt_code_ch", nullable = false, unique = true )
	private String code;

	@Column( name = "svt_description_ch", nullable = false )
	private String description;

	public ServiceTerm( )
	{
	}

	@Override
	public Integer getId( )
	{
		return this.id;
	}

	@Override
	public void setId( Integer svtIdIn )
	{
		this.id = svtIdIn;
	}

	@Override
	public String getCode( )
	{
		return this.code;
	}

	@Override
	public void setCode( String svtCodeCh )
	{
		this.code = svtCodeCh;
	}

	@Override
	public String getDescription( )
	{
		return this.description;
	}

	@Override
	public void setDescription( String svtDescriptionCh )
	{
		this.description = svtDescriptionCh;
	}

}