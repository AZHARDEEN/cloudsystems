package br.com.mcampos.web.core.combobox;

import br.com.mcampos.ejb.user.contact.ContactTypeSession;

public class ContactTypeComboboxController extends BaseDBCombobox<ContactTypeSession>
{
	private static final long serialVersionUID = 1L;

	@Override
	protected Class<ContactTypeSession> getSessionClass( )
	{
		// TODO Auto-generated method stub
		return ContactTypeSession.class;
	}

}
