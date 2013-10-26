package br.com.mcampos.jpa.inep;

import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class BaseInepSubscription extends BaseInepEvent
{
	private static final long serialVersionUID = -8725499774946623661L;

	@Override
	public abstract BaseInepSubscriptionPK getId( );

	@ManyToOne
	@JoinColumns( {
			@JoinColumn(
					name = "usr_id_in", referencedColumnName = "usr_id_in", updatable = false, insertable = false, nullable = false ),
			@JoinColumn(
					name = "pct_id_in", referencedColumnName = "pct_id_in", updatable = false, insertable = false, nullable = false ),
			@JoinColumn(
					name = "isc_id_ch", referencedColumnName = "isc_id_ch", updatable = false, insertable = false, nullable = false ) } )
	private InepSubscription subscription;

	public BaseInepSubscription( )
	{
		super( );
	}

	public BaseInepSubscription( @NotNull InepSubscription sub )
	{
		super( sub.getEvent( ) );
		this.setSubscription( sub );
	}

	public InepSubscription getSubscription( )
	{
		return this.subscription;
	}

	public void setSubscription( InepSubscription subscription )
	{
		this.subscription = subscription;
		if ( subscription != null ) {
			this.getId( ).setCompanyId( subscription.getId( ).getCompanyId( ) );
			this.getId( ).setEventId( subscription.getId( ).getEventId( ) );
			this.getId( ).setSubscriptionId( subscription.getId( ).getId( ) );
		}
		else {
			this.getId( ).setCompanyId( null );
			this.getId( ).setEventId( null );
			this.getId( ).setSubscriptionId( null );
		}
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( obj != null ) {
			return this.getId( ).equals( ( (BaseInepSubscription) obj ).getId( ) );
		}
		else {
			return false;
		}
	}

	public int compareTo( BaseInepSubscription other )
	{
		return this.getId( ).compareTo( other.getId( ) );
	}

}
