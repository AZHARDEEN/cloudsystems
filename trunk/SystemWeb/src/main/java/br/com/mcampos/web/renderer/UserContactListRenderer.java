package br.com.mcampos.web.renderer;

import org.zkoss.zul.Listitem;

import br.com.mcampos.ejb.user.UserContact;
import br.com.mcampos.web.core.listbox.BaseListRenderer;

public class UserContactListRenderer extends BaseListRenderer<UserContact>
{

	@Override
	public void render( Listitem item, UserContact data, int index ) throws Exception
	{
		super.render( item, data, index );
		addCell( item, data.getType( ).getDescription( ) );
		addCell( item, data.getDescription( ) );
		addCell( item, data.getObs( ) );
	}
}
