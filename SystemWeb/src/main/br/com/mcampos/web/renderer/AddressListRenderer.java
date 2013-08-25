package br.com.mcampos.web.renderer;

import org.zkoss.zul.Listitem;

import br.com.mcampos.ejb.user.address.Address;
import br.com.mcampos.web.core.listbox.BaseListRenderer;

public class AddressListRenderer extends BaseListRenderer<Address>
{

	@Override
	public void render( Listitem item, Address data, int index ) throws Exception
	{
		super.render( item, data, index );
		addCell( item, data.getType( ).getDescription( ) );
		addCell( item, data.getAddress( ) );
		addCell( item, data.getCity( ).getDescription( ) );
		addCell( item, data.getCity( ).getState( ).getAbbreviation( ) );

	}

}
