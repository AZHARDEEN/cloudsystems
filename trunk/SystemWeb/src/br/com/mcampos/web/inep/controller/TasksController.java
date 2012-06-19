package br.com.mcampos.web.inep.controller;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.South;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import br.com.mcampos.dto.inep.InepTaskCounters;
import br.com.mcampos.ejb.inep.entity.DistributionStatus;
import br.com.mcampos.ejb.inep.entity.InepDistribution;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepRevisor;
import br.com.mcampos.ejb.inep.team.TeamSession;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseDBLoggedController;
import br.com.mcampos.web.inep.controller.event.CoordinatorEventChange;
import br.com.mcampos.web.inep.controller.renderer.InepDistributionRenderer;

public class TasksController extends BaseDBLoggedController<TeamSession>
{
	private static final long serialVersionUID = -4229563648862167526L;
	public static final String coordinatorEvent = "coordinatorQueueEvent";
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
	private Toolbarbutton cmdInepSave;

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
		logger.info( "doAfterCompose - Listbox: " + getListbox( ).getItemCount( ) );
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
		if ( getListbox( ) != null && getListbox( ).getSelectedItem( ) != null ) {
			InepDistribution d = (InepDistribution) getListbox( ).getSelectedItem( ).getValue( );
			showFields( d );
			if ( d.getRevisor( ).isCoordenador( ) ) {
				EventQueues.lookup( coordinatorEvent, true ).publish(
						new CoordinatorEventChange( getSession( ).getOtherDistributions( d.getTest( ) ) ) );
			}
		}
		if ( evt != null ) {
			evt.stopPropagation( );
		}
		logger.info( "onSelect - Listbox: " + getListbox( ).getItemCount( ) );
	}

	@Listen( "onSelect = #comboEvent" )
	public void onSelectPackage( Event evt )
	{
		List<InepDistribution> list = Collections.emptyList( );
		Comboitem item = this.comboEvent.getSelectedItem( );
		if ( item != null && getRevisor( ) != null ) {
			if ( getRevisor( ).isCoordenador( ) ) {
				setTestStatus( DistributionStatus.statusVariance );
			}
			list = getSession( ).getTests( getRevisor( ), getTestStatus( ) );
		}
		getListbox( ).setModel( new ListModelList<InepDistribution>( list ) );
		updateCounters( );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
		logger.info( "onSelectPackage - Listbox: " + getListbox( ).getItemCount( ) );
	}

	protected void showFields( InepDistribution rev )
	{
		if ( rev != null ) {
			this.notas.setSelectedItem( null );
			showFrame( );
			if ( rev.getNota( ) != null ) {
				this.notas.setSelectedIndex( rev.getNota( ) );
			}
		}
		else {
			hideTasks( );
		}
		if ( isBlocked( rev ) ) {
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
		logger.info( "showFields - Listbox: " + getListbox( ).getItemCount( ) );
	}

	@Listen( "onClick = #cmdObs" )
	public void onClickComments( Event evt )
	{
		logger.info( "onClickComments - before - Listbox: " + getListbox( ).getItemCount( ) );
		Listitem item = getListbox( ).getSelectedItem( );
		if ( item != null ) {
			InepDistribution rev = (InepDistribution) getListbox( ).getSelectedItem( ).getValue( );
			Component comp = Executions.createComponents( "/private/inep/dlg_comment.zul", getMainWindow( ), null );
			if ( comp instanceof DlgComment ) {
				DlgComment dlg = ( (DlgComment) comp );

				dlg.setDistribution( rev );
				dlg.doModal( );
			}
		}
		if ( evt != null ) {
			evt.stopPropagation( );
		}
		logger.info( "onClickComments - Listbox: " + getListbox( ).getItemCount( ) );
	}

	@Listen( "onClick = #cmdInepSave" )
	public void onClickSubmit( Event evt )
	{
		int nIndex = this.notas.getSelectedIndex( );
		if ( nIndex < 0 ) {
			return;
		}
		Listitem item = getListbox( ).getSelectedItem( );
		if ( item != null ) {
			InepDistribution rev = (InepDistribution) getListbox( ).getSelectedItem( ).getValue( );
			if ( isBlocked( rev ) == false )
			{
				rev.setNota( this.notas.getSelectedIndex( ) );
				getSession( ).updateRevision( rev );
				showTasks( );
				this.listbox.removeItemAt( this.listbox.getSelectedIndex( ) );
				getListbox( ).clearSelection( );
			}
		}
		updateCounters( );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
		logger.info( "onClickSubmit - Listbox: " + getListbox( ).getItemCount( ) );
	}

	private boolean isBlocked( InepDistribution test )
	{
		if ( test != null ) {
			Integer status = test.getStatus( ).getId( );
			if ( getRevisor( ).isCoordenador( ) ) {
				logger.info( "isBlocked 1 - Listbox: " + getListbox( ).getItemCount( ) );
				return status.equals( DistributionStatus.statusVariance ) == false;
			}
			else {
				logger.info( "isBlocked 2 - Listbox: " + getListbox( ).getItemCount( ) );
				return ( status.equals( DistributionStatus.statusDistributed ) == false );
			}
		}
		logger.info( "isBlocked 3 - Listbox: " + getListbox( ).getItemCount( ) );
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
		logger.info( "onClickCancel - Listbox: " + getListbox( ).getItemCount( ) );
	}

	private void showTasks( )
	{
		showFields( null );
		this.divFrame.setVisible( false );
		this.divListbox.setVisible( true );
		if ( this.inepGrade != null ) {
			this.inepGrade.setVisible( false );
		}
		getListbox( ).setVisible( true );
		logger.info( "showTasks - Listbox: " + getListbox( ).getItemCount( ) );
	}

	private void hideTasks( )
	{
		this.divFrame.setVisible( true );
		this.divListbox.setVisible( false );
		if ( this.inepGrade != null ) {
			this.inepGrade.setVisible( true );
		}
		logger.info( "hideTasks - Listbox: " + getListbox( ).getItemCount( ) );
	}

	private void showFrame( )
	{
		InepDistribution item = (InepDistribution) this.listbox.getSelectedItem( ).getValue( );
		hideTasks( );
		// String.format( "/img/pdf/%s-%d-4.pdf", item.getId(
		// ).getSubscriptionId( ), item.getId( ).getTaskId( ) );
		AMedia media = new AMedia( null, null, null, getSession( ).getMedia( item.getTest( ) ) );
		this.framePdf.setContent( media );
		logger.info( "showFrame - Listbox: " + getListbox( ).getItemCount( ) );
	}

	private void loadCombobox( )
	{
		List<InepPackage> events = getSession( ).getEvents( getCurrentCollaborator( ) );

		if ( SysUtils.isEmpty( getComboEvent( ).getItems( ) ) == false ) {
			getComboEvent( ).getItems( ).clear( );
		}
		for ( InepPackage e : events ) {
			Comboitem item = getComboEvent( ).appendItem( e.getDescription( ) );
			item.setValue( e );
		}
		if ( getComboEvent( ).getItemCount( ) > 0 ) {
			getComboEvent( ).setSelectedIndex( 0 );
			onSelectPackage( null );
		}
		logger.info( "loadCombobox - Listbox: " + getListbox( ).getItemCount( ) );
	}

	public Combobox getComboEvent( )
	{
		return this.comboEvent;
	}

	public InepRevisor getRevisor( )
	{
		if ( this.revisor == null ) {
			this.revisor = getSession( ).getRevisor( (InepPackage) getComboEvent( ).getSelectedItem( ).getValue( ),
					getCurrentCollaborator( ) );
		}
		return this.revisor;
	}

	private void updateCounters( )
	{
		InepTaskCounters dto = getSession( ).getCounters( getRevisor( ) );
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
		logger.info( "updateCounters - Listbox: " + getListbox( ).getItemCount( ) );
	}

	@Listen( "onClick=toolbarbutton" )
	public void onCountersClick( MouseEvent evt )
	{
		if ( evt != null ) {
			if ( evt.getTarget( ).equals( this.countAll ) ) {
				setTestStatus( DistributionStatus.statusDistributed );
			}
			else if ( evt.getTarget( ).equals( this.countRevised ) ) {
				setTestStatus( DistributionStatus.statusRevised );
			}
			else {
				setTestStatus( DistributionStatus.statusVariance );
			}
			onSelectPackage( evt );
			evt.stopPropagation( );
		}
		logger.info( "onCountersClick - Listbox: " + getListbox( ).getItemCount( ) );
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
