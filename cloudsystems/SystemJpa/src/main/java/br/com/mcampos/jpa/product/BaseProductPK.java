package br.com.mcampos.jpa.product;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import br.com.mcampos.jpa.BaseCompanyPK;

@MappedSuperclass
public abstract class BaseProductPK extends BaseCompanyPK
{
	private static final long serialVersionUID = -6421717762510849993L;

	@Column( name = "prd_id_in", nullable = false, updatable = true, insertable = true )
	private Integer productId;

	public Integer getProductId( )
	{
		return productId;
	}

	public void setProductId( Integer productId )
	{
		this.productId = productId;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( this == other ) {
			return true;
		}
		if ( !( other instanceof BaseProductPK ) ) {
			return false;
		}
		BaseProductPK castOther = (BaseProductPK) other;
		return super.equals( castOther ) && getProductId( ).equals( castOther.getProductId( ) );
	}

	public int compareTo( BaseProductPK o )
	{
		int nRet = super.compareTo( o );
		if ( nRet == 0 )
			nRet = getProductId( ).compareTo( o.getProductId( ) );
		return nRet;
	}

}
