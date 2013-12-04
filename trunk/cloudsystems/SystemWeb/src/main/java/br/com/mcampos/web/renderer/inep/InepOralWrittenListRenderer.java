package br.com.mcampos.web.renderer.inep;

import org.zkoss.zul.Listitem;

import br.com.mcampos.jpa.inep.InepOralTest;
import br.com.mcampos.web.core.listbox.BaseListRenderer;

public class InepOralWrittenListRenderer extends BaseListRenderer<InepOralTest>
{
	@Override
	public void render( Listitem item, InepOralTest data, int index ) throws Exception
	{
		super.render( item, data, index );
		item.setContext( "listPopupOralTest" );
		addCell( item, data.getSubscription( ).getId( ).getId( ) );
		addCell( item, data.getSubscription( ).getStation( ).getClient( ).getName( ) );
		addCell( item, data.getObserverGrade( ) );
		addCell( item, data.getInterviewGrade( ) );
		addCell( item, data.getAgreementGrade( ) );
		addCell( item, data.getAgreement2Grade( ) );
		addCell( item, data.getSubscription( ).getOralGrade( ) );
		addCell( item, data.getSubscription( ).getWrittenGrade( ) );
	}

}
