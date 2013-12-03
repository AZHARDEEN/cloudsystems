package br.com.mcampos.jpa.inep;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the inep_golden_test database table.
 * 
 */
@Embeddable
public class InepGoldenTestPK extends BaseInepSubscriptionPK implements Serializable
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "tsk_id_in", unique = true, nullable = false )
	private Integer tskIdIn;

	public InepGoldenTestPK( )
	{
	}

	public Integer getTskIdIn( )
	{
		return this.tskIdIn;
	}

	public void setTskIdIn( Integer tskIdIn )
	{
		this.tskIdIn = tskIdIn;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( this == other ) {
			return true;
		}
		if ( !( other instanceof InepGoldenTestPK ) ) {
			return false;
		}
		InepGoldenTestPK castOther = (InepGoldenTestPK) other;
		return super.equals( castOther )
				&& this.tskIdIn.equals( castOther.tskIdIn );
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + super.hashCode( );
		hash = hash * prime + this.tskIdIn.hashCode( );

		return hash;
	}
}