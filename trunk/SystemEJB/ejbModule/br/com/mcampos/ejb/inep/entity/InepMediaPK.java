package br.com.mcampos.ejb.inep.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the inep_media database table.
 * 
 */
@Embeddable
public class InepMediaPK implements Serializable, Comparable<InepMediaPK>
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "pct_id_in" )
	private Integer eventId;

	@Column( name = "usr_id_in" )
	private Integer companyId;

	@Column( name = "isc_id_ch" )
	private String subscriptionId;

	@Column( name = "med_id_in" )
	private Integer mediaId;

	public InepMediaPK( )
	{
	}

	public InepMediaPK( InepSubscriptionPK key )
	{
		set( key );
	}

	public void set( InepSubscriptionPK key )
	{
		if ( key != null ) {
			setEventId( key.getEventId( ) );
			setCompanyId( key.getCompanyId( ) );
			setSubscriptionId( key.getId( ) );
		}
		else {
			setEventId( null );
			setCompanyId( null );
			setSubscriptionId( null );
		}
	}

	public Integer getEventId( )
	{
		return eventId;
	}

	public void setEventId( Integer pctIdIn )
	{
		eventId = pctIdIn;
	}

	public Integer getCompanyId( )
	{
		return companyId;
	}

	public void setCompanyId( Integer usrIdIn )
	{
		companyId = usrIdIn;
	}

	public String getSubscriptionId( )
	{
		return subscriptionId;
	}

	public void setSubscriptionId( String iscIdCh )
	{
		subscriptionId = iscIdCh;
	}

	public Integer getMediaId( )
	{
		return mediaId;
	}

	public void setMediaId( Integer imdIdIn )
	{
		mediaId = imdIdIn;
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
		return eventId.equals( castOther.eventId )
				&& companyId.equals( castOther.companyId )
				&& subscriptionId.equals( castOther.subscriptionId )
				&& mediaId.equals( castOther.mediaId );
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + eventId.hashCode( );
		hash = hash * prime + companyId.hashCode( );
		hash = hash * prime + subscriptionId.hashCode( );
		hash = hash * prime + mediaId.hashCode( );

		return hash;
	}

	@Override
	public int compareTo( InepMediaPK o )
	{
		if ( o == null )
			return -1;

		int nRet = getEventId( ).compareTo( o.getEventId( ) );
		if ( nRet == 0 ) {
			nRet = getCompanyId( ).compareTo( o.getCompanyId( ) );
			if ( nRet == 0 ) {
				nRet = getSubscriptionId( ).compareTo( o.getSubscriptionId( ) );
				if ( nRet == 0 )
					nRet = getMediaId( ).compareTo( o.getMediaId( ) );
			}
		}
		return 0;
	}
}