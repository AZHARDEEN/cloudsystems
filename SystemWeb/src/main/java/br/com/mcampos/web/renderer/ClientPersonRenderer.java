package br.com.mcampos.web.renderer;

import org.zkoss.zul.Listitem;

import br.com.mcampos.jpa.user.Client;
import br.com.mcampos.jpa.user.UserDocument;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.listbox.BaseListRenderer;

public class ClientPersonRenderer extends BaseListRenderer<Client>
{

	@Override
	public void render( Listitem item, Client data, int index ) throws Exception
	{
		super.render( item, data, index );

		addCell( item, data.getId( ).getSequence( ).toString( ) );

		UserDocument cpf = null;
		UserDocument email = null;
		for ( UserDocument document : data.getClient( ).getDocuments( ) )
		{
			switch ( document.getType( ).getId( ) ) {
			case UserDocument.typeCPF:
				cpf = document;
				break;
			case UserDocument.typeEmail:
				email = document;
				break;
			}
		}
		addCell( item, cpf != null ? cpf.getCode( ) : "" );
		addCell( item, data.getClient( ).getName( ) );
		addCell( item, email != null ? email.getCode( ) : "" );
		addCell( item, SysUtils.formatDate( data.getFromDate( ) ) );
	}
}
