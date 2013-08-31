package br.com.mcampos.ejb.product.price;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import br.com.mcampos.ejb.core.SimpleSessionBean;
import br.com.mcampos.entity.product.PriceType;

/**
 * Session Bean implementation class PriceTypeSessionBean
 */
@Stateless( mappedName = "PriceTypeSession", name = "PriceTypeSession" )
@LocalBean
public class PriceTypeSessionBean extends SimpleSessionBean<PriceType> implements PriceTypeSession, PriceTypeSessionLocal
{
	@Override
	protected Class<PriceType> getEntityClass( )
	{
		return PriceType.class;
	}

}
