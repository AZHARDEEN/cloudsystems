package br.com.mcampos.ejb.product;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.product.Category;

@Remote
public interface CategorySession extends BaseSessionInterface<Category>
{

}
