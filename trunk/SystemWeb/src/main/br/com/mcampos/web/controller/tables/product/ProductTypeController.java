package br.com.mcampos.web.controller.tables.product;

import br.com.mcampos.ejb.product.entity.ProductType;
import br.com.mcampos.ejb.product.type.ProductTypeSession;
import br.com.mcampos.web.core.SimpleTableController;

public class ProductTypeController extends SimpleTableController<ProductTypeSession, ProductType>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5552946990225056850L;

	@Override
	protected Class<ProductTypeSession> getSessionClass( )
	{
		return ProductTypeSession.class;
	}

}
