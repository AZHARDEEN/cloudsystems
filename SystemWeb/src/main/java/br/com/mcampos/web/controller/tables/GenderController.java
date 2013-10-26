package br.com.mcampos.web.controller.tables;

import br.com.mcampos.ejb.user.person.gender.GenderSession;
import br.com.mcampos.jpa.user.Gender;
import br.com.mcampos.web.core.SimpleTableController;

public class GenderController extends SimpleTableController<GenderSession, Gender>
{
	private static final long serialVersionUID = 3883290733755877495L;

	public GenderController( )
	{
		super();
	}

	@Override
	protected Class<GenderSession> getSessionClass()
	{
		return GenderSession.class;
	}
}
