package br.com.mcampos.entity.product;

import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseProduct
{
	@ManyToOne( optional = false )
	@JoinColumns( {
			@JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in", nullable = false, insertable = false, updatable = false ),
			@JoinColumn( name = "prd_id_in", referencedColumnName = "prd_id_in", nullable = false, insertable = false, updatable = false ),
	} )
	private Product product;

	public abstract BaseProductPK getId( );

	public BaseProduct( )
	{

	}

	public BaseProduct( Product product )
	{
		setProduct( product );
	}

	public Product getProduct( )
	{
		return product;
	}

	public void setProduct( Product product )
	{
		this.product = product;
		if ( product != null ) {
			getId( ).setCompanyId( product.getCompanyId( ) );
			getId( ).setProductId( product.getProductId( ) );
		}
	}

}
