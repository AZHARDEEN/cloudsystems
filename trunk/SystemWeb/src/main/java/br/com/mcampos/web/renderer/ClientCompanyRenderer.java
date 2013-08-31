package br.com.mcampos.web.renderer;

import org.zkoss.zul.Listitem;

import br.com.mcampos.entity.user.Client;
import br.com.mcampos.entity.user.UserDocument;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.listbox.BaseListRenderer;

public class ClientCompanyRenderer extends BaseListRenderer<Client>
{
	@Override
	public void render( Listitem item, Client data, int index ) throws Exception
	{
		super.render( item, data, index );

		addCell( item, data.getId( ).getSequence( ).toString( ) );

		UserDocument cpf = null;
		for ( UserDocument document : data.getClient( ).getDocuments( ) )
		{
			switch ( document.getType( ).getId( ) ) {
			case UserDocument.typeCNPJ:
				cpf = document;
				break;
			}
		}
		addCell( item, cpf != null ? cpf.getCode( ) : "" );
		addCell( item, data.getClient( ).getName( ) );
		addCell( item, SysUtils.formatDate( data.getFromDate( ) ) );
	}

}
