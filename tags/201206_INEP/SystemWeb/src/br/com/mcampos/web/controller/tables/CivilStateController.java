package br.com.mcampos.web.controller.tables;

import br.com.mcampos.ejb.user.person.civilstate.CivilState;
import br.com.mcampos.ejb.user.person.civilstate.CivilStateSession;
import br.com.mcampos.web.core.SimpleTableController;

public class CivilStateController extends SimpleTableController<CivilStateSession, CivilState>
{
	private static final long serialVersionUID = -8370680494133956267L;

	@Override
	protected Class<CivilStateSession> getSessionClass()
	{
		return CivilStateSession.class;
	}
}
