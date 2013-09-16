package br.com.mcampos.ejb.product.price;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.product.PriceType;

@Remote
public interface PriceTypeSession extends BaseCrudSessionInterface<PriceType>
{

}
