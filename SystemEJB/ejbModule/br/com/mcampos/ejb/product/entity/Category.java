package br.com.mcampos.ejb.product.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import br.com.mcampos.ejb.core.entity.BaseCompanyEntity;

/**
 * The persistent class for the category database table.
 * 
 */
@Entity
public class Category extends BaseCompanyEntity implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CategoryPK id;

	@Column( name = "cat_description_tx" )
	private String description;

	@Column( name = "cat_name_ch" )
	private String name;

	public Category( )
	{
	}

	public CategoryPK getId( )
	{
		if ( id == null )
			id = new CategoryPK( );
		return id;
	}

	public void setId( CategoryPK id )
	{
		this.id = id;
	}

	public String getDescription( )
	{
		return description;
	}

	public void setDescription( String catDescriptionTx )
	{
		description = catDescriptionTx;
	}

	public String getName( )
	{
		return name;
	}

	public void setName( String catNameCh )
	{
		name = catNameCh;
	}

	@Override
	public void setCompanyId( Integer id )
	{
		getId( ).setCompanyId( id );
	}

}