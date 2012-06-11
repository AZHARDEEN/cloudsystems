package br.com.mcampos.web.controller.client;

import br.com.mcampos.ejb.user.company.CompanySession;

public class CompanyController extends UserController<CompanySession>
{
	private static final long serialVersionUID = -5957735242513634394L;

	@Override
	protected Class<CompanySession> getSessionClass( )
	{
		return CompanySession.class;
	}

}
