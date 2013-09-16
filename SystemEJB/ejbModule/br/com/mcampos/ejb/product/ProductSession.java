package br.com.mcampos.ejb.product;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.product.Product;
import br.com.mcampos.sysutils.dto.PrincipalDTO;

@Remote
public interface ProductSession extends BaseCrudSessionInterface<Product>
{
	Product loadObjects( PrincipalDTO auth, Product product );
}
