package br.com.mcampos.web.core.combobox;

import java.util.List;

import br.com.mcampos.ejb.user.contact.ContactTypeSession;
import br.com.mcampos.entity.user.ContactType;

public class ContactTypeCombobox extends ComboboxExt<ContactTypeSession, ContactType>
{
	private static final long serialVersionUID = -1473353298602882180L;

	@Override
	protected Class<ContactTypeSession> getSessionClass( )
	{
		return ContactTypeSession.class;
	}

	@Override
	protected void load( )
	{
		load( (List<ContactType>) getSession( ).getAll( ), null, true );

	}

}
