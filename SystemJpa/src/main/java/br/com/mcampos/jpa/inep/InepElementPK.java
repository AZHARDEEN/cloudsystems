package br.com.mcampos.jpa.inep;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the inep_element database table.
 * 
 */
@Embeddable
public class InepElementPK extends BaseInepSubscriptionPK implements Serializable, Comparable<InepElementPK>
{
	private static final long serialVersionUID = 1L;

	@Column( name = "iel_id_in", nullable = false, updatable = true, insertable = true, columnDefinition = "Integer" )
	private Integer id;

	public InepElementPK( )
	{
	}

	public Integer getId( )
	{
		return this.id;
	}

	public void setId( Integer ielIdIn )
	{
		this.id = ielIdIn;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( this == other ) {
			return true;
		}
		if ( !( other instanceof InepElementPK ) ) {
			return false;
		}
		InepElementPK castOther = (InepElementPK) other;
		return super.equals( other ) && this.id.equals( castOther.id );
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + super.hashCode( );
		hash = hash * prime + this.id.hashCode( );

		return hash;
	}

	@Override
	public int compareTo( InepElementPK o )
	{
		int nRet = super.compareTo( o );
		if ( nRet == 0 ) {
			nRet = this.getId( ).compareTo( o.getId( ) );
		}
		return nRet;
	}

}