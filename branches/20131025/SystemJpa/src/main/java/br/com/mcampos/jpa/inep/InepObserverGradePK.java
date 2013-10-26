package br.com.mcampos.jpa.inep;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the inep_observer_grade database table.
 * 
 */
@Embeddable
public class InepObserverGradePK extends BaseInepSubscriptionPK implements Serializable
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "iog_id_in" )
	private Integer id;

	public InepObserverGradePK( )
	{
	}

	public Integer getId( )
	{
		return this.id;
	}

	public void setId( Integer iogIdIn )
	{
		this.id = iogIdIn;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( this == other ) {
			return true;
		}
		if ( !( other instanceof InepObserverGradePK ) ) {
			return false;
		}
		InepObserverGradePK castOther = (InepObserverGradePK) other;
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
}