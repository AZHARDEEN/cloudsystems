package br.com.mcampos.web.inep.controller.station;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public abstract class BaseStationController extends SelectorComposer<Window>
{
	private static final long serialVersionUID = 7010049059485022961L;

	protected abstract boolean validate( );

	protected abstract void proceed( );

	protected abstract void cleanUp( );

	@Wire
	private Textbox subscription;

	@Wire
	private Div divData;

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
		this.divData.setVisible( false );
		this.subscription.setFocus( true );
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		this.cleanUp( );
		this.subscription.setFocus( true );
	}

	@Listen( "onOK=#subscription;onClick=#btnSearch" )
	public void lookupCandidate( Event evt )
	{
		if ( evt != null ) {
			evt.stopPropagation( );
		}
		this.divData.setVisible( true );
	}

}
