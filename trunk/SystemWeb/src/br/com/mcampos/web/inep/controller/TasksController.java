package br.com.mcampos.web.inep.controller;

import java.util.Collections;
import java.util.List;

import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import br.com.mcampos.dto.inep.InepTaskCounters;
import br.com.mcampos.ejb.inep.entity.InepDistribution;
import br.com.mcampos.ejb.inep.packs.InepPackage;
import br.com.mcampos.ejb.inep.revisor.InepRevisor;
import br.com.mcampos.ejb.inep.team.TeamSession;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseLoggedController;
import br.com.mcampos.web.inep.controller.event.CoordinatorEventChange;
import br.com.mcampos.web.inep.controller.renderer.InepDistributionRenderer;

public class TasksController extends BaseLoggedController<Window>
{
	private static final long serialVersionUID = -4229563648862167526L;
	public static final String coordinatorEvent = "coordinatorQueueEvent";

	private TeamSession session;

	@Wire
	private Component mainCpomponent;

	@Wire( "listbox#listTable" )
	private Listbox listbox;

	@Wire( value = "paging" )
	private List<Paging> pagings;

	@Wire( "radiogroup" )
	private Radiogroup[ ] notas;

	@Wire
	private Combobox comboEvent;

	@Wire( "#divGrid" )
	private Div divGrid;

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

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		getListbox( ).setItemRenderer( new InepDistributionRenderer( ) );
		loadCombobox( );
		// this.divGrid.setVisible( false );
		updateCounters( );
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
	public void onSelect( )
	{
		InepDistribution d = (InepDistribution) getListbox( ).getSelectedItem( ).getValue( );
		showFields( d );
		if ( d.getRevisor( ).isCoordenador( ) ) {
			EventQueues.lookup( coordinatorEvent, true ).publish(
					new CoordinatorEventChange( getSession( ).getOtherDistributions( d.getTest( ) ) ) );
		}
	}

	@Listen( "onSelect = #comboEvent" )
	public void onSelectPackage( )
	{
		List<InepDistribution> list = Collections.emptyList( );
		Comboitem item = this.comboEvent.getSelectedItem( );
		if ( item != null ) {
			list = getSession( ).getTests( (InepPackage) item.getValue( ), getAuthentication( ) );
		}
		getListbox( ).setModel( new ListModelList<InepDistribution>( list ) );
		updateCounters( );
	}

	protected void showFields( InepDistribution rev )
	{
		if ( rev != null ) {
			for ( Radiogroup item : this.notas ) {
				item.setSelectedItem( null );
			}
			this.divGrid.setVisible( true );
			showFrame( );
			this.divListbox.setVisible( false );
			if ( rev.getNota( ) != null ) {
				this.notas[ 0 ].setSelectedIndex( rev.getNota( ) );
			}
		}
		else {
			this.divFrame.setVisible( true );
		}
	}

	public TeamSession getSession( )
	{
		if ( this.session == null ) {
			this.session = (TeamSession) getSession( TeamSession.class );
		}
		return this.session;
	}

	@Listen( "onClick = #cmdObs" )
	public void onClickComments( )
	{
		Listitem item = getListbox( ).getSelectedItem( );
		if ( item != null ) {
			InepDistribution rev = (InepDistribution) getListbox( ).getSelectedItem( ).getValue( );
			Component comp = Executions.createComponents( "/private/inep/dlg_comment.zul", null, null );
			if ( comp instanceof DlgComment ) {
				DlgComment dlg = ( (DlgComment) comp );

				dlg.setDistribution( rev );
				dlg.setMainController( this );
				dlg.doModal( );
			}
		}
		else {
			Messagebox.show( "Antes de editar um comentário sobre a correção, uma tarefa deve ser selecionada primeiro",
					"Correção",
					Messagebox.OK, Messagebox.INFORMATION );
		}
	}

	@Listen( "onClick = #cmdInepSave" )
	public void onClickSubmit( )
	{
		Listitem item = getListbox( ).getSelectedItem( );
		if ( item != null ) {
			InepDistribution rev = (InepDistribution) getListbox( ).getSelectedItem( ).getValue( );
			rev.setNota( this.notas[ 0 ].getSelectedIndex( ) );
			getSession( ).updateRevision( rev );
			this.divFrame.setVisible( false );
			this.divListbox.setVisible( true );
			this.listbox.removeItemAt( this.listbox.getSelectedIndex( ) );
			this.divGrid.setVisible( false );
			getListbox( ).clearSelection( );
		}
		else {
			Messagebox.show( "Antes de finalizar uma correção, uma tarefa deve ser selecionada primeiro", "Correção",
					Messagebox.OK, Messagebox.INFORMATION );
		}
		updateCounters( );
	}

	@Listen( "onClick = #cmdCancel" )
	public void onClickCancel( )
	{
		showFields( null );
		this.divFrame.setVisible( false );
		this.divListbox.setVisible( true );
		// this.divGrid.setVisible( false );
		getListbox( ).clearSelection( );
		updateCounters( );
	}

	private void showFrame( )
	{
		InepDistribution item = (InepDistribution) this.listbox.getSelectedItem( ).getValue( );
		this.divFrame.setVisible( true );
		String.format( "/img/pdf/%s-%d-4.pdf", item.getId( ).getSubscriptionId( ), item.getId( ).getTaskId( ) );
		AMedia media = new AMedia( null, null, null, getSession( ).getMedia( item.getTest( ) ) );
		this.framePdf.setContent( media );
	}

	private void loadCombobox( )
	{
		List<InepPackage> events = getSession( ).getEvents( getAuthentication( ) );

		if ( SysUtils.isEmpty( getComboEvent( ).getItems( ) ) == false ) {
			getComboEvent( ).getItems( ).clear( );
		}
		for ( InepPackage e : events ) {
			Comboitem item = getComboEvent( ).appendItem( e.getDescription( ) );
			item.setValue( e );
		}
		if ( getComboEvent( ).getItemCount( ) > 0 ) {
			getComboEvent( ).setSelectedIndex( 0 );
			onSelectPackage( );
		}
	}

	public Combobox getComboEvent( )
	{
		return this.comboEvent;
	}

	public InepRevisor getRevisor( )
	{
		if ( this.revisor == null ) {
			this.revisor = getSession( ).getRevisor( (InepPackage) getComboEvent( ).getSelectedItem( ).getValue( ),
					getAuthentication( ) );
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
				percent = ( (double) dto.getTasks( ) ) / ( (double) total );
				this.countAll.setLabel( String.format( "%04d de %04d - %06.2f%%", dto.getTasks( ), total, percent ) );
				percent = ( (double) dto.getRevised( ) ) / ( (double) total );
				this.countRevised.setLabel( String.format( "%04d de %04d - %06.2f%%", dto.getRevised( ), total, percent ) );
				percent = ( (double) dto.getVariance( ) ) / ( (double) total );
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

}
