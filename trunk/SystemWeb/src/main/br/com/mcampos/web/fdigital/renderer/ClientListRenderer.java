package br.com.mcampos.web.fdigital.renderer;

import org.zkoss.zul.Listitem;

import br.com.mcampos.ejb.fdigital.form.user.AnotoFormUser;
import br.com.mcampos.web.core.listbox.BaseListRenderer;

public class ClientListRenderer extends BaseListRenderer<AnotoFormUser>
{

	@Override
	public void render( Listitem item, AnotoFormUser data, int index ) throws Exception
	{
		super.render( item, data, index );
		addCell( item, data.getClient( ).getId( ).toString( ) );
		addCell( item, data.getClient( ).getName( ) );
	}

}
