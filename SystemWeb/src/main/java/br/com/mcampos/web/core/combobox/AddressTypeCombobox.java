package br.com.mcampos.web.core.combobox;

import java.util.List;

import br.com.mcampos.ejb.user.address.AddressType;
import br.com.mcampos.ejb.user.address.AddressTypeSession;

public class AddressTypeCombobox extends ComboboxExt<AddressTypeSession, AddressType>
{
	private static final long serialVersionUID = -7146347472512153160L;

	@Override
	protected Class<AddressTypeSession> getSessionClass( )
	{
		return AddressTypeSession.class;
	}

	@Override
	protected void load( )
	{
		load( (List<AddressType>) getSession( ).getAll( ), null, true );
	}

}
