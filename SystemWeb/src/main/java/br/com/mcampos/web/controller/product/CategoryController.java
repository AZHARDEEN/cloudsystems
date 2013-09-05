package br.com.mcampos.web.controller.product;

import br.com.mcampos.ejb.product.CategorySession;
import br.com.mcampos.entity.product.Category;
import br.com.mcampos.web.core.listbox.BaseDBListController;

public class CategoryController extends BaseDBListController<CategorySession, Category>
{
	private static final long serialVersionUID = 982452702734832618L;

	@Override
	protected Class<CategorySession> getSessionClass( )
	{
		return CategorySession.class;
	}

	@Override
	protected void showFields( Category targetEntity )
	{
	}

	@Override
	protected void updateTargetEntity( Category entity )
	{
	}

	@Override
	protected boolean validateEntity( Category entity, int operation )
	{
		return false;
	}

}
