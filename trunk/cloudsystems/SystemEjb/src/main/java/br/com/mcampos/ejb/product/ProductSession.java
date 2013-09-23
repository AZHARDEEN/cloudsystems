package br.com.mcampos.ejb.product;

import javax.ejb.Remote;

import br.com.mcampos.dto.core.PrincipalDTO;
import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.product.Product;

@Remote
public interface ProductSession extends BaseCrudSessionInterface<Product>
{
	Product loadObjects( PrincipalDTO auth, Product product );
}
