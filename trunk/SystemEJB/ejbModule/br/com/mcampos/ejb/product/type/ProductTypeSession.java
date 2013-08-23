package br.com.mcampos.ejb.product.type;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.product.entity.ProductType;

@Remote
public interface ProductTypeSession extends BaseSessionInterface<ProductType>
{

}
