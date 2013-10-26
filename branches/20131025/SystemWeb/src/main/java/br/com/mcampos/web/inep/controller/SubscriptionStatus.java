package br.com.mcampos.web.inep.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.event.OpenEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.inep.team.TeamSession;
import br.com.mcampos.jpa.inep.InepDistribution;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepOralDistribution;
import br.com.mcampos.jpa.inep.InepOralTest;
import br.com.mcampos.jpa.inep.InepRevisor;
import br.com.mcampos.jpa.inep.InepSubscription;
import br.com.mcampos.jpa.inep.InepTask;
import br.com.mcampos.jpa.inep.InepTest;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseDBLoggedController;
import br.com.mcampos.web.inep.utils.DistributionInfoListRenderer;
import br.com.mcampos.web.renderer.inep.InepStatusOralDistributionsListRenderer;
import br.com.mcampos.web.renderer.inep.InepTaskListRenderer;
import br.com.mcampos.web.renderer.inep.SubscriptionItemRenderer;

public class SubscriptionStatus extends BaseDBLoggedController<TeamSession>
{
	private static final long serialVersionUID = 4073500689607424974L;
	private static final Logger LOGGER = LoggerFactory.getLogger( SubscriptionStatus.class );

	private InepRevisor revisor;

	@Wire
	private Combobox comboEvent;

	@Wire
	private Bandbox subscription;

	@Wire( "#list" )
	private Listbox listBox;

	@Wire
	private Listbox listDetail;

	@Wire( "#framePdf" )
	private Iframe framePdf;

	@Wire
	Label task1;
	@Wire
	Label task2;
	@Wire
	Label task3;
	@Wire
	Label task4;
	@Wire
	Label station;
	@Wire
	Label interviewerGrade;
	@Wire
	Label observerGrade;
	@Wire
	Label stationGrade;
	@Wire
	Label agreementGrade;
	@Wire
	Label agreement2Grade;
	@Wire
	Tab tabOper;
	@Wire
	Listbox lstOralRevisor;

	@Wire
	private Combobox cmbTask;

	@Wire
	private Listbox swapTaskListbox;

	@Wire
	private Checkbox resetDistribution;

	@Override
	protected Class<TeamSession> getSessionClass( )
	{
		return TeamSession.class;
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		this.listBox.setItemRenderer( new SubscriptionItemRenderer( ) );
		this.listDetail.setItemRenderer( new DistributionInfoListRenderer( ) );
		this.lstOralRevisor.setItemRenderer( new InepStatusOralDistributionsListRenderer( ) );
		this.swapTaskListbox.setItemRenderer( new InepTaskListRenderer( ) );
		this.loadCombobox( );

		if ( this.getRevisor( ) == null ) {
			this.cmbTask.setVisible( true );
			this.tabOper.setVisible( true );
		}
		else {
			this.tabOper.setVisible( false );
		}
	}

	private void loadCombobox( )
	{
		List<InepEvent> events = this.getSession( ).getEvents( this.getPrincipal( ) );

		if ( SysUtils.isEmpty( this.getComboEvent( ).getItems( ) ) == false ) {
			this.getComboEvent( ).getItems( ).clear( );
		}
		for ( InepEvent e : events ) {
			Comboitem item = this.getComboEvent( ).appendItem( e.getDescription( ) );
			item.setValue( e );
		}
		if ( this.getComboEvent( ).getItemCount( ) > 0 ) {
			this.getComboEvent( ).setSelectedIndex( 0 );
			InepEvent event = this.getComboEvent( ).getSelectedItem( ).getValue( );
			this.loadComboboxTask( event );
			ListModelList<InepTask> tasks = new ListModelList<InepTask>( this.getSession( ).getTasks( event ) );
			tasks.setMultiple( true );
			this.swapTaskListbox.setModel( tasks );
		}
	}

	private void loadComboboxTask( InepEvent event )
	{
		List<InepTask> events = this.getSession( ).getTasks( event );

		this.cmbTask.getItems( ).clear( );
		for ( InepTask e : events ) {
			if ( this.getRevisor( ) == null || this.getRevisor( ).getTask( ) == null || this.getRevisor( ).getTask( ).equals( e ) ) {
				Comboitem item = this.cmbTask.appendItem( e.getDescription( ) );
				item.setValue( e );
			}
		}
		if ( this.cmbTask.getItemCount( ) > 0 ) {
			this.cmbTask.setSelectedIndex( 0 );
		}
	}

	private Combobox getComboEvent( )
	{
		return this.comboEvent;
	}

