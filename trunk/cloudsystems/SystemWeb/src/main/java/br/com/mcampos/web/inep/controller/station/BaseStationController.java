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

import br.com.mcampos.dto.inep.StationGradeDTO;
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

	protected abstract void showGradeIfexists( StationGradeDTO grade );

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

	@Wire
	private Label lblTitle;

	private InepEvent currentEvent;

	@Listen( "onClick = #cmdSubmit" )
	public void onOk( Event evt )
	{
		if ( validate( ) == false ) {
			return;
		}
		InepSubscription s = getCurrentSubscription( );

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

	@Listen( "onClick = #cmdCancel" )
	public void onCancel( )
	{
		cleanUp( );
		subscription.setValue( "" );
		if( divData != null ) {
			divData.setVisible( false );
		}
		subscription.setFocus( true );
	}

	private void update( )
	{
		proceed( );
		onCancel( );
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		cleanUp( );
		subscription.setFocus( true );
		listBox.setItemRenderer( new SubscriptionItemRenderer( ) );
		if ( getCurrentEvent( ) == null ) {
			Messagebox.show( "Não há nenhum evento de correção ativo", "Evento", Messagebox.OK, Messagebox.EXCLAMATION );
		}
		lblTitle.setValue( getCurrentEvent( ).getDescription( ) );

	}

	@Listen( "onOK=#subscription;onClick=#btnSearch;onOpen=bandbox" )
	public void lookupCandidate( Event evt )
	{
		if ( evt != null ) {
			evt.stopPropagation( );
		}
		getSubscription( subscription.getValue( ) );
	}

	protected InepEvent getCurrentEvent( )
	{
		if ( currentEvent == null ) {
			currentEvent = this.getSession( ).getCurrentEvent( getPrincipal( ) );
		}
		return currentEvent;
	}

	@Override
	protected Class<StationSession> getSessionClass( )
	{
		return StationSession.class;
	}

	protected void getSubscription( String part )
	{

		InepEvent event = getCurrentEvent( );
		if ( event == null || SysUtils.isEmpty( part ) ) {
			return;
		}
		if ( part.length( ) >= 3 ) {
			LOGGER.info( "Searching for Subscription: " + part );
			List<InepSubscription> list = this.getSession( ).getSubscriptions( getPrincipal( ), event, part );
			listBox.setModel( new ListModelList<InepSubscription>( list ) );
			if ( SysUtils.isEmpty( list ) ) {
				return;
			}
			subscription.open( );
		}
	}

	@Listen( "onSelect = #list" )
	public void onSelect( Event evt )
	{
		if ( evt != null ) {
			evt.stopPropagation( );
		}
		InepSubscription s = getCurrentSubscription( );
		if ( s != null ) {
			subscription.setValue( s.getId( ).getId( ) );
			subscription.close( );
			showInfo( s );
		}
	}

	protected InepSubscription getCurrentSubscription( )
	{
		Listitem item = listBox.getSelectedItem( );
		if ( item == null || item.getValue( ) == null ) {
			return null;
		}
		return item.getValue( );
	}

	protected void showInfo( InepSubscription s )
	{
		if( divData != null ) {
			divData.setVisible( s != null );
		}
		candidate.setValue( "" );
		citizenship.setValue( "" );
		needs.setValue( "" );
		if ( s != null ) {
			candidate.setValue( s.getPerson( ).getName( ) );
			citizenship.setValue( s.getCitizenship( ) );
			needs.setValue( s.getSpecialNeeds( ) );
			StationGradeDTO grade = this.getSession( ).getStationGrade( getPrincipal( ), s );
			if ( grade != null ) {
				showGradeIfexists( grade );
			}
		}
	}

	protected void cleanUp( )
	{
		showInfo( null );
	}

}
