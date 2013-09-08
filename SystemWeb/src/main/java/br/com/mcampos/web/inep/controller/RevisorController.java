package br.com.mcampos.web.inep.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import br.com.mcampos.dto.MediaDTO;
import br.com.mcampos.dto.inep.CorretorDTO;
import br.com.mcampos.ejb.inep.InepSession;
import br.com.mcampos.ejb.inep.revisor.InepRevisorSession;
import br.com.mcampos.entity.inep.InepEvent;
import br.com.mcampos.entity.inep.InepRevisor;
import br.com.mcampos.entity.inep.InepTask;
import br.com.mcampos.entity.user.UserDocument;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.UploadMedia;
import br.com.mcampos.web.core.event.IDialogEvent;
import br.com.mcampos.web.core.listbox.BaseDBListController;
import br.com.mcampos.web.inep.controller.dialog.NewRevisorWindow;
import br.com.mcampos.web.locator.ServiceLocator;
import br.com.mcampos.web.renderer.inep.RevisorListRenderer;

public class RevisorController extends BaseDBListController<InepRevisorSession, InepRevisor> implements IDialogEvent
{
	private static final Logger logger = LoggerFactory.getLogger( RevisorController.class );

	private static final long serialVersionUID = -1355230877237131653L;
	private transient InepSession inepSession = null;

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
	@Wire
	Toolbarbutton addRevisor;

	@Override
	protected void showFields( InepRevisor field )
	{
		if ( field != null ) {
			revNome.setValue( field.getCollaborator( ).getPerson( ).getName( ) );
			infoCPF.setValue( "" );
			infoEmail.setValue( "" );
			for ( UserDocument doc : field.getCollaborator( ).getPerson( ).getDocuments( ) ) {
				switch ( doc.getType( ).getId( ) ) {
				case 1:
					infoCPF.setValue( doc.getCode( ) );
					break;
				case 6:
					infoEmail.setValue( doc.getCode( ) );
					break;
				}
			}
			infoTask.setValue( field.getTask( ) != null ? field.getTask( ).getDescription( ) : "Oral" );
			infoTeamMaster.setValue( field.isCoordenador( ) ? "SIM" : "" );
		}
		else {
			revNome.setValue( "" );
			infoCPF.setValue( "" );
			infoEmail.setValue( "" );
			infoTask.setValue( "" );
			infoTeamMaster.setValue( "" );
		}
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
		return comboTask;
	}

	private Combobox getComboEvent( )
	{
		return comboEvent;
	}

	@Listen( "onSelect = #comboEvent" )
	public void onSelectEvent( Event evt )
	{
		Comboitem item = getComboEvent( ).getSelectedItem( );
		loadComboTask( (InepEvent) item.getValue( ) );
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
		List<InepEvent> events = getSession( ).getEvents( getPrincipal( ) );

		if ( SysUtils.isEmpty( getComboEvent( ).getItems( ) ) == false ) {
			getComboEvent( ).getItems( ).clear( );
		}
		for ( InepEvent e : events ) {
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

	private void loadComboTask( InepEvent event )
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
			list = getSession( ).getAll( (InepEvent) getComboEvent( ).getSelectedItem( ).getValue( ) );
		}
		getListbox( ).setModel( new ListModelList<InepRevisor>( list ) );
	}

	@Listen( "onUpload = #loadRevisor" )
	public void loadRevisor( UploadEvent evt )
	{

		if ( evt != null ) {
			try {
				MediaDTO m = UploadMedia.getMedia( evt.getMedia( ) );
				loadRevisor( m );
			}
			catch ( IOException e ) {
				e.printStackTrace( );
			}
			evt.stopPropagation( );
		}
	}

	private void loadRevisor( MediaDTO media )
	{
		Comboitem item = getComboEvent( ).getSelectedItem( );
		InepEvent event = ( (InepEvent) item.getValue( ) );

		try {

			String cvs = new String( media.getObject( ), "UTF-8" );
			BufferedReader br = new BufferedReader( new StringReader( cvs ) );
			String line;
			int lineNumber = 0;
			Integer task;

			while ( SysUtils.isEmpty( ( line = br.readLine( ) ) ) == false ) {
				lineNumber++;
				if ( lineNumber == 1 )
					continue;
				String[ ] parts = line.split( ";" );
				Integer type = Integer.parseInt( parts[ 4 ] );
				if ( !SysUtils.isEmpty( parts[ 3 ] ) )
					task = Integer.parseInt( parts[ 3 ] );
				else
					task = null;
				String cpf = parts[ 2 ];
				if ( !SysUtils.isEmpty( cpf ) ) {
					cpf = cpf.replaceAll( "\\.", "" );
					cpf = cpf.replaceAll( "-", "" );
				}
				getInepSession( ).add( event, task, parts[ 0 ], parts[ 1 ], cpf, type );
			}
			br.close( );
		}
		catch ( IOException e ) {
			logger.error( "ProcessFile ", e );
		}
	}

	@Listen( "onClick = #distribute" )
	public void distribute( Event evt )
	{

		List<InepTask> tasks = getInepSession( ).getTasks( (InepEvent) getComboEvent( ).getSelectedItem( ).getValue( ) );

		for ( InepTask task : tasks ) {
			logger.info( "Distributing task: " + task.getDescription( ) );
			getInepSession( ).distribute( task );
		}
		if ( evt != null )
			evt.stopPropagation( );
	}

	public void load( File file ) throws IOException
	{
		BufferedReader input = new BufferedReader( new FileReader( file ) );
		String line;
		InepSession is = getInepSession( );

		while ( ( line = input.readLine( ) ) != null ) {
			logger.info( line );
			CorretorDTO dto = new CorretorDTO( );

			String values[] = line.split( ";" );
			if ( values.length >= 5 ) {
				dto.setCpf( values[ 0 ].trim( ) );
				dto.setEmail( values[ 1 ].trim( ) );
				dto.setNome( values[ 2 ].trim( ) );
				dto.setCoordenador( values[ 3 ].toUpperCase( ).equals( "S" ) );
				dto.setTarefa( Integer.parseInt( values[ 4 ].trim( ) ) );
				dto.setEvento( 1 );
				dto.setUser( 13623 );
				try {
					if ( is.loadCorretore( dto ) == false ) {
						break;
					}
				}
				catch ( Exception e ) {
					logger.error( "load", e );
					break;
				}
			}
		}
		input.close( );
	}

	private InepSession getInepSession( )
	{
		try {
			if ( inepSession == null ) {
				inepSession = (InepSession) ServiceLocator.getInstance( ).getRemoteSession( InepSession.class );
			}
		}
		catch ( NamingException e ) {
			logger.error( "getInepSession", e );
		}
		return inepSession;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public void onOK( Window wnd )
	{
		if ( !( wnd instanceof NewRevisorWindow ) )
			return;
		NewRevisorWindow w = (NewRevisorWindow) wnd;

		InepTask task = getComboTask( ).getSelectedItem( ).getValue( );

		InepRevisor rev = getInepSession( ).add( task, w.getName( ).getValue( ), w.getEmail( ).getValue( ), w.getCpf( ).getValue( ),
				false );
		if ( rev != null ) {
			ListModelList<InepRevisor> model = null;
			Object objModel = getListbox( ).getModel( );
			if ( objModel != null ) {
				model = (ListModelList<InepRevisor>) objModel;
			}
			if ( model == null ) {
				model = new ListModelList<InepRevisor>( );
				getListbox( ).setModel( model );
			}
			model.add( rev );
		}

	}
}
