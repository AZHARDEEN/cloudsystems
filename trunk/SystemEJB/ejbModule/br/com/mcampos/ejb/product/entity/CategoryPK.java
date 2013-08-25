package br.com.mcampos.ejb.product.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the category database table.
 * 
 */
@Embeddable
public class CategoryPK implements Serializable, Comparable<CategoryPK>
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "usr_id_in" )
	private Integer companyId;

	@Column( name = "cat_id_in" )
	private Integer id;

	public CategoryPK( )
	{
	}

	public Integer getCompanyId( )
	{
		return companyId;
	}

	public void setCompanyId( Integer usrIdIn )
	{
		companyId = usrIdIn;
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
		return companyId.equals( castOther.companyId )
				&& id.equals( castOther.id );
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + companyId.hashCode( );
		hash = hash * prime + id.hashCode( );

		return hash;
	}

	@Override
	public int compareTo( CategoryPK c )
	{
		if ( c == null )
			return -1;
		int nRet = getCompanyId( ).compareTo( c.getCompanyId( ) );
		if ( nRet == 0 )
			nRet = getId( ).compareTo( c.getId( ) );
		return nRet;
	}
}
