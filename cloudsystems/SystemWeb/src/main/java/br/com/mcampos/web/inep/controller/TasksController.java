package br.com.mcampos.web.inep.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.South;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import br.com.mcampos.dto.inep.InepTaskCounters;
import br.com.mcampos.ejb.inep.team.TeamSession;
import br.com.mcampos.jpa.inep.DistributionStatus;
import br.com.mcampos.jpa.inep.InepDistribution;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepRevisor;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseDBLoggedController;
import br.com.mcampos.web.inep.controller.event.CoordinatorEventChange;
import br.com.mcampos.web.renderer.inep.InepDistributionRenderer;

public class TasksController extends BaseDBLoggedController<TeamSession>
{
	private static final long serialVersionUID = -4229563648862167526L;
	public static final String coordinatorEvent = "coordinatorQueueEvent";
	@SuppressWarnings( "unused" )
	private static final Logger logger = LoggerFactory.getLogger( TasksController.class );

	@Wire( "listbox#listTable" )
	private Listbox listbox;

	@Wire( value = "paging" )
	private List<Paging> pagings;

	@Wire( "radiogroup#sv6" )
	private Radiogroup notas;

	@Wire( "radio" )
	private Radio[ ] options;

	@Wire
	private Button cmdInepSave;

	@Wire
	private Button cmdCancel;

	@Wire
	private Combobox comboEvent;

	@Wire
	private South inepGrade;

	@Wire( "#divListbox" )
	private Div divListbox;

	@Wire( "#divFrame" )
	private Div divFrame;

	@Wire( "#framePdf" )
	private Iframe framePdf;

	@Wire
	private Toolbarbutton countVariance;
	@Wire
	private Toolbarbutton countRevised;
	@Wire
	private Toolbarbutton countAll;

	private InepRevisor revisor;

