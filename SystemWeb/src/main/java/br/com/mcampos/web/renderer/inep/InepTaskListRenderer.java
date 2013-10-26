package br.com.mcampos.web.renderer.inep;

import org.zkoss.zul.Listitem;

import br.com.mcampos.jpa.inep.InepTask;
import br.com.mcampos.web.core.listbox.BaseListRenderer;

public class InepTaskListRenderer extends BaseListRenderer<InepTask>
{
	@Override
	public void render( Listitem item, InepTask data, int index ) throws Exception
	{
		super.render( item, data, index );
		addCell( item, data.getId( ).getId( ).toString( ) );
		addCell( item, data.getDescription( ) );
	}

}
