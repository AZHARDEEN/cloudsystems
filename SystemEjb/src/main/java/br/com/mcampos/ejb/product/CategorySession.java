package br.com.mcampos.ejb.product;

import javax.ejb.Remote;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.jpa.product.Category;

@Remote
public interface CategorySession extends BaseCrudSessionInterface<Category>
{

}
