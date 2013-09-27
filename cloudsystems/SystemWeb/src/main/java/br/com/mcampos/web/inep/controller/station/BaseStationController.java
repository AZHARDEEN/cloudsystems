package br.com.mcampos.web.inep.controller.station;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.inep.StationSession;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepSubscription;
import br.com.mcampos.jpa.inep.InepSubscriptionPK;
import br.com.mcampos.web.core.BaseDBLoggedController;

public abstract class BaseStationController extends BaseDBLoggedController<StationSession>
{
	private static final long serialVersionUID = 7010049059485022961L;

	protected abstract boolean validate( );

	protected abstract void proceed( );

	protected abstract void cleanUp( );

	@Wire
	private Textbox subscription;

	@Wire
	private Div divData;

	private InepEvent currentEvent;

	private InepSubscription currentSubscription;

	@Listen( "onClick = #cmdSubmit" )
	public void onOk( Event evt )
	{
		if ( this.validate( ) == false ) {
			return;
		}
		Messagebox.show( "Confirma a avaliação do candidato?", "Confirmação", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, 0,
				new EventListener<Event>( )
				{
					@Override
					public void onEvent( Event evt )
					{
						if ( ( (Integer) evt.getData( ) ).equals( Messagebox.YES ) ) {
							BaseStationController.this.update( );
						}
					}
				} );

		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	private void update( )
	{
		this.proceed( );
		this.cleanUp( );
		this.subscription.setValue( "" );
		this.currentSubscription = null;
		this.divData.setVisible( false );
		this.subscription.setFocus( true );
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		this.cleanUp( );
		this.subscription.setFocus( true );
		if ( this.getCurrentEvent( ) == null ) {
			Messagebox.show( "Não há nenhum evento de correção ativo", "Evento", Messagebox.OK, Messagebox.EXCLAMATION );
		}

	}

	@Listen( "onOK=#subscription;onClick=#btnSearch" )
	public void lookupCandidate( Event evt )
	{
		if ( evt != null ) {
			evt.stopPropagation( );
		}
		if ( this.getSubscription( ) == null ) {
			Messagebox.show( "Este candidato não existe. Por favor verifique o número de inscrição correto", "Evento", Messagebox.OK,
					Messagebox.EXCLAMATION );
			this.divData.setVisible( false );
		}
		else {
			this.divData.setVisible( true );
		}
	}

	protected InepEvent getCurrentEvent( )
	{
		if ( this.currentEvent == null ) {
			this.currentEvent = this.getSession( ).getCurrentEvent( this.getPrincipal( ) );
		}
		return this.currentEvent;
	}

	@Override
	protected Class<StationSession> getSessionClass( )
	{
		return StationSession.class;
	}

	protected InepSubscription getSubscription( )
	{
		if ( this.currentSubscription == null ) {
			InepSubscriptionPK key = new InepSubscriptionPK( this.getCurrentEvent( ), this.subscription.getValue( ) );
			this.currentSubscription = this.getSession( ).getSubscription( this.getPrincipal( ), key );
		}
		return this.currentSubscription;
	}
}
