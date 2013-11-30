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
		this.addCell( item, data.getSubscription( ).getId( ).getId( ) );
		this.addCell( item, data.getSubscription( ).getStation( ).getClient( ).getName( ) );
		this.addCell( item, data.getObserverGrade( ) );
		this.addCell( item, data.getInterviewGrade( ) );
		this.addCell( item, data.getFinalGrade( ) );
		double obsGrade, intrGrade;
		obsGrade = data.getObserverGrade( ).doubleValue( );
		intrGrade = data.getInterviewGrade( ).doubleValue( );

		if ( ( obsGrade < 2 && intrGrade >= 2 ) || ( obsGrade >= 2 && intrGrade < 2 ) ) {
			this.addCell( item, "Certifica e Não Certifica" );
		}
		else {
			this.addCell( item, "Discrepância >= 1,5" );
		}
	}
}
