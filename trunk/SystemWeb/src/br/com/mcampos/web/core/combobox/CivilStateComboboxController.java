package br.com.mcampos.web.core.combobox;

import br.com.mcampos.ejb.user.person.civilstate.CivilStateSession;

public class CivilStateComboboxController extends BaseDBCombobox<CivilStateSession>
{
	private static final long serialVersionUID = 4580521611405756294L;

	@Override
	protected Class<CivilStateSession> getSessionClass( )
	{
		return CivilStateSession.class;
	}

}