	private void search( String value )
	{
		if ( SysUtils.isEmpty( value ) == false && value.length( ) >= 3 ) {
			if ( this.showSubscriptions( value ) == false ) {
				this.subscription.close( );
			}
		}
		else {
			this.subscription.close( );
		}
	}

	@Listen( "onOpen=bandbox" )
	public void onOpenBandbox( OpenEvent evt )
	{
		if ( evt != null ) {
			LOGGER.info( "onOpen[bandbox]: " + evt.getValue( ) );
			this.search( evt.getValue( ).toString( ) );
			evt.stopPropagation( );
		}
	}

	@Listen( "onOK=bandbox" )
	public void onOkBandbox( Event evt )
	{
		if ( evt != null ) {
			this.subscription.open( );
			LOGGER.info( "onOK[bandbox]: " + this.subscription.getValue( ) );
			this.search( this.subscription.getValue( ) );
			evt.stopPropagation( );
		}
	}

	private boolean showSubscriptions( String part )
	{
		if ( this.getComboEvent( ).getSelectedItem( ) == null ) {
			return false;
		}
		InepEvent event = this.getComboEvent( ).getSelectedItem( ).getValue( );
		if ( event == null ) {
			return false;
		}
		LOGGER.info( "Searching for: " + part );
		List<InepSubscription> list = this.getSession( ).getSubscriptions( this.getPrincipal( ), event, part );
		if ( SysUtils.isEmpty( list ) ) {
			return false;
		}
		this.listBox.setModel( new ListModelList<InepSubscription>( list ) );
		return true;
	}

