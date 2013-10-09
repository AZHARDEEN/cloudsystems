package br.com.mcampos.web.inep.controller.station;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Listen;

import br.com.mcampos.jpa.inep.InepSubscription;

public class ResetCandidateController extends BaseStationController
{
	private static final long serialVersionUID = -8676829584083092391L;

	@Override
	protected boolean validate( )
	{
		return true;
	}

	@Override
	protected void proceed( )
	{
	}

	@Listen( "onClick=#cmdReset" )
	public void onReset( Event evt )
	{
		if ( evt != null ) {
			evt.stopPropagation( );
		}
		InepSubscription s = this.getCurrentSubscription( );
		Messagebox.show( "Deseja reinicializar a situação do(a) examinando " + s.getPerson( ).getName( ) + "?", "Confirmação de Reinicialização",
				Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, 0,
				new EventListener<Event>( )
				{
					@Override
					public void onEvent( Event evt )
					{
						if ( ( (Integer) evt.getData( ) ).equals( Messagebox.YES ) ) {
							ResetCandidateController.this.reset( );
						}
					}
				} );
	}

	private void reset( )
	{
		this.getSession( ).reset( this.getPrincipal( ), this.getCurrentSubscription( ) );
		this.cleanUp( );
	}

	@Listen( "onClick=#cmdMissing" )
	public void onMissing( Event evt )
	{
		if ( evt != null ) {
			evt.stopPropagation( );
		}
		InepSubscription s = this.getCurrentSubscription( );
		Messagebox.show( "Confirma marcar o(a) examinando " + s.getPerson( ).getName( ) + " como ausente?", "Confirmação de Ausencia",
				Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, 0,
				new EventListener<Event>( )
				{
					@Override
					public void onEvent( Event evt )
					{
						if ( ( (Integer) evt.getData( ) ).equals( Messagebox.YES ) ) {
							ResetCandidateController.this.setMissing( );
						}
					}
				} );
	}

	private void setMissing( )
	{
		this.getSession( ).setMissing( this.getPrincipal( ), this.getCurrentSubscription( ) );
		this.cleanUp( );
	}

}
