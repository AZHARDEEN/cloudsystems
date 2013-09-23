package br.com.mcampos.jpa.product;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.mcampos.jpa.BaseCompanyEntity;

/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@Table( name = "category", schema = "public" )
@NamedQueries( {
		@NamedQuery( name = Category.getNextId, query = "select coalesce ( max(o.id.id), 0 ) + 1 from Category o where o.id.companyId = ?1" )
} )
public class Category extends BaseCompanyEntity implements Serializable, Comparable<Category>
{
	private static final long serialVersionUID = 1L;

	public static final String getNextId = "Category.getNextId";

	@EmbeddedId
	private CategoryPK id;

	@Column( name = "cat_description_tx" )
	private String description;

	@Column( name = "cat_name_ch" )
	private String name;

	public Category( )
	{
	}

	@Override
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
		description = catDescriptionTx != null ? catDescriptionTx.trim( ) : null;
	}

	public String getName( )
	{
		return name;
	}

	public void setName( String catNameCh )
	{
		name = catNameCh != null ? catNameCh.trim( ) : null;
	}

	@Override
	public int compareTo( Category o )
	{
		if ( o == null )
			return -1;
		return getId( ).compareTo( o.getId( ) );
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( obj == null )
			return false;
		if ( obj instanceof Category ) {
			Category other = (Category) obj;

			return getId( ).equals( other.getId( ) );
		}
		else
			return false;
	}

	@Override
	public String toString( )
	{
		return getId( ).getId( ).toString( ) + "- " + getName( );
	}

	@Override
	public Integer getCompanyId( )
	{
		return getId( ).getCompanyId( );
	}

	@Transient
	/*
	 * Esta funcao é usada na view (category.zul) par facilitar o renderer
	 */
	public Integer getCategoryId( )
	{
		return getId( ).getId( );
	}

}