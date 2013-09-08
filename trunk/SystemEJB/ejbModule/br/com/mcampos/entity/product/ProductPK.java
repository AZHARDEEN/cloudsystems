package br.com.mcampos.entity.product;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.com.mcampos.entity.BaseCompanyPK;

/**
 * The primary key class for the product database table.
 * 
 */
@Embeddable
public class ProductPK extends BaseCompanyPK implements Serializable, Comparable<ProductPK>
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "prd_id_in" )
	private Integer id;

	public ProductPK( )
	{
	}

	public Integer getId( )
	{
		return id;
	}

	public void setId( Integer prdIdIn )
	{
		id = prdIdIn;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( this == other ) {
			return true;
		}
		if ( !( other instanceof ProductPK ) ) {
			return false;
		}
		ProductPK castOther = (ProductPK) other;
		return super.equals( castOther ) && getId( ).equals( castOther.getId( ) );
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + getCompanyId( ).hashCode( );
		hash = hash * prime + getId( ).hashCode( );

		return hash;
	}

	@Override
	public int compareTo( ProductPK o )
	{
		int nRet = super.compareTo( o );
		if ( nRet == 0 )
			nRet = getId( ).compareTo( o.getId( ) );
		return nRet;
	}
}