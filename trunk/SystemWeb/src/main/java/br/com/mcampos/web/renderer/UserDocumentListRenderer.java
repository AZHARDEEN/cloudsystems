package br.com.mcampos.web.renderer;

import org.zkoss.zul.Listitem;

import br.com.mcampos.entity.user.UserDocument;
import br.com.mcampos.web.core.listbox.BaseListRenderer;

public class UserDocumentListRenderer extends BaseListRenderer<UserDocument>
{

	@Override
	public void render( Listitem item, UserDocument data, int index ) throws Exception
	{
		super.render( item, data, index );
		addCell( item, data.getType( ).getDescription( ) );
		addCell( item, data.getCode( ) );
		addCell( item, data.getAdditionalInfo( ) );
	}

}
