package br.com.mcampos.jpa.product;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.com.mcampos.jpa.BaseCompanyPK;

/**
 * The primary key class for the category database table.
 * 
 */
@Embeddable
public class CategoryPK extends BaseCompanyPK implements Serializable, Comparable<CategoryPK>
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "cat_id_in" )
	private Integer id;

	public CategoryPK( )
	{
	}

	public Integer getId( )
	{
		return id;
	}

	public void setId( Integer catIdIn )
	{
		id = catIdIn;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( this == other ) {
			return true;
		}
		if ( !( other instanceof CategoryPK ) ) {
			return false;
		}
		CategoryPK castOther = (CategoryPK) other;
		return super.equals( castOther )
				&& id.equals( castOther.id );
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + getCompanyId( ).hashCode( );
		hash = hash * prime + id.hashCode( );

		return hash;
	}

	@Override
	public int compareTo( CategoryPK c )
	{
		if ( c == null )
			return -1;
		int nRet = super.compareTo( c );
		if ( nRet == 0 )
			nRet = getId( ).compareTo( c.getId( ) );
		return nRet;
	}

	@Override
	public String toString( )
	{
		return getCompanyId( ).toString( ) + " - " + getId( ).toString( );
	}
}
