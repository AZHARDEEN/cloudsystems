package br.com.mcampos.web.inep.controller.renderer;

import org.zkoss.zul.Listitem;

import br.com.mcampos.ejb.inep.packs.InepPackage;
import br.com.mcampos.web.core.listbox.BaseListRenderer;

public class EventListRenderer extends BaseListRenderer<InepPackage>
{

	@Override
	public void render( Listitem item, InepPackage data, int index ) throws Exception
	{
		super.render( item, data, index );
		addCell( item, data.getId( ).getId( ).toString( ) );
		addCell( item, data.getDescription( ) );
	}

}
