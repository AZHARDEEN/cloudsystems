package br.com.mcampos.jpa.product;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the product_keyword database table.
 * 
 */
@Embeddable
public class ProductKeywordPK extends BaseProductPK implements Serializable, Comparable<ProductKeywordPK>
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "prd_keywork_ch" )
	private String id;

	public ProductKeywordPK( )
	{
	}

	public String getId( )
	{
		return id;
	}

	public void setId( String prdKeyworkCh )
	{
		id = prdKeyworkCh;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( this == other ) {
			return true;
		}
		if ( !( other instanceof ProductKeywordPK ) ) {
			return false;
		}
		ProductKeywordPK castOther = (ProductKeywordPK) other;
		return super.equals( castOther )
				&& getId( ).equals( castOther.getId( ) );
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + getCompanyId( ).hashCode( );
		hash = hash * prime + getProductId( ).hashCode( );
		hash = hash * prime + getId( ).hashCode( );

		return hash;
	}

	@Override
	public int compareTo( ProductKeywordPK o )
	{
		int nRet = super.compareTo( o );
		if ( nRet == 0 )
			nRet = getId( ).compareTo( o.getId( ) );
		return nRet;
	}
}