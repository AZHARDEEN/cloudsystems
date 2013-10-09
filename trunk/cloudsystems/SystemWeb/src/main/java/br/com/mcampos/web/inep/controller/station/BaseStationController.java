package br.com.mcampos.web.inep.controller.station;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.inep.StationSession;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepSubscription;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseDBLoggedController;
import br.com.mcampos.web.renderer.inep.SubscriptionItemRenderer;

public abstract class BaseStationController extends BaseDBLoggedController<StationSession>
{
	private static final long serialVersionUID = 7010049059485022961L;
	private static final Logger LOGGER = LoggerFactory.getLogger( BaseStationController.class );

	protected abstract boolean validate( );

	protected abstract void proceed( );

	@Wire
	private Bandbox subscription;
	@Wire( "#list" )
	private Listbox listBox;

	@Wire
	private Div divData;

	@Wire
	private Label candidate;
	@Wire
	private Label citizenship;
	@Wire
	private Label needs;

	private InepEvent currentEvent;

	@Listen( "onClick = #cmdSubmit" )
	public void onOk( Event evt )
	{
		if ( this.validate( ) == false ) {
			return;
		}
		InepSubscription s = this.getCurrentSubscription( );

		Messagebox.show( "Confirma a avaliação do(a) examinando " + s.getPerson( ).getName( ) + "?", "Confirmação", Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION, 0,
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
		this.divData.setVisible( false );
		this.subscription.setFocus( true );
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		this.cleanUp( );
		this.subscription.setFocus( true );
		this.listBox.setItemRenderer( new SubscriptionItemRenderer( ) );
		if ( this.getCurrentEvent( ) == null ) {
			Messagebox.show( "Não há nenhum evento de correção ativo", "Evento", Messagebox.OK, Messagebox.EXCLAMATION );
		}

	}

	@Listen( "onOK=#subscription;onClick=#btnSearch;onOpen=bandbox" )
	public void lookupCandidate( Event evt )
	{
		if ( evt != null ) {
			evt.stopPropagation( );
		}
		this.getSubscription( this.subscription.getValue( ) );
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

	protected void getSubscription( String part )
	{

		InepEvent event = this.getCurrentEvent( );
		if ( event == null || SysUtils.isEmpty( part ) ) {
			return;
		}
		if ( part.length( ) >= 3 ) {
			LOGGER.info( "Searching for Subscription: " + part );
			List<InepSubscription> list = this.getSession( ).getSubscriptions( this.getPrincipal( ), event, part );
			this.listBox.setModel( new ListModelList<InepSubscription>( list ) );
			if ( SysUtils.isEmpty( list ) ) {
				return;
			}
			this.subscription.open( );
		}
	}

	@Listen( "onSelect = #list" )
	public void onSelect( Event evt )
	{
		if ( evt != null ) {
			evt.stopPropagation( );
		}
		InepSubscription s = this.getCurrentSubscription( );
		if ( s != null ) {
			this.subscription.setValue( s.getId( ).getId( ) );
			this.subscription.close( );
			this.showInfo( s );
		}
	}

	protected InepSubscription getCurrentSubscription( )
	{
		Listitem item = this.listBox.getSelectedItem( );
		if ( item == null || item.getValue( ) == null ) {
			return null;
		}
		return item.getValue( );
	}

	private void showInfo( InepSubscription s )
	{
		this.divData.setVisible( s != null );
		this.candidate.setValue( "" );
		this.citizenship.setValue( "" );
		this.needs.setValue( "" );
		if ( s != null ) {
			this.candidate.setValue( s.getPerson( ).getName( ) );
			this.citizenship.setValue( s.getCitizenship( ) );
			this.needs.setValue( s.getSpecialNeeds( ) );
		}
	}

	protected void cleanUp( )
	{
		this.showInfo( null );
	}

}
