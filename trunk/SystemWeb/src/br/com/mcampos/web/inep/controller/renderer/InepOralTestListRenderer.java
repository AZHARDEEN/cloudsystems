package br.com.mcampos.web.inep.controller.renderer;

import org.zkoss.zul.Listitem;

import br.com.mcampos.ejb.inep.entity.InepOralTest;
import br.com.mcampos.web.core.listbox.BaseListRenderer;

public class InepOralTestListRenderer extends BaseListRenderer<InepOralTest>
{

	@Override
	public void render( Listitem item, InepOralTest data, int index ) throws Exception
	{
		super.render( item, data, index );
		item.setContext( "listPopupOralTest" );
		addCell( item, data.getSubscription( ).getId( ).getId( ) );
		addCell( item, data.getStation( ) );
		addCell( item, data.getInterviewGrade( ).toString( ) );
		addCell( item, data.getObserverGrade( ).toString( ) );
		addCell( item, "Discrepância Oral" );
	}
}
