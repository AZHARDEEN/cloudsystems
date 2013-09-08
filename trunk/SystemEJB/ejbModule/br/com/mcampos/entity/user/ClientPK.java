package br.com.mcampos.entity.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.com.mcampos.entity.BaseCompanyPK;

/**
 * The primary key class for the client database table.
 * 
 */
@Embeddable
public class ClientPK extends BaseCompanyPK implements Serializable, Comparable<ClientPK>
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "cli_seq_in", unique = true, nullable = false )
	private Integer sequence;

	public ClientPK( )
	{
	}

	public void set( Company c )
	{
		setCompanyId( c.getId( ) );
	}

	public Integer getSequence( )
	{
		return sequence;
	}

	public void setSequence( Integer cliSeqIn )
	{
		sequence = cliSeqIn;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( this == other ) {
			return true;
		}
		if ( other == null || ( other instanceof ClientPK ) ) {
			return false;
		}
		ClientPK castOther = (ClientPK) other;
		return super.equals( other ) && getSequence( ).equals( castOther.sequence );

	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + getCompanyId( ).hashCode( );
		hash = hash * prime + getSequence( ).hashCode( );

		return hash;
	}

	@Override
	public int compareTo( ClientPK o )
	{
		int nRet;

		nRet = super.compareTo( o );
		if ( nRet == 0 ) {
			nRet = getSequence( ).compareTo( o.getSequence( ) );
		}
		return nRet;
	}
}