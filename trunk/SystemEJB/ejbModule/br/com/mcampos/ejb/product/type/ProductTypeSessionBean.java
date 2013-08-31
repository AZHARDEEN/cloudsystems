package br.com.mcampos.ejb.product.type;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.entity.product.ProductType;

/**
 * Session Bean implementation class ProductTypeSessionBean
 */
@Stateless( mappedName = "ProductTypeSession", name = "ProductTypeSession" )
@LocalBean
public class ProductTypeSessionBean extends SimpleSessionBean<ProductType> implements ProductTypeSession, ProductTypeSessionLocal
{

	/**
	 * Default constructor.
	 */
	public ProductTypeSessionBean( )
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Class<ProductType> getEntityClass( )
	{
		return ProductType.class;
	}

}
