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

import br.com.mcampos.ejb.inep.entity.InepDistribution;
import br.com.mcampos.ejb.inep.entity.InepOralDistribution;
import br.com.mcampos.ejb.inep.entity.InepOralTest;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepRevisor;
import br.com.mcampos.ejb.inep.entity.InepSubscription;
import br.com.mcampos.ejb.inep.entity.InepTask;
import br.com.mcampos.ejb.inep.entity.InepTest;
import br.com.mcampos.ejb.inep.team.TeamSession;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseDBLoggedController;
import br.com.mcampos.web.inep.controller.renderer.InepTaskListRenderer;
import br.com.mcampos.web.inep.utils.DistributionInfoListRenderer;
import br.com.mcampos.web.inep.utils.InepStatusOralDistributionsListRenderer;
import br.com.mcampos.web.inep.utils.SubscriptionItemRenderer;

public class SubscriptionStatus extends BaseDBLoggedController<TeamSession>
{
	private static final long serialVersionUID = 4073500689607424974L;
	private static final Logger logger = LoggerFactory.getLogger( SubscriptionStatus.class );

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
		listBox.setItemRenderer( new SubscriptionItemRenderer( ) );
		listDetail.setItemRenderer( new DistributionInfoListRenderer( ) );
		lstOralRevisor.setItemRenderer( new InepStatusOralDistributionsListRenderer( ) );
		swapTaskListbox.setItemRenderer( new InepTaskListRenderer( ) );
		loadCombobox( );

