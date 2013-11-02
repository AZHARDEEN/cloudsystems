package br.com.mcampos.jpa.product;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The primary key class for the price database table.
 *
 */
@Embeddable
public class PricePK extends BaseProductPK implements Serializable, Comparable<PricePK>
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "pct_id_in", insertable = true, updatable = true, nullable = false )
	private Integer priceTypeId;

	@Temporal( TemporalType.TIMESTAMP )
	@Column( name = "pct_from_dt" )
	private java.util.Date fromDate;

	public PricePK( )
	{
	}

	public Integer getPriceTypeId( )
	{
		return priceTypeId;
	}

	public void setPriceTypeId( Integer pctIdIn )
	{
		priceTypeId = pctIdIn;
	}

	public java.util.Date getFromDate( )
	{
		return fromDate;
	}

	public void setFromDate( java.util.Date pctFromDt )
	{
		fromDate = pctFromDt;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( this == other ) {
			return true;
		}
		if ( !( other instanceof PricePK ) ) {
			return false;
		}
		PricePK castOther = (PricePK) other;
		return super.equals( castOther )
				&& priceTypeId.equals( castOther.priceTypeId )
				&& fromDate.equals( castOther.fromDate );
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + getCompanyId( ).hashCode( );
		hash = hash * prime + getProductId( ).hashCode( );
		hash = hash * prime + priceTypeId.hashCode( );
		hash = hash * prime + fromDate.hashCode( );

		return hash;
	}

	@Override
	public int compareTo( PricePK o )
	{
		int nRet = super.compareTo( o );
		if ( nRet == 0 ) {
			nRet = getPriceTypeId( ).compareTo( o.getPriceTypeId( ) );
			if ( nRet == 0 ) {
				nRet = getFromDate( ).compareTo( o.getFromDate( ) );
			}
		}
		return 0;
	}
}