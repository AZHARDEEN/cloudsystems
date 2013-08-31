package br.com.mcampos.web.controller.tables.product;

import br.com.mcampos.ejb.product.price.PriceTypeSession;
import br.com.mcampos.entity.product.PriceType;
import br.com.mcampos.web.core.SimpleTableController;

public class PriceTypeController extends SimpleTableController<PriceTypeSession, PriceType>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5616975937312500523L;

	@Override
	protected Class<PriceTypeSession> getSessionClass( )
	{
		// TODO Auto-generated method stub
		return PriceTypeSession.class;
	}

}
