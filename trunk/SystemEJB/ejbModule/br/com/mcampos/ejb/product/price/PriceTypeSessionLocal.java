package br.com.mcampos.ejb.product.price;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.product.entity.PriceType;

@Local
public interface PriceTypeSessionLocal extends BaseSessionInterface<PriceType>
{

}
