package br.com.mcampos.web.fdigital.renderer;

import org.zkoss.zul.Listitem;

import br.com.mcampos.ejb.fdigital.Pad;
import br.com.mcampos.web.core.listbox.BaseListRenderer;

public class PadListRenderer extends BaseListRenderer<Pad>
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.mcampos.web.core.listbox.BaseListRenderer#render(org.zkoss.zul
	 * .Listitem, java.lang.Object, int)
	 */
	@Override
	public void render( Listitem item, Pad data, int index ) throws Exception
	{
		super.render( item, data, index );
		addCell( item, data.getMedia( ).getName( ) );
	}

}