		if ( getRevisor( ) == null ) {
			cmbTask.setVisible( true );
			tabOper.setVisible( true );
		}
		else {
			tabOper.setVisible( false );
		}
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
			InepPackage event = getComboEvent( ).getSelectedItem( ).getValue( );
			loadComboboxTask( event );
			ListModelList<InepTask> tasks = new ListModelList<InepTask>( getSession( ).getTasks( event ) );
			tasks.setMultiple( true );
			swapTaskListbox.setModel( tasks );
		}
	}

	private void loadComboboxTask( InepPackage event )
	{
		List<InepTask> events = getSession( ).getTasks( event );

		cmbTask.getItems( ).clear( );
		for ( InepTask e : events ) {
			if ( getRevisor( ) == null || getRevisor( ).getTask( ).equals( e ) ) {
				Comboitem item = cmbTask.appendItem( e.getDescription( ) );
				item.setValue( e );
			}
		}
		if ( cmbTask.getItemCount( ) > 0 ) {
			cmbTask.setSelectedIndex( 0 );
		}
	}

	private Combobox getComboEvent( )
	{
		return comboEvent;
	}

	private void search( String value )
	{
		if ( SysUtils.isEmpty( value ) == false && value.length( ) >= 3 ) {
			if ( showSubscriptions( value ) == false ) {
				subscription.close( );
			}
		}
		else {
			subscription.close( );
		}
	}

	@Listen( "onOpen=bandbox" )
	public void onOpenBandbox( OpenEvent evt )
	{
		if ( evt != null ) {
			logger.info( "onOpen[bandbox]: " + evt.getValue( ) );
			search( evt.getValue( ).toString( ) );
			evt.stopPropagation( );
		}
	}

	@Listen( "onOK=bandbox" )
	public void onOkBandbox( Event evt )
	{
		if ( evt != null ) {
			subscription.open( );
			logger.info( "onOK[bandbox]: " + subscription.getValue( ) );
			search( subscription.getValue( ) );
			evt.stopPropagation( );
		}
	}

	private boolean showSubscriptions( String part )
	{
		if ( getComboEvent( ).getSelectedItem( ) == null ) {
			return false;
		}
		InepPackage event = getComboEvent( ).getSelectedItem( ).getValue( );
		if ( event == null ) {
			return false;
		}
		logger.info( "Searching for: " + part );
		List<InepSubscription> list = getSession( ).getSubscriptions( event, part );
		if ( SysUtils.isEmpty( list ) ) {
			return false;
		}
		listBox.setModel( new ListModelList<InepSubscription>( list ) );
		return true;
	}

	@Listen( "onSelect = #list" )
	public void onSelect( Event evt )
	{
		InepSubscription s = getCurrentSubscription( );
		subscription.setValue( s.getId( ).getId( ) );
		subscription.close( );
		showInfo( s );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	private InepSubscription getCurrentSubscription( )
	{
		Listitem item = listBox.getSelectedItem( );
		if ( item == null || item.getValue( ) == null ) {
			return null;
		}
		return item.getValue( );
	}

	@Listen( "onSelect = #cmbTask" )
	public void onSelectTask( Event evt )
	{
		onSelect( evt );
	}

	private void showInfo( InepSubscription s )
	{
		if ( s == null ) {
			return;
		}
		List<InepDistribution> list = getSession( ).getDistribution( s );
		listDetail.setModel( new ListModelList<InepDistribution>( list ) );
		showFrame( s );
		showOralGrade( s );
		showTasks( s );

	}

	private void showOralGrade( InepSubscription s )
	{
		InepOralTest test = getSession( ).getOralTest( s );
		station.setValue( "" );
		interviewerGrade.setValue( "" );
		observerGrade.setValue( "" );
		stationGrade.setValue( "" );
		agreementGrade.setValue( "" );
		agreement2Grade.setValue( "" );
		if ( test != null ) {
			station.setValue( test.getStation( ) );
			interviewerGrade.setValue( test.getInterviewGrade( ).toString( ) );
			observerGrade.setValue( test.getObserverGrade( ).toString( ) );
			stationGrade.setValue( test.getFinalGrade( ).toString( ) );
			if ( test.getAgreementGrade( ) != null )
				agreementGrade.setValue( test.getAgreementGrade( ).toString( ) );
			if ( test.getAgreement2Grade( ) != null )
				agreement2Grade.setValue( test.getAgreement2Grade( ).toString( ) );
		}
		List<InepOralDistribution> list = getSession( ).getOralDistributions( test );
		lstOralRevisor.setModel( new ListModelList<InepOralDistribution>( list ) );
	}

	private void showTasks( InepSubscription s )
	{
		List<InepTest> tests = getSession( ).getTests( s );
		if ( !SysUtils.isEmpty( tests ) ) {
			for ( InepTest test : tests ) {
				switch ( test.getId( ).getTaskId( ) ) {
				case 1:
					task1.setValue( test.getGrade( ).toString( ) );
					break;
				case 2:
					task2.setValue( test.getGrade( ).toString( ) );
					break;
				case 3:
					task3.setValue( test.getGrade( ).toString( ) );
					break;
				case 4:
					task4.setValue( test.getGrade( ).toString( ) );
					break;
				}
			}
		}
	}

	private void showFrame( InepSubscription s )
	{
		InepTask task = (InepTask) cmbTask.getSelectedItem( ).getValue( );
		byte[ ] obj = getSession( ).getMedia( s, task );
		AMedia media = null;
		if ( obj != null )
			media = new AMedia( s.getId( ).getId( ) + "-" + task.getId( ).getId( ) + ".pdf", "pdf", "application/pdf", obj );
		framePdf.setContent( media );
	}

	public InepRevisor getRevisor( )
	{
		if ( revisor == null ) {
			if ( getComboEvent( ).getSelectedItem( ) != null ) {
				revisor = getSession( ).getRevisor( (InepPackage) getComboEvent( ).getSelectedItem( ).getValue( ),
						getCurrentCollaborator( ) );
			}
		}
		return revisor;
	}

	@SuppressWarnings( { "unchecked", "rawtypes" } )
	@Listen( "onClick=#swapTask" )
	public void onSwapTask( MouseEvent evt )
	{
		ListModelList<InepTask> model = (ListModelList<InepTask>) (Object) swapTaskListbox.getModel( );
		if ( model == null || SysUtils.isEmpty( model.getSelection( ) ) ) {
			Messagebox.show( "Por favor selecione ao menos 2(duas) tarefas", "Trocar Tarefa", Messagebox.OK, Messagebox.EXCLAMATION );
			return;
		}
		if ( getCurrentSubscription( ) == null ) {
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
							ListModelList<InepTask> model = (ListModelList<InepTask>) (Object) swapTaskListbox.getModel( );
							ArrayList<InepTask> tasks = new ArrayList<InepTask>( model.getSelection( ) );
							getSession( ).swapTasks( getCurrentSubscription( ), tasks.get( 0 ), tasks.get( 1 ) );
							onSelect( null );
							Messagebox.show( "As provas foram trocadas com suceso", "Trocar Provas", Messagebox.OK, Messagebox.INFORMATION );
						}
					}
				} );
		if ( evt != null )
			evt.stopPropagation( );
	}

	@SuppressWarnings( { "unchecked", "rawtypes" } )
	@Listen( "onClick=#resetDistribution" )
	public void onResetDistribution( MouseEvent evt )
	{
		ListModelList<InepTask> model = (ListModelList<InepTask>) (Object) swapTaskListbox.getModel( );
		if ( model == null || SysUtils.isEmpty( model.getSelection( ) ) ) {
			Messagebox.show( "Por favor selecione ao menos 1(uma) tarefa", "Reinicar Tarefa", Messagebox.OK, Messagebox.EXCLAMATION );
			return;
		}
		if ( getCurrentSubscription( ) == null ) {
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
							ListModelList<InepTask> model = (ListModelList<InepTask>) (Object) swapTaskListbox.getModel( );
							ArrayList<InepTask> tasks = new ArrayList<InepTask>( model.getSelection( ) );
							getSession( ).resetTasks( getCurrentSubscription( ), tasks );
							onSelect( null );
							Messagebox.show( "Reinicialização concluída com sucesso", "Reinicar Tarefa", Messagebox.OK, Messagebox.INFORMATION );
						}
					}
				} );
		if ( evt != null )
			evt.stopPropagation( );
	}
}
