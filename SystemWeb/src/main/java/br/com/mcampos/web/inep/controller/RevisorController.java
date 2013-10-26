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

import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.dto.inep.CorretorDTO;
import br.com.mcampos.ejb.inep.InepSession;
import br.com.mcampos.ejb.inep.revisor.InepRevisorSession;
import br.com.mcampos.jpa.inep.InepEvent;
import br.com.mcampos.jpa.inep.InepRevisor;
import br.com.mcampos.jpa.inep.InepTask;
import br.com.mcampos.jpa.user.UserDocument;
import br.com.mcampos.sysutils.ServiceLocator;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.UploadMedia;
import br.com.mcampos.web.core.event.IDialogEvent;
import br.com.mcampos.web.core.listbox.BaseDBListController;
import br.com.mcampos.web.inep.controller.dialog.NewRevisorWindow;
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
			this.infoTask.setValue( field.getTask( ) != null ? field.getTask( ).getDescription( ) : "Oral" );
			this.infoTeamMaster.setValue( field.isCoordenador( ) ? "SIM" : "" );
		}
		else {
			this.revNome.setValue( "" );
			this.infoCPF.setValue( "" );
			this.infoEmail.setValue( "" );
			this.infoTask.setValue( "" );
			this.infoTeamMaster.setValue( "" );
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
		return this.comboTask;
	}

	private Combobox getComboEvent( )
	{
		return this.comboEvent;
	}

	@Listen( "onSelect = #comboEvent" )
	public void onSelectEvent( Event evt )
	{
		Comboitem item = this.getComboEvent( ).getSelectedItem( );
		this.loadComboTask( (InepEvent) item.getValue( ) );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	@Listen( "onSelect = #comboTask" )
	public void onSelectTask( Event evt )
	{
		this.loadPage( 0 );
		if ( evt != null ) {
			evt.stopPropagation( );
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
			this.onSelectEvent( null );
		}
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		this.loadCombobox( );
	}

	private void loadComboTask( InepEvent event )
	{
		List<InepTask> list = this.getSession( ).getTasks( event );

		if ( SysUtils.isEmpty( this.getComboTask( ).getItems( ) ) == false ) {
			this.getComboTask( ).getItems( ).clear( );
		}
		this.getComboTask( ).appendItem( "Todas as Tarefas" );
		for ( InepTask e : list ) {
			Comboitem item = this.getComboTask( ).appendItem( e.getDescription( ) );
			item.setValue( e );
		}
		if ( this.getComboTask( ).getItemCount( ) > 0 ) {
			this.getComboTask( ).setSelectedIndex( 0 );
			this.loadPage( 0 );
		}
	}

	@Override
	protected void loadPage( int activePage )
	{
		Comboitem comboItem;
		List<InepRevisor> list = Collections.emptyList( );

		comboItem = this.getComboTask( ).getSelectedItem( );
		if ( comboItem != null && comboItem.getValue( ) != null ) {
			list = this.getSession( ).getAll( (InepTask) comboItem.getValue( ) );
		}
		else if ( this.getComboEvent( ).getSelectedItem( ) != null ) {
			list = this.getSession( ).getAll( (InepEvent) this.getComboEvent( ).getSelectedItem( ).getValue( ) );
		}
		this.getListbox( ).setModel( new ListModelList<InepRevisor>( list ) );
	}

	@Listen( "onUpload = #loadRevisor" )
	public void loadRevisor( UploadEvent evt )
	{

		if ( evt != null ) {
			try {
				MediaDTO m = UploadMedia.getMedia( evt.getMedia( ) );
				this.loadRevisor( m );
			}
			catch ( IOException e ) {
				e.printStackTrace( );
			}
			evt.stopPropagation( );
		}
	}

	private void loadRevisor( MediaDTO media )
	{
		Comboitem item = this.getComboEvent( ).getSelectedItem( );
		InepEvent event = ( (InepEvent) item.getValue( ) );

		try {

			String cvs = new String( media.getObject( ), "UTF-8" );
			BufferedReader br = new BufferedReader( new StringReader( cvs ) );
			String line;
			int lineNumber = 0;
			Integer task;

			while ( SysUtils.isEmpty( ( line = br.readLine( ) ) ) == false ) {
				lineNumber++;
				if ( lineNumber == 1 ) {
					continue;
				}
				String[ ] parts = line.split( ";" );
				Integer type = Integer.parseInt( parts[ 4 ] );
				if ( !SysUtils.isEmpty( parts[ 3 ] ) ) {
					task = Integer.parseInt( parts[ 3 ] );
				}
				else {
					task = null;
				}
				String cpf = parts[ 2 ];
				if ( !SysUtils.isEmpty( cpf ) ) {
					cpf = cpf.replaceAll( "\\.", "" );
					cpf = cpf.replaceAll( "-", "" );
				}
				this.getInepSession( ).add( event, task, parts[ 0 ], parts[ 1 ], cpf, type );
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

		List<InepTask> tasks = this.getInepSession( ).getTasks( (InepEvent) this.getComboEvent( ).getSelectedItem( ).getValue( ) );

		for ( InepTask task : tasks ) {
			logger.info( "Distributing task: " + task.getDescription( ) );
			this.getInepSession( ).distribute( task );
		}
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	public void load( File file ) throws IOException
	{
		BufferedReader input = new BufferedReader( new FileReader( file ) );
		String line;
		InepSession is = this.getInepSession( );

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
			if ( this.inepSession == null ) {
				this.inepSession = (InepSession) ServiceLocator.getInstance( ).getRemoteSession( InepSession.class, ServiceLocator.EJB_NAME[ 0 ] );
			}
		}
		catch ( NamingException e ) {
			logger.error( "getInepSession", e );
		}
		return this.inepSession;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public void onOK( Window wnd )
	{
		if ( !( wnd instanceof NewRevisorWindow ) ) {
			return;
		}
		NewRevisorWindow w = (NewRevisorWindow) wnd;

		InepTask task = this.getComboTask( ).getSelectedItem( ).getValue( );

		InepRevisor rev = this.getInepSession( ).add( task, w.getName( ).getValue( ), w.getEmail( ).getValue( ), w.getCpf( ).getValue( ),
				false );
		if ( rev != null ) {
			ListModelList<InepRevisor> model = null;
			Object objModel = this.getListbox( ).getModel( );
			if ( objModel != null ) {
				model = (ListModelList<InepRevisor>) objModel;
			}
			if ( model == null ) {
				model = new ListModelList<InepRevisor>( );
				this.getListbox( ).setModel( model );
			}
			model.add( rev );
		}

	}
}
