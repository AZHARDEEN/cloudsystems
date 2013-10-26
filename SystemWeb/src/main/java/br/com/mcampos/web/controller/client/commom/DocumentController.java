package br.com.mcampos.web.controller.client.commom;

import org.zkoss.zk.ui.Component;

import br.com.mcampos.jpa.user.UserDocument;
import br.com.mcampos.web.renderer.UserDocumentListRenderer;

public class DocumentController extends BaseUserAttrListController<UserDocument>
{
	private static final long serialVersionUID = -3999386922233720288L;

	private static final String dialogPath = "/private/client/commom/document_dlg.zul";

	@Override
	protected String getDialogPath( )
	{
		return dialogPath;
	}

	@Override
	public void doAfterCompose( Component comp ) throws Exception
	{
		super.doAfterCompose( comp );
		getListbox( ).setItemRenderer( new UserDocumentListRenderer( ) );
	}
}
