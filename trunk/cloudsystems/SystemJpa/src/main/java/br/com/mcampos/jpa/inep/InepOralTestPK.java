package br.com.mcampos.jpa.inep;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * The primary key class for the inep_oral_test database table.
 * 
 */
@Embeddable
public class InepOralTestPK extends BaseInepSubscriptionPK implements Serializable
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Override
	public boolean equals( Object other )
	{
		return super.equals( other );
	}

	@Override
	public int hashCode( )
	{
		return super.hashCode( );
	}
}