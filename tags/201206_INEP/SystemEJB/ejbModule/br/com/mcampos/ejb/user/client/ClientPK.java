package br.com.mcampos.ejb.user.client;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.com.mcampos.ejb.user.company.Company;

/**
 * The primary key class for the client database table.
 * 
 */
@Embeddable
public class ClientPK implements Serializable, Comparable<ClientPK>
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "usr_id_in", unique = true, nullable = false )
	private Integer companyId;

	@Column( name = "cli_seq_in", unique = true, nullable = false )
	private Integer sequence;

	public ClientPK( )
	{
	}

	public void set( Company c )
	{
		setCompanyId( c.getId( ) );
	}

	public Integer getCompanyId( )
	{
		return this.companyId;
	}

	public void setCompanyId( Integer usrIdIn )
	{
		this.companyId = usrIdIn;
	}

	public Integer getSequence( )
	{
		return this.sequence;
	}

	public void setSequence( Integer cliSeqIn )
	{
		this.sequence = cliSeqIn;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( this == other ) {
			return true;
		}
		if ( !( other instanceof ClientPK ) ) {
			return false;
		}
		ClientPK castOther = (ClientPK) other;
		return getCompanyId( ).equals( castOther.companyId )
				&& getSequence( ).equals( castOther.sequence );

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

		nRet = getCompanyId( ).compareTo( o.getCompanyId( ) );
		if ( nRet == 0 ) {
			nRet = getSequence( ).compareTo( o.getSequence( ) );
		}
		return nRet;
	}
}