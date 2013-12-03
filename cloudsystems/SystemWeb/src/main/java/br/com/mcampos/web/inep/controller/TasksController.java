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
		this.getListbox( ).setItemRenderer( new InepDistributionRenderer( ) );
		this.loadCombobox( );
		this.updateCounters( );
	}

	protected Listbox getListbox( )
	{
		return this.listbox;
	}

	protected List<Paging> getPaging( )
	{
		return this.pagings;
	}

	@Listen( "onSelect = listbox#listTable" )
	public void onSelect( Event evt )
	{
		@SuppressWarnings( "unchecked" )
		ListModelList<InepDistribution> model = ( (ListModelList<InepDistribution>) (Object) this.getListbox( ).getModel( ) );
		if ( this.getListbox( ) != null && this.getListbox( ).getSelectedItem( ) != null ) {
			InepDistribution d = null;
			for ( InepDistribution item : model.getSelection( ) ) {
				d = item;
				break;
			}
			if ( d != null ) {
				this.showFields( d );
				if ( d.getRevisor( ).isCoordenador( ) ) {
					EventQueues.lookup( coordinatorEvent, true ).publish(
							new CoordinatorEventChange( this.getSession( ).getOtherDistributions( d.getTest( ) ) ) );
				}
			}
		}
		this.updateCounters( );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	@Listen( "onSelect = #comboEvent" )
	public void onSelectPackage( Event evt )
	{
		List<InepDistribution> list = Collections.emptyList( );
		Comboitem item = this.comboEvent.getSelectedItem( );
		if ( item != null && this.getRevisor( ) != null ) {
			this.revisor = null;
			if ( this.getRevisor( ).isCoordenador( ) ) {
				this.setTestStatus( DistributionStatus.statusVariance );
			}
			list = this.getSession( ).getTests( this.getRevisor( ), this.getTestStatus( ) );
		}
		this.getListbox( ).setModel( new ListModelList<InepDistribution>( list ) );
		this.updateCounters( );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	protected void showFields( InepDistribution rev )
	{
		if ( rev != null ) {
			this.notas.setSelectedItem( null );
			this.showFrame( rev );
			if ( rev.getNota( ) != null ) {
				this.notas.setSelectedIndex( rev.getNota( ) );
			}
		}
		else {
			this.hideTasks( );
		}
		if ( this.isBlocked( rev ) ) {
			this.cmdInepSave.setVisible( false );
			for ( Radio r : this.options ) {
				r.setDisabled( true );
			}
		}
		else {
			this.cmdInepSave.setVisible( true );
			for ( Radio r : this.options ) {
				r.setDisabled( false );
			}
		}
	}

	@Listen( "onClick = #cmdInepSave" )
	public void onClickSubmit( Event evt )
	{
		int nIndex = this.notas.getSelectedIndex( );
		if ( nIndex >= 0 ) {
			Listitem item = this.getListbox( ).getSelectedItem( );
			if ( item != null ) {
				InepDistribution rev = (InepDistribution) this.getListbox( ).getSelectedItem( ).getValue( );
				if ( this.isBlocked( rev ) == false )
				{
					rev.setNota( this.notas.getSelectedIndex( ) );
					try {
						this.getSession( ).updateRevision( rev );
					}
					catch ( Exception e ) {
						Messagebox.show( e.getMessage( ), "Erro Atualizando inscrição " + rev.getId( ).getSubscriptionId( ), Messagebox.OK,
								Messagebox.ERROR );
					}
					this.showTasks( );
				}
			}
			this.updateCounters( );
		}
		@SuppressWarnings( "unchecked" )
		ListModelList<InepDistribution> model = ( (ListModelList<InepDistribution>) (Object) this.getListbox( ).getModel( ) );
		if ( model != null ) {
			model.removeAll( model.getSelection( ) );
		}
		this.cmdInepSave.setDisabled( false );
		this.cmdCancel.setDisabled( false );
		if ( model.getSize( ) > 0 ) {
			ArrayList<InepDistribution> sel = new ArrayList<InepDistribution>( 1 );
			sel.add( model.get( 0 ) );
			model.setSelection( sel );
			this.onSelect( null );
		}
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	private boolean isBlocked( InepDistribution test )
	{
		if ( test != null ) {
			Integer status = test.getStatus( ).getId( );
			if ( this.getRevisor( ).isCoordenador( ) ) {
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
		this.showTasks( );
		this.getListbox( ).clearSelection( );
		this.updateCounters( );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
		this.updateCounters( );
	}

	private void showTasks( )
	{
		this.showFields( null );
		this.divFrame.setVisible( false );
		this.divListbox.setVisible( true );
		if ( this.inepGrade != null ) {
			this.inepGrade.setVisible( false );
		}
		this.getListbox( ).setVisible( true );
	}

	private void hideTasks( )
	{
		this.divFrame.setVisible( true );
		this.divListbox.setVisible( false );
		if ( this.inepGrade != null ) {
			this.inepGrade.setVisible( true );
		}
	}

	private void showFrame( InepDistribution item )
	{
		if ( item == null ) {
			return;
		}
		this.hideTasks( );
		// String.format( "/img/pdf/%s-%d-4.pdf", item.getId(
		// ).getSubscriptionId( ), item.getId( ).getTaskId( ) );
		item.setStartDate( new Date( ) );
		byte[ ] obj = this.getSession( ).getMedia( item );
		if ( obj != null && obj.length > 0 ) {
			AMedia media = new AMedia( null, null, null, obj );
			this.framePdf.setContent( media );
		}
		else {
			this.framePdf.setContent( null );
		}

	}

	private void loadCombobox( )
	{
		List<InepEvent> events = this.getSession( ).getAvailableEvents( this.getPrincipal( ) );

		if ( SysUtils.isEmpty( this.getComboEvent( ).getItems( ) ) == false ) {
			this.getComboEvent( ).getItems( ).clear( );
		}
		for ( InepEvent e : events ) {
			Comboitem item = this.getComboEvent( ).appendItem( e.getDescription( ) );
			item.setValue( e );
		}
		if ( this.getComboEvent( ).getItemCount( ) > 0 ) {
			this.getComboEvent( ).setSelectedIndex( 0 );
			this.onSelectPackage( null );
		}
		if ( this.getComboEvent( ).getItemCount( ) == 1 ) {
			this.getComboEvent( ).setDisabled( true );
		}
	}

	public Combobox getComboEvent( )
	{
		return this.comboEvent;
	}

	public InepRevisor getRevisor( )
	{
		if ( this.revisor == null ) {
			this.revisor = this.getSession( ).getRevisor( (InepEvent) this.getComboEvent( ).getSelectedItem( ).getValue( ),
					this.getPrincipal( ) );
		}
		return this.revisor;
	}

	private void updateCounters( )
	{
		InepTaskCounters dto = this.getSession( ).getCounters( this.getRevisor( ) );
		if ( dto != null ) {
			int total = dto.getTasks( ) + dto.getRevised( ) + dto.getVariance( );
			double percent = 0;

			if ( total == 0 )
			{
				this.countAll.setLabel( "" + dto.getTasks( ) );
				this.countRevised.setLabel( "" + dto.getRevised( ) );
				this.countVariance.setLabel( "" + dto.getVariance( ) );
			}
			else
			{
				percent = ( ( (double) dto.getTasks( ) ) / ( (double) total ) * 100 );
				this.countAll.setLabel( String.format( "%04d de %04d - %06.2f%%", dto.getTasks( ), total, percent ) );
				percent = ( ( (double) dto.getRevised( ) ) / ( (double) total ) * 100 );
				this.countRevised.setLabel( String.format( "%04d de %04d - %06.2f%%", dto.getRevised( ), total, percent ) );
				percent = ( ( (double) dto.getVariance( ) ) / ( (double) total ) * 100 );
				this.countVariance.setLabel( String.format( "%04d de %04d - %06.2f%%", dto.getVariance( ), total, percent ) );
			}
		}
		else
		{
			this.countAll.setLabel( "" );
			this.countRevised.setLabel( "" );
			this.countVariance.setLabel( "" );
		}
	}

	@Listen( "onClick=toolbarbutton" )
	public void onCountersClick( MouseEvent evt )
	{
		if ( evt != null ) {
			if ( evt.getTarget( ).equals( this.countAll ) ) {
				this.setTestStatus( DistributionStatus.statusDistributed );
			}
			else if ( evt.getTarget( ).equals( this.countRevised ) ) {
				this.setTestStatus( DistributionStatus.statusRevised );
			}
			else {
				this.setTestStatus( DistributionStatus.statusVariance );
			}
			this.onSelectPackage( evt );
			evt.stopPropagation( );
		}
	}

	private Integer getTestStatus( )
	{
		return this.testStatus;
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
