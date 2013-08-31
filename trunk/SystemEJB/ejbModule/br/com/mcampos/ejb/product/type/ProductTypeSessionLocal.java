package br.com.mcampos.ejb.product.type;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.product.ProductType;

@Local
public interface ProductTypeSessionLocal extends BaseSessionInterface<ProductType>
{

}
