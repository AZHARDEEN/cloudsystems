package br.com.mcampos.web.core.combobox;

import java.util.List;

import br.com.mcampos.ejb.product.type.ProductTypeSession;
import br.com.mcampos.entity.product.ProductType;

public class ProductTypeCombobox extends ComboboxExt<ProductTypeSession, ProductType>
{
	private static final long serialVersionUID = -3302742836101747622L;

	@Override
	protected Class<ProductTypeSession> getSessionClass( )
	{
		return ProductTypeSession.class;
	}

	@Override
	protected void load( )
	{
		load( ( (List<ProductType>) getSession( ).getAll( getPrincipal( ) ) ), null, true );
	}

}
