package br.com.mcampos.web.core.listbox;

import java.util.Collection;

import br.com.mcampos.ejb.core.BaseCompanySessionBean;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.jpa.BaseCompanyEntity;

public abstract class BaseCompanyDBListController<BEAN extends BaseCompanySessionBean<BaseCompanyEntity>, ENTITY extends BaseCompanyEntity> extends BaseDBListController<BEAN, ENTITY>
{
	private static final long serialVersionUID = -7402479572538209385L;

	@Override
	protected Collection<ENTITY> getAll( int activePage )
	{
		return getPagingSession( ).getAll( getPrincipal( ), null, new DBPaging( activePage, getRows( ) ) );
	}
}
