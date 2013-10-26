package br.com.mcampos.ejb.product.price;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.product.PriceType;

@Local
public interface PriceTypeSessionLocal extends BaseCrudSessionInterface<PriceType>
{

}
