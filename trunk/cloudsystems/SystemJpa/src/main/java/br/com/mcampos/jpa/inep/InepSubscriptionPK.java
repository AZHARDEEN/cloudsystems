package br.com.mcampos.jpa.inep;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the inep_subscription database table.
 * 
 */
@Embeddable
public class InepSubscriptionPK implements Serializable, Comparable<InepSubscriptionPK>
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "usr_id_in" )
	private Integer companyId;

	@Column( name = "pct_id_in" )
	private Integer eventId;

	@Column( name = "isc_id_ch" )
	private String id;

	public InepSubscriptionPK( )
	{
	}

	public InepSubscriptionPK( InepEvent event )
	{
		this.set( event );
	}

	public InepSubscriptionPK( InepEvent event, String id )
	{
		this.set( event );
		this.setId( id );
	}

	public InepSubscriptionPK( Integer companyId, Integer eventId, String id )
	{
		super( );
		this.companyId = companyId;
		this.eventId = eventId;
		this.id = id;
	}

	public void set( InepEvent t )
	{
		this.setCompanyId( t.getId( ).getCompanyId( ) );
		this.setEventId( t.getId( ).getId( ) );
	}

	public Integer getCompanyId( )
	{
		return this.companyId;
	}

	public void setCompanyId( Integer usrIdIn )
	{
		this.companyId = usrIdIn;
	}

	public Integer getEventId( )
	{
		return this.eventId;
	}

	public void setEventId( Integer pctIdIn )
	{
		this.eventId = pctIdIn;
	}

	public String getId( )
	{
		return this.id;
	}

	public void setId( String iscIdCh )
	{
		this.id = iscIdCh;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( this == other ) {
			return true;
		}
		if ( !( other instanceof InepSubscriptionPK ) ) {
			return false;
		}
		InepSubscriptionPK castOther = (InepSubscriptionPK) other;
		return this.companyId.equals( castOther.companyId )
				&& this.eventId.equals( castOther.eventId )
				&& this.id.equals( castOther.id );

	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.companyId.hashCode( );
		hash = hash * prime + this.eventId.hashCode( );
		hash = hash * prime + this.id.hashCode( );

		return hash;
	}

	@Override
	public int compareTo( InepSubscriptionPK o )
	{
		int nRet;

		nRet = this.getCompanyId( ).compareTo( o.getCompanyId( ) );
		if ( nRet == 0 ) {
			nRet = this.getEventId( ).compareTo( o.getEventId( ) );
		}
		if ( nRet == 0 ) {
			nRet = this.getId( ).compareTo( o.getId( ) );
		}
		return nRet;
	}
}