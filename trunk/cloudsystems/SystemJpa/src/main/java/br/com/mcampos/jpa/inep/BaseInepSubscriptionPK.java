package br.com.mcampos.jpa.inep;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseInepSubscriptionPK extends BaseInepEventPK
{
	private static final long serialVersionUID = -7316321569695429964L;

	@Column( name = "isc_id_ch", nullable = false, columnDefinition = "varchar not null", length = 32 )
	private String subscriptionId;

	public BaseInepSubscriptionPK( )
	{
		super( );
	}

	public BaseInepSubscriptionPK( Integer companyId, Integer eventId, String id )
	{
		super( companyId, eventId );
		this.setSubscriptionId( id );
	}

	public String getSubscriptionId( )
	{
		return this.subscriptionId;
	}

	public void setSubscriptionId( String subscriptionId )
	{
		this.subscriptionId = subscriptionId;
	}

	public int compareTo( BaseInepSubscriptionPK o )
	{
		int nRet = super.compareTo( o );
		if ( nRet == 0 ) {
			nRet = this.getSubscriptionId( ).compareTo( o.getSubscriptionId( ) );
		}
		return nRet;
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( obj == null || !( obj instanceof BaseInepSubscriptionPK ) ) {
			return false;
		}
		BaseInepSubscriptionPK other = (BaseInepSubscriptionPK) obj;
		return super.equals( obj ) && this.getSubscriptionId( ).equals( other.getSubscriptionId( ) );
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = super.hashCode( );
		hash = hash * prime + this.getSubscriptionId( ).hashCode( );
		return hash;
	}

}