	private Integer testStatus = DistributionStatus.statusDistributed;

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		getListbox( ).setItemRenderer( new InepDistributionRenderer( ) );
		loadCombobox( );
		updateCounters( );
	}

	protected Listbox getListbox( )
	{
		return listbox;
	}

	protected List<Paging> getPaging( )
	{
		return pagings;
	}

	@Listen( "onSelect = listbox#listTable" )
	public void onSelect( Event evt )
	{
		@SuppressWarnings( "unchecked" )
		ListModelList<InepDistribution> model = ( (ListModelList<InepDistribution>) (Object) getListbox( ).getModel( ) );
		if ( getListbox( ) != null && getListbox( ).getSelectedItem( ) != null ) {
			InepDistribution d = null;
			for ( InepDistribution item : model.getSelection( ) ) {
				d = item;
				break;
			}
			if ( d != null ) {
				showFields( d );
				if ( d.getRevisor( ).isCoordenador( ) ) {
					EventQueues.lookup( coordinatorEvent, true ).publish(
							new CoordinatorEventChange( this.getSession( ).getOtherDistributions( d.getTest( ) ) ) );
				}
			}
		}
		updateCounters( );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	@Listen( "onSelect = #comboEvent" )
	public void onSelectPackage( Event evt )
	{
		List<InepDistribution> list = Collections.emptyList( );
		Comboitem item = comboEvent.getSelectedItem( );
		if ( item != null && getRevisor( ) != null ) {
			revisor = null;
			if ( getRevisor( ).isCoordenador( ) ) {
				setTestStatus( DistributionStatus.statusVariance );
			}
			list = this.getSession( ).getTests( getRevisor( ), getTestStatus( ) );
		}
		getListbox( ).setModel( new ListModelList<InepDistribution>( list ) );
		updateCounters( );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	protected void showFields( InepDistribution rev )
	{
		if ( rev != null ) {
			notas.setSelectedItem( null );
			showFrame( rev );
			if ( rev.getNota( ) != null ) {
				notas.setSelectedIndex( rev.getNota( ) );
			}
		}
		else {
			hideTasks( );
		}
		if ( isBlocked( rev ) ) {
			cmdInepSave.setVisible( false );
			for ( Radio r : options ) {
				r.setDisabled( true );
			}
		}
		else {
			cmdInepSave.setVisible( true );
			for ( Radio r : options ) {
				r.setDisabled( false );
			}
		}
	}

	@Listen( "onClick = #cmdInepSave" )
	public void onClickSubmit( Event evt )
	{
		int nIndex = notas.getSelectedIndex( );
		if ( nIndex >= 0 ) {
			Listitem item = getListbox( ).getSelectedItem( );
			if ( item != null ) {
				InepDistribution rev = (InepDistribution) getListbox( ).getSelectedItem( ).getValue( );
				if ( isBlocked( rev ) == false )
				{
					rev.setNota( notas.getSelectedIndex( ) );
					try {
						this.getSession( ).updateRevision( rev );
					}
					catch ( Exception e ) {
						Messagebox.show( e.getMessage( ), "Erro Atualizando inscrição " + rev.getId( ).getSubscriptionId( ), Messagebox.OK,
								Messagebox.ERROR );
					}
					showTasks( );
				}
			}
			updateCounters( );
		}
		@SuppressWarnings( "unchecked" )
		ListModelList<InepDistribution> model = ( (ListModelList<InepDistribution>) (Object) getListbox( ).getModel( ) );
		if ( model != null ) {
			model.removeAll( model.getSelection( ) );
		}
		cmdInepSave.setDisabled( false );
		cmdCancel.setDisabled( false );
		if ( model.getSize( ) > 0 ) {
			ArrayList<InepDistribution> sel = new ArrayList<InepDistribution>( 1 );
			sel.add( model.get( 0 ) );
			model.setSelection( sel );
			onSelect( null );
		}
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	private boolean isBlocked( InepDistribution test )
	{
		if ( test != null ) {
			Integer status = test.getStatus( ).getId( );
			if ( getRevisor( ).isCoordenador( ) ) {
				return status.equals( DistributionStatus.statusVariance ) == false;
			}
			else {
				return ( status.equals( DistributionStatus.statusDistributed ) == false );
			}
		}
		return true;
	}

	@Listen( "onClick = #cmdCancel" )
	public void onClickCancel( Event evt )
	{
		showTasks( );
		getListbox( ).clearSelection( );
		updateCounters( );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
		updateCounters( );
	}

	private void showTasks( )
	{
		showFields( null );
		divFrame.setVisible( false );
		divListbox.setVisible( true );
		if ( inepGrade != null ) {
			inepGrade.setVisible( false );
		}
		getListbox( ).setVisible( true );
	}

	private void hideTasks( )
	{
		divFrame.setVisible( true );
		divListbox.setVisible( false );
		if ( inepGrade != null ) {
			inepGrade.setVisible( true );
		}
	}

	private void showFrame( InepDistribution item )
	{
		if ( item == null ) {
			return;
		}
		hideTasks( );
		// String.format( "/img/pdf/%s-%d-4.pdf", item.getId(
		// ).getSubscriptionId( ), item.getId( ).getTaskId( ) );
		logger.info( "Getting image for subscription " + item.getTest( ).getSubscription( ).getId( ).getId( ) + " - " + item.getTest( ).getId( ).getTaskId( ) );
		item.setStartDate( new Date( ) );
		byte[ ] obj = this.getSession( ).getMedia( item );
		if ( obj != null && obj.length > 0 ) {
			logger.info( "Media for " + item.getTest( ).getSubscription( ).getId( ).getId( ) + " - " + item.getTest( ).getId( ).getTaskId( ) + " has "
					+ obj.length + " bytes " );
			AMedia media = new AMedia( item.getTest( ).getSubscription( ).getId( ).getId( ), "pdf", "application/pdf", obj );
			framePdf.setContent( media );
		}
		else {
			logger.info( "No media for " + item.getTest( ).getSubscription( ).getId( ).getId( ) + " - " + item.getTest( ).getId( ).getTaskId( ) );
			framePdf.setContent( null );
		}

	}

	private void loadCombobox( )
	{
		List<InepEvent> events = this.getSession( ).getAvailableEvents( getPrincipal( ) );

		if ( SysUtils.isEmpty( getComboEvent( ).getItems( ) ) == false ) {
			getComboEvent( ).getItems( ).clear( );
		}
		for ( InepEvent e : events ) {
			Comboitem item = getComboEvent( ).appendItem( e.getDescription( ) );
			item.setValue( e );
		}
		if ( getComboEvent( ).getItemCount( ) > 0 ) {
			getComboEvent( ).setSelectedIndex( 0 );
			onSelectPackage( null );
		}
		if ( getComboEvent( ).getItemCount( ) == 1 ) {
			getComboEvent( ).setDisabled( true );
		}
	}

	public Combobox getComboEvent( )
	{
		return comboEvent;
	}

	public InepRevisor getRevisor( )
	{
		if ( revisor == null ) {
			revisor = this.getSession( ).getRevisor( (InepEvent) getComboEvent( ).getSelectedItem( ).getValue( ),
					getPrincipal( ) );
		}
		return revisor;
	}

	private void updateCounters( )
	{
		InepTaskCounters dto = this.getSession( ).getCounters( getRevisor( ) );
		if ( dto != null ) {
			int total = dto.getTasks( ) + dto.getRevised( ) + dto.getVariance( );
			double percent = 0;

			if ( total == 0 )
			{
				countAll.setLabel( "" + dto.getTasks( ) );
				countRevised.setLabel( "" + dto.getRevised( ) );
				countVariance.setLabel( "Discr." );
			}
			else
			{
				percent = ( ( (double) dto.getTasks( ) ) / ( (double) total ) * 100 );
				countAll.setLabel( String.format( "%04d de %04d - %06.2f%%", dto.getTasks( ), total, percent ) );
				percent = ( ( (double) dto.getRevised( ) ) / ( (double) total ) * 100 );
				countRevised.setLabel( String.format( "%04d de %04d - %06.2f%%", dto.getRevised( ), total, percent ) );
				percent = ( ( (double) dto.getVariance( ) ) / ( (double) total ) * 100 );
				countVariance.setLabel( "Discr." );
			}
		}
		else
		{
			countAll.setLabel( "" );
			countRevised.setLabel( "" );
			countVariance.setLabel( "" );
		}
	}

	@Listen( "onClick=toolbarbutton" )
	public void onCountersClick( MouseEvent evt )
	{
		if ( evt != null ) {
			if ( evt.getTarget( ).equals( countAll ) ) {
				setTestStatus( DistributionStatus.statusDistributed );
			}
			else if ( evt.getTarget( ).equals( countRevised ) ) {
				setTestStatus( DistributionStatus.statusRevised );
			}
			else {
				setTestStatus( DistributionStatus.statusVariance );
			}
			onSelectPackage( evt );
			evt.stopPropagation( );
		}
	}

	private Integer getTestStatus( )
	{
		return testStatus;
	}

	private void setTestStatus( Integer testStatus )
	{
		this.testStatus = testStatus;
	}

	@Override
	protected Class<TeamSession> getSessionClass( )
	{
		// TODO Auto-generated method stub
		return TeamSession.class;
	}
}
