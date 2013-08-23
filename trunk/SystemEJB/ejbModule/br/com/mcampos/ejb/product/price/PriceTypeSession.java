package br.com.mcampos.ejb.product.price;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.product.entity.PriceType;

@Remote
public interface PriceTypeSession extends BaseSessionInterface<PriceType>
{

}
