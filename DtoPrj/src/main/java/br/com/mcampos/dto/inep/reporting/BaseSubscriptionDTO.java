package br.com.mcampos.dto.inep.reporting;

import java.io.Serializable;

public abstract class BaseSubscriptionDTO implements Serializable, Comparable<BaseSubscriptionDTO>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 374032568123370296L;
	private String subscription;

	public abstract String[ ] getFields( );

	public abstract String getHeader( );

	public String getSubscription( )
	{
		return this.subscription;
	}

	public void setSubscription( String subscription )
	{
		this.subscription = subscription;
	}

	@Override
	public int compareTo( BaseSubscriptionDTO o )
	{
		return getSubscription( ).compareTo( o.getSubscription( ) );
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( obj instanceof NotasFinaisDTO ) {
			return getSubscription( ).equals( ( (NotasFinaisDTO) obj ).getSubscription( ) );
		}
		else if ( obj instanceof String ) {
			return getSubscription( ).endsWith( ( (String) obj ) );
		}
		else {
			return false;
		}
	}

}
