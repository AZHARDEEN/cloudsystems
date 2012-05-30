package br.com.mcampos.web.core.combobox;

import br.com.mcampos.ejb.user.person.gender.GenderSession;

public class GenderComboboxController extends BaseDBCombobox<GenderSession>
{
	private static final long serialVersionUID = -7337687101622339229L;

	@Override
	protected Class<GenderSession> getSessionClass( )
	{
		return GenderSession.class;
	}

}
