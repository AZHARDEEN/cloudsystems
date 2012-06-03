package br.com.mcampos.web.inep.controller;

import java.util.Collections;
import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepRevisor;
import br.com.mcampos.ejb.inep.entity.InepTask;
import br.com.mcampos.ejb.inep.revisor.InepRevisorSession;
import br.com.mcampos.ejb.user.document.UserDocument;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.listbox.BaseDBListController;
import br.com.mcampos.web.inep.controller.renderer.RevisorListRenderer;

public class RevisorController extends BaseDBListController<InepRevisorSession, InepRevisor>
{
	private static final long serialVersionUID = -1355230877237131653L;

	@Wire
	Combobox comboEvent;
	@Wire
	Combobox comboTask;

	@Wire
	Label revNome;
	@Wire
	Label infoCPF;
	@Wire
	Label infoEmail;
	@Wire
	Label infoTask;
	@Wire
	Label infoTeamMaster;

	@Override
	protected void showFields( InepRevisor field )
	{
		this.revNome.setValue( field.getCollaborator( ).getPerson( ).getName( ) );
		this.infoCPF.setValue( "" );
		this.infoEmail.setValue( "" );
		for ( UserDocument doc : field.getCollaborator( ).getPerson( ).getDocuments( ) ) {
			switch ( doc.getType( ).getId( ) ) {
			case 1:
				this.infoCPF.setValue( doc.getCode( ) );
				break;
			case 6:
				this.infoEmail.setValue( doc.getCode( ) );
				break;
			}
		}
		this.infoTask.setValue( field.getTask( ).getDescription( ) );
		this.infoTeamMaster.setValue( field.isCoordenador( ) ? "SIM" : "" );
	}

	@Override
	protected void updateTargetEntity( InepRevisor entity )
	{

	}

	@Override
	protected boolean validateEntity( InepRevisor entity, int operation )
	{
		return true;
	}

	@Override
	protected Class<InepRevisorSession> getSessionClass( )
	{
		return InepRevisorSession.class;
	}

	@Override
	protected ListitemRenderer<InepRevisor> getListRenderer( )
	{
		return new RevisorListRenderer( );
	}

	private Combobox getComboTask( )
	{
		return this.comboTask;
	}

	private Combobox getComboEvent( )
	{
		return this.comboEvent;
	}

	@Listen( "onSelect = #comboEvent" )
	public void onSelectEvent( Event evt )
	{
		Comboitem item = getComboEvent( ).getSelectedItem( );
		loadComboTask( (InepPackage) item.getValue( ) );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	@Listen( "onSelect = #comboTask" )
	public void onSelectTask( Event evt )
	{
		loadPage( 0 );
		if ( evt != null ) {
			evt.stopPropagation( );
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
			onSelectEvent( null );
		}
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		loadCombobox( );
	}

	private void loadComboTask( InepPackage event )
	{
		List<InepTask> list = getSession( ).getTasks( event );

		if ( SysUtils.isEmpty( getComboTask( ).getItems( ) ) == false ) {
			getComboTask( ).getItems( ).clear( );
		}
		getComboTask( ).appendItem( "Todas as Tarefas" );
		for ( InepTask e : list ) {
			Comboitem item = getComboTask( ).appendItem( e.getDescription( ) );
			item.setValue( e );
		}
		if ( getComboTask( ).getItemCount( ) > 0 ) {
			getComboTask( ).setSelectedIndex( 0 );
			loadPage( 0 );
		}
	}

	@Override
	protected void loadPage( int activePage )
	{
		Comboitem comboItem;
		List<InepRevisor> list = Collections.emptyList( );

		comboItem = getComboTask( ).getSelectedItem( );
		if ( comboItem != null && comboItem.getValue( ) != null ) {
			list = getSession( ).getAll( (InepTask) comboItem.getValue( ) );
		}
		else if ( getComboEvent( ).getSelectedItem( ) != null )
		{
			list = getSession( ).getAll( (InepPackage) getComboEvent( ).getSelectedItem( ).getValue( ) );
		}
		getListbox( ).setModel( new ListModelList<InepRevisor>( list ) );
	}

}
