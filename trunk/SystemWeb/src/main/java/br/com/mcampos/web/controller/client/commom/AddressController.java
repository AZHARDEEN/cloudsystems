package br.com.mcampos.web.controller.client.commom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;

import br.com.mcampos.entity.user.Address;
import br.com.mcampos.web.renderer.AddressListRenderer;

public class AddressController extends BaseUserAttrListController<Address>
{
	private static final long serialVersionUID = -3981301497156139554L;

	@SuppressWarnings( "unused" )
	private static final Logger logger = LoggerFactory.getLogger( AddressController.class );

	private static final String dialogPath = "/private/client/commom/address_dlg.zul";

	@Override
	public void doAfterCompose( Component comp ) throws Exception
	{
		super.doAfterCompose( comp );
		getListbox( ).setItemRenderer( new AddressListRenderer( ) );
	}

	@Override
	protected String getDialogPath( )
	{
		return dialogPath;
	}
}
