package br.com.mcampos.ejb.product;

import javax.ejb.Local;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.entity.product.Category;

@Local
public interface CategorySessionLocal extends BaseSessionInterface<Category>
{

}
