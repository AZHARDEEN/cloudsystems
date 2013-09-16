package br.com.mcampos.ejb.product.type;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.product.ProductType;

@Remote
public interface ProductTypeSession extends BaseCrudSessionInterface<ProductType>
{

}
