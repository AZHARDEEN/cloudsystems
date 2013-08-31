package br.com.mcampos.web.renderer.inep;

import org.zkoss.zul.Listitem;

import br.com.mcampos.entity.inep.InepPackage;
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
