package br.com.mcampos.web.renderer.inep;

import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import br.com.mcampos.jpa.inep.InepOralDistribution;
import br.com.mcampos.web.core.listbox.BaseListRenderer;
import br.com.mcampos.web.inep.utils.InepOralTestEventListener;

public class InepOralDistributionCoordinatoListRenderer extends BaseListRenderer<InepOralDistribution>
{
	@Override
	public void render( Listitem item, InepOralDistribution data, int index ) throws Exception
	{
		super.render( item, data, index );
		item.setContext( "listPopupOralTest" );
		if ( data.getTest( ).getVarianceStatus( ).intValue( ) > 10 ) {
			this.addCell( item, data.getId( ).getSubscriptionId( ) + "***" );
		}
		else {
			this.addCell( item, data.getId( ).getSubscriptionId( ) );
		}

		this.addCell( item, data.getTest( ).getSubscription( ).getStation( ).getClient( ).getName( ) );
		this.addCell( item, data.getTest( ).getObserverGrade( ) );
		this.addCell( item, data.getTest( ).getInterviewGrade( ) );
		this.addCell( item, data.getTest( ).getFinalGrade( ) );
		this.addCell( item, data.getTest( ).getAgreementGrade( ) );
		this.addCell( item, data.getTest( ).getAgreement2Grade( ) );
		this.addCell( item, data.getTest( ).getSubscription( ).getOralGrade( ) );
		this.addCell( item, data.getTest( ).getSubscription( ).getWrittenGrade( ) );
		Listcell cell = new Listcell( );
		Button btnDownload = new Button( "Audio" );
		cell.appendChild( btnDownload );
		item.appendChild( cell );
		btnDownload.setAttribute( InepOralDistribution.class.getSimpleName( ), data );
		btnDownload.addEventListener( Events.ON_CLICK, new InepOralTestEventListener( ) );
	}

}
