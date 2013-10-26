package br.com.mcampos.jpa.inep;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the inep_media database table.
 * 
 */
@Embeddable
public class InepMediaPK extends BaseInepSubscriptionPK implements Serializable, Comparable<InepMediaPK>
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "med_id_in" )
	private Integer mediaId;

	public InepMediaPK( )
	{
	}

	public InepMediaPK( InepSubscriptionPK key )
	{
		this.set( key );
	}

	public void set( InepSubscriptionPK key )
	{
		if ( key != null ) {
			this.setEventId( key.getEventId( ) );
			this.setCompanyId( key.getCompanyId( ) );
			this.setSubscriptionId( key.getId( ) );
		}
		else {
			this.setEventId( null );
			this.setCompanyId( null );
			this.setSubscriptionId( null );
		}
	}

	public Integer getMediaId( )
	{
		return this.mediaId;
	}

	public void setMediaId( Integer imdIdIn )
	{
		this.mediaId = imdIdIn;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( this == other ) {
			return true;
		}
		if ( !( other instanceof InepMediaPK ) ) {
			return false;
		}
		InepMediaPK castOther = (InepMediaPK) other;
		return super.equals( other ) && this.mediaId.equals( castOther.mediaId );
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + super.hashCode( );
		hash = hash * prime + this.mediaId.hashCode( );

		return hash;
	}

	@Override
	public int compareTo( InepMediaPK o )
	{
		if ( o == null ) {
			return -1;
		}
		int nRet = super.compareTo( o );
		if ( nRet == 0 ) {
			nRet = this.getMediaId( ).compareTo( o.getMediaId( ) );
		}
		return 0;
	}
}