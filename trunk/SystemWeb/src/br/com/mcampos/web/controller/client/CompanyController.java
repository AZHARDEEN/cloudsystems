package br.com.mcampos.web.controller.client;

import br.com.mcampos.ejb.user.company.Company;
import br.com.mcampos.ejb.user.company.CompanySession;
import br.com.mcampos.web.core.listbox.ReadOnlyListboxController;

public class CompanyController extends ReadOnlyListboxController<CompanySession, Company>
{
	private static final long serialVersionUID = -5957735242513634394L;

	@Override
	protected Class<CompanySession> getSessionClass( )
	{
		return CompanySession.class;
	}

}
