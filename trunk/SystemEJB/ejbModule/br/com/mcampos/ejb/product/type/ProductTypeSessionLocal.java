package br.com.mcampos.ejb.product.type;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.product.ProductType;

@Local
public interface ProductTypeSessionLocal extends BaseCrudSessionInterface<ProductType>
{

}
