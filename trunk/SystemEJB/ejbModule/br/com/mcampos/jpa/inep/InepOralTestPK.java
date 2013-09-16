package br.com.mcampos.jpa.inep;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the inep_oral_test database table.
 * 
 */
@Embeddable
public class InepOralTestPK implements Serializable
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "pct_id_in" )
	private Integer eventId;

	@Column( name = "usr_id_in" )
	private Integer userId;

	@Column( name = "isc_id_ch" )
	private String subscriptionId;

	public InepOralTestPK( )
	{
	}

	public Integer getEventId( )
	{
		return eventId;
	}

	public void setEventId( Integer pctIdIn )
	{
		eventId = pctIdIn;
	}

	public Integer getUserId( )
	{
		return userId;
	}

	public void setUserId( Integer usrIdIn )
	{
		userId = usrIdIn;
	}

	public String getSubscriptionId( )
	{
		return subscriptionId;
	}

	public void setSubscriptionId( String iscIdCh )
	{
		subscriptionId = iscIdCh;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( this == other ) {
			return true;
		}
		if ( !( other instanceof InepOralTestPK ) ) {
			return false;
		}
		InepOralTestPK castOther = (InepOralTestPK) other;
		return eventId.equals( castOther.eventId )
				&& userId.equals( castOther.userId )
				&& subscriptionId.equals( castOther.subscriptionId );
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + eventId.hashCode( );
		hash = hash * prime + userId.hashCode( );
		hash = hash * prime + subscriptionId.hashCode( );

		return hash;
	}
}