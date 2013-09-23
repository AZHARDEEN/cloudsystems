package br.com.mcampos.web.controller.client.commom;

import org.zkoss.zk.ui.Component;

import br.com.mcampos.jpa.user.UserContact;
import br.com.mcampos.web.renderer.UserContactListRenderer;

public class ContactController extends BaseUserAttrListController<UserContact>
{
	private static final long serialVersionUID = 6544906635795366755L;

	private static final String dialogPath = "/private/client/commom/contact_dlg.zul";

	@Override
	protected String getDialogPath( )
	{
		return dialogPath;
	}

	@Override
	public void doAfterCompose( Component comp ) throws Exception
	{
		super.doAfterCompose( comp );
		getListbox( ).setItemRenderer( new UserContactListRenderer( ) );
	}
}
