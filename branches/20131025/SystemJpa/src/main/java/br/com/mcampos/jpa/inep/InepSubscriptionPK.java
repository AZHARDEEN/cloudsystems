package br.com.mcampos.jpa.inep;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the inep_subscription database table.
 * 
 */
@Embeddable
public class InepSubscriptionPK extends BaseInepEventPK implements Serializable, Comparable<InepSubscriptionPK>
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

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
		super( companyId, eventId );
		this.id = id;
	}

	public void set( InepEvent t )
	{
		this.setCompanyId( t.getId( ).getCompanyId( ) );
		this.setEventId( t.getId( ).getId( ) );
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
		if( this == other ) {
			return true;
		}
		if( !(other instanceof InepSubscriptionPK) ) {
			return false;
		}
		InepSubscriptionPK castOther = (InepSubscriptionPK) other;
		return super.equals( castOther ) && this.id.equals( castOther.id );

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

	@Override
	public int compareTo( InepSubscriptionPK o )
	{
		int nRet;

		nRet = super.compareTo( o );
		if( nRet == 0 ) {
			nRet = this.getId( ).compareTo( o.getId( ) );
		}
		return nRet;
	}
}