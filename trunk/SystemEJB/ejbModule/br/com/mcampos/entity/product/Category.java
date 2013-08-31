package br.com.mcampos.entity.product;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import br.com.mcampos.entity.BaseCompanyEntity;

/**
 * The persistent class for the category database table.
 * 
 */
@Entity
public class Category extends BaseCompanyEntity implements Serializable, Comparable<Category>
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

	@Override
	public int compareTo( Category o )
	{
		if ( o == null )
			return -1;
		return this.getId( ).compareTo( o.getId( ) );
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( obj == null )
			return false;
		if ( obj instanceof Category ) {
			Category other = (Category) obj;

			return this.getId( ).equals( other.getId( ) );
		}
		else
			return false;
	}

	@Override
	public String toString( )
	{
		return getId( ).getId( ).toString( ) + "- " + getName( );
	}

}