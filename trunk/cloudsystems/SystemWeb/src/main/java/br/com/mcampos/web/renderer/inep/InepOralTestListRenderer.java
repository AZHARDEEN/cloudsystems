package br.com.mcampos.web.renderer.inep;

import org.zkoss.zul.Listitem;

import br.com.mcampos.jpa.inep.InepOralTest;
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
		addCell( item, data.getSubscription( ).getOralGrade( ) );
		addCell( item, data.getSubscription( ).getWrittenGrade( ) );
		addCell( item, "Discrepância Oral" );
	}
}
