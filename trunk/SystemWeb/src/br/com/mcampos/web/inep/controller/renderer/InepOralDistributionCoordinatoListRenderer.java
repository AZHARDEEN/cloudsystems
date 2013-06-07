package br.com.mcampos.web.inep.controller.renderer;

import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import br.com.mcampos.ejb.inep.entity.InepOralDistribution;
import br.com.mcampos.web.core.listbox.BaseListRenderer;
import br.com.mcampos.web.inep.utils.InepOralTestEventListener;

public class InepOralDistributionCoordinatoListRenderer extends BaseListRenderer<InepOralDistribution>
{
	@Override
	public void render( Listitem item, InepOralDistribution data, int index ) throws Exception
	{
		super.render( item, data, index );
		item.setContext( "listPopupOralTest" );
		if ( data.getTest( ).getVarianceStatus( ).intValue( ) > 10 )
			addCell( item, data.getId( ).getSubscriptionId( ) + "***" );
		else
			addCell( item, data.getId( ).getSubscriptionId( ) );

		addCell( item, data.getTest( ).getStation( ) );
		addCell( item, data.getTest( ).getFinalGrade( ).toString( ) );
		addCell( item, data.getTest( ).getAgreementGrade( ).toString( ) );
		Listcell cell = new Listcell( );
		Button btnDownload = new Button( "Audio" );
		cell.appendChild( btnDownload );
		item.appendChild( cell );
		btnDownload.setAttribute( InepOralDistribution.class.getSimpleName( ), data );
		btnDownload.addEventListener( Events.ON_CLICK, new InepOralTestEventListener( ) );
	}

}
