package br.com.mcampos.entity.inep;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.com.mcampos.entity.BaseCompanyPK;
import br.com.mcampos.entity.user.Collaborator;
import br.com.mcampos.entity.user.Company;

/**
 * The primary key class for the inep_package database table.
 * 
 */
@Embeddable
public class InepEventPK extends BaseCompanyPK implements Serializable, Comparable<InepEventPK>
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "pct_id_in" )
	private Integer id;

	public InepEventPK( )
	{
	}

	public InepEventPK( Company c, Integer id )
	{
		setCompanyId( c.getId( ) );
		setId( id );
	}

	public Integer getId( )
	{
		return id;
	}

	public void setId( Integer pctIdIn )
	{
		id = pctIdIn;
	}

	public void setCompanyId( Collaborator auth )
	{
		setCompanyId( auth.getCompany( ).getId( ) );
	}

	@Override
	public boolean equals( Object other )
	{
		if ( this == other ) {
			return true;
		}
		if ( !( other instanceof InepEventPK ) ) {
			return false;
		}
		InepEventPK castOther = (InepEventPK) other;
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
	public int compareTo( InepEventPK o )
	{
		int nRet;

		nRet = super.compareTo( o );
		if ( nRet == 0 ) {
			nRet = getId( ).compareTo( o.getId( ) );
		}
		return nRet;
	}
}