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
		this.addCell( item, data.getSubscription( ).getId( ).getId( ) );
		this.addCell( item, data.getSubscription( ).getStation( ).getClient( ).getName( ) );
		this.addCell( item, data.getObserverGrade( ) );
		this.addCell( item, data.getInterviewGrade( ) );
		this.addCell( item, data.getAgreementGrade( ) );
		this.addCell( item, data.getAgreement2Grade( ) );
		this.addCell( item, data.getFinalGrade( ) );
		this.addCell( item, data.getSubscription( ).getWrittenGrade( ) );
	}

}
