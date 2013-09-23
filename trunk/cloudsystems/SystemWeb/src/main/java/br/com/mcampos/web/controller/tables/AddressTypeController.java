package br.com.mcampos.web.controller.tables;

import br.com.mcampos.ejb.user.address.AddressTypeSession;
import br.com.mcampos.jpa.user.AddressType;
import br.com.mcampos.web.core.SimpleTableController;

public class AddressTypeController extends SimpleTableController<AddressTypeSession, AddressType>
{
	private static final long serialVersionUID = -3645436748290619024L;

	@Override
	protected Class<AddressTypeSession> getSessionClass( )
	{
		return AddressTypeSession.class;
	}

}
