package br.com.mcampos.jpa.user;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.com.mcampos.jpa.BaseCompanyPK;

@Embeddable
public class CollaboratorTypePK extends BaseCompanyPK implements Comparable<CollaboratorTypePK>
{
	private static final long serialVersionUID = 2153564925322274084L;

	@Column( name = "clt_id_in", unique = true, nullable = false )
	private Integer id;

	public CollaboratorTypePK( )
	{
		super( );
	}

	public CollaboratorTypePK( Integer companyId, Integer typeId )
	{
		super( companyId );
		this.setId( typeId );
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( this == obj ) {
			return true;
		}
		if ( obj == null || !( obj instanceof CollaboratorTypePK ) ) {
			return false;
		}
		return super.equals( obj ) && ( (CollaboratorTypePK) obj ).getId( ).equals( this.getId( ) );
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + super.hashCode( );
		hash = hash * prime + this.getId( ).hashCode( );

		return hash;
	}

	public Integer getId( )
	{
		return this.id;
	}

	public void setId( Integer id )
	{
		this.id = id;
	}

	@Override
	public int compareTo( CollaboratorTypePK other )
	{
		int nRet = super.compareTo( other );
		if ( nRet == 0 ) {
			nRet = this.getId( ).compareTo( other.getId( ) );
		}
		return nRet;
	}

	@Override
	public String toString( )
	{
		return this.getId( ).toString( );
	}

}
