package br.com.mcampos.jpa.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the price database table.
 *
 */
@Entity
@NamedQuery( name = "Price.findAll", query = "SELECT p FROM Price p" )
@Table( name = "price", schema = "public" )
public class Price extends BaseProduct implements Serializable, Comparable<Price>
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PricePK id;

	@Column( name = "pct_to_dt" )
	@Temporal( TemporalType.DATE )
	private Date toDate;

	@Column( name = "prc_value_nm" )
	private BigDecimal value;

	public Price( )
	{
	}

	@Override
	public PricePK getId( )
	{
		if ( id == null ) {
			id = new PricePK( );
		}
		return id;
	}

	public void setId( PricePK id )
	{
		this.id = id;
	}

	public Date getToDate( )
	{
		return toDate;
	}

	public void setToDate( Date pctToDt )
	{
		toDate = pctToDt;
	}

	public BigDecimal getValue( )
	{
		return value;
	}

	public void setValue( BigDecimal prcValueNm )
	{
		value = prcValueNm;
	}

	@Override
	public int compareTo( Price o )
	{
		return getId( ).compareTo( o.getId( ) );
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( obj == this ) {
			return true;
		}
		if ( obj instanceof Price ) {
			Price other = (Price) obj;
			return getId( ).equals( other.getId( ) );
		}
		if ( obj instanceof PricePK ) {
			PricePK other = (PricePK) obj;
			return getId( ).equals( other );
		}
		else {
			return false;
		}
	}
}