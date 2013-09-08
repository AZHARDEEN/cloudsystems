package br.com.mcampos.ejb.product;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseCrudSessionInterface;
import br.com.mcampos.entity.product.Category;

@Local
public interface CategorySessionLocal extends BaseCrudSessionInterface<Category>
{

}
