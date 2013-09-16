package br.com.mcampos.web.renderer.fdigital;

import org.zkoss.zul.Listitem;

import br.com.mcampos.jpa.fdigital.FormMedia;
import br.com.mcampos.web.core.listbox.BaseListRenderer;

public class FormMediaListRenderer extends BaseListRenderer<FormMedia>
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.mcampos.web.core.listbox.BaseListRenderer#render(org.zkoss.zul
	 * .Listitem, java.lang.Object, int)
	 */
	@Override
	public void render( Listitem item, FormMedia data, int index ) throws Exception
	{
		super.render( item, data, index );
		addCell( item, data.getMedia( ).getName( ) );
		addCell( item, data.getMedia( ).getSize( ).toString( ) );
	}

}
