package br.com.mcampos.ejb.product.price;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.product.PriceType;

@Remote
public interface PriceTypeSession extends BaseSessionInterface<PriceType>
{

}