	@Listen( "onSelect = #list" )
	public void onSelect( Event evt )
	{
		InepSubscription s = this.getCurrentSubscription( );
		this.subscription.setValue( s.getId( ).getId( ) );
		this.subscription.close( );
		this.showInfo( s );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	private InepSubscription getCurrentSubscription( )
	{
		Listitem item = this.listBox.getSelectedItem( );
		if ( item == null || item.getValue( ) == null ) {
			return null;
		}
		return item.getValue( );
	}

	@Listen( "onSelect = #cmbTask" )
	public void onSelectTask( Event evt )
	{
		this.onSelect( evt );
	}

	private void showInfo( InepSubscription s )
	{
		if ( s == null ) {
			return;
		}
		List<InepDistribution> list = this.getSession( ).getDistribution( s );
		this.listDetail.setModel( new ListModelList<InepDistribution>( list ) );
		this.showFrame( s );
		this.showOralGrade( s );
		this.showTasks( s );

	}

	private void showOralGrade( InepSubscription s )
	{
		InepOralTest test = this.getSession( ).getOralTest( s );
		this.station.setValue( "" );
		this.interviewerGrade.setValue( "" );
		this.observerGrade.setValue( "" );
		this.stationGrade.setValue( "" );
		this.agreementGrade.setValue( "" );
		this.agreement2Grade.setValue( "" );
		if ( test != null ) {
			this.station.setValue( test.getStation( ) );
			this.interviewerGrade.setValue( test.getInterviewGrade( ).toString( ) );
			this.observerGrade.setValue( test.getObserverGrade( ).toString( ) );
			this.stationGrade.setValue( test.getFinalGrade( ).toString( ) );
			if ( test.getAgreementGrade( ) != null ) {
				this.agreementGrade.setValue( test.getAgreementGrade( ).toString( ) );
			}
			if ( test.getAgreement2Grade( ) != null ) {
				this.agreement2Grade.setValue( test.getAgreement2Grade( ).toString( ) );
			}
		}
		List<InepOralDistribution> list = this.getSession( ).getOralDistributions( test );
		this.lstOralRevisor.setModel( new ListModelList<InepOralDistribution>( list ) );
	}

	private void showTasks( InepSubscription s )
	{
		List<InepTest> tests = this.getSession( ).getTests( this.getPrincipal( ), s );
		if ( !SysUtils.isEmpty( tests ) ) {
			for ( InepTest test : tests ) {
				switch ( test.getId( ).getTaskId( ) ) {
				case 1:
					this.task1.setValue( test.getGrade( ).toString( ) );
					break;
				case 2:
					this.task2.setValue( test.getGrade( ).toString( ) );
					break;
				case 3:
					this.task3.setValue( test.getGrade( ).toString( ) );
					break;
				case 4:
					this.task4.setValue( test.getGrade( ).toString( ) );
					break;
				}
			}
		}
	}

	private void showFrame( InepSubscription s )
	{
		if ( this.cmbTask.getSelectedItem( ) != null ) {
			InepTask task = (InepTask) this.cmbTask.getSelectedItem( ).getValue( );
			byte[ ] obj = this.getSession( ).getMedia( s, task );
			AMedia media = null;
			if ( obj != null ) {
				media = new AMedia( s.getId( ).getId( ) + "-" + task.getId( ).getId( ) + ".pdf", "pdf", "application/pdf", obj );
			}
			this.framePdf.setContent( media );
		}
	}

	public InepRevisor getRevisor( )
	{
		if ( this.revisor == null ) {
			if ( this.getComboEvent( ).getSelectedItem( ) != null ) {
				this.revisor = this.getSession( ).getRevisor( (InepEvent) this.getComboEvent( ).getSelectedItem( ).getValue( ),
						this.getPrincipal( ) );
			}
		}
		return this.revisor;
	}

	@SuppressWarnings( { "unchecked", "rawtypes" } )
	@Listen( "onClick=#swapTask" )
	public void onSwapTask( MouseEvent evt )
	{
		ListModelList<InepTask> model = (ListModelList<InepTask>) (Object) this.swapTaskListbox.getModel( );
		if ( model == null || SysUtils.isEmpty( model.getSelection( ) ) ) {
			Messagebox.show( "Por favor selecione ao menos 2(duas) tarefas", "Trocar Tarefa", Messagebox.OK, Messagebox.EXCLAMATION );
			return;
		}
		if ( this.getCurrentSubscription( ) == null ) {
			Messagebox.show( "Não existe uma inscrição selecionada", "Reinicar Tarefa", Messagebox.OK, Messagebox.ERROR );
			return;
		}
		if ( model.getSelection( ).size( ) != 2 ) {
			Messagebox.show( "Por favor selecione 2(duas) tarefas apenas", "Trocar Tarefas", Messagebox.OK, Messagebox.EXCLAMATION );
			return;
		}
		Messagebox.show( "Confirma a ação para as tarefas selecionadas", "Reinicar Tarefa", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener( )
				{
					@Override
					public void onEvent( Event e )
					{
						if ( Messagebox.ON_YES.equals( e.getName( ) ) ) {
							ListModelList<InepTask> model = (ListModelList<InepTask>) (Object) SubscriptionStatus.this.swapTaskListbox.getModel( );
							ArrayList<InepTask> tasks = new ArrayList<InepTask>( model.getSelection( ) );
							SubscriptionStatus.this.getSession( ).swapTasks( SubscriptionStatus.this.getCurrentSubscription( ), tasks.get( 0 ),
									tasks.get( 1 ) );
							SubscriptionStatus.this.onSelect( null );
							Messagebox.show( "As provas foram trocadas com suceso", "Trocar Provas", Messagebox.OK, Messagebox.INFORMATION );
						}
					}
				} );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	@SuppressWarnings( { "unchecked", "rawtypes" } )
	@Listen( "onClick=#resetDistribution" )
	public void onResetDistribution( MouseEvent evt )
	{
		ListModelList<InepTask> model = (ListModelList<InepTask>) (Object) this.swapTaskListbox.getModel( );
		if ( model == null || SysUtils.isEmpty( model.getSelection( ) ) ) {
			Messagebox.show( "Por favor selecione ao menos 1(uma) tarefa", "Reinicar Tarefa", Messagebox.OK, Messagebox.EXCLAMATION );
			return;
		}
		if ( this.getCurrentSubscription( ) == null ) {
			Messagebox.show( "Não existe uma inscrição selecionada", "Reinicar Tarefa", Messagebox.OK, Messagebox.ERROR );
			return;
		}
		Messagebox.show( "Confirma a ação para as tarefas selecionadas", "Reinicar Tarefa", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener( )
				{
					@Override
					public void onEvent( Event e )
					{
						if ( Messagebox.ON_YES.equals( e.getName( ) ) ) {
							ListModelList<InepTask> model = (ListModelList<InepTask>) (Object) SubscriptionStatus.this.swapTaskListbox.getModel( );
							ArrayList<InepTask> tasks = new ArrayList<InepTask>( model.getSelection( ) );
							SubscriptionStatus.this.getSession( ).resetTasks( SubscriptionStatus.this.getPrincipal( ),
									SubscriptionStatus.this.getCurrentSubscription( ), tasks );
							SubscriptionStatus.this.onSelect( null );
							Messagebox.show( "Reinicialização concluída com sucesso", "Reinicar Tarefa", Messagebox.OK, Messagebox.INFORMATION );
						}
					}
				} );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}
}
