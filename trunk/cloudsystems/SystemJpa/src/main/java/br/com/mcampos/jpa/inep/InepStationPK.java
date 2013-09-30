package br.com.mcampos.jpa.inep;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the inep_station database table.
 * 
 */
@Embeddable
public class InepStationPK extends BaseInepEventPK implements Serializable, Comparable<InepStationPK>
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "cli_seq_in" )
	private Integer clientId;

	public InepStationPK( )
	{
	}

	public InepStationPK( Integer companyId, Integer eventId, Integer sequence )
	{
		super( companyId, eventId );
		this.setClientId( sequence );
	}

	public Integer getClientId( )
	{
		return this.clientId;
	}

	public void setClientId( Integer cliSeqIn )
	{
		this.clientId = cliSeqIn;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( this == other ) {
			return true;
		}
		if ( !( other instanceof InepStationPK ) ) {
			return false;
		}
		InepStationPK castOther = (InepStationPK) other;
		return super.equals( other ) && this.clientId.equals( castOther.clientId );
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * super.hashCode( );
		hash = hash * prime + this.clientId.hashCode( );

		return hash;
	}

	@Override
	public int compareTo( InepStationPK o )
	{
		int nRet = super.compareTo( o );
		if ( nRet == 0 ) {
			nRet = this.getClientId( ).compareTo( o.getClientId( ) );
		}

		return nRet;
	}
}