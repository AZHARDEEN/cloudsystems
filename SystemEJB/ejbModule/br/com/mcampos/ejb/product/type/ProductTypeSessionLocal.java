package br.com.mcampos.ejb.product.type;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.product.entity.ProductType;

@Local
public interface ProductTypeSessionLocal extends BaseSessionInterface<ProductType>
{

}
