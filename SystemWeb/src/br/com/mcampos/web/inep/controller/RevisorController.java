package br.com.mcampos.web.inep.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import br.com.mcampos.dto.inep.CorretorDTO;
import br.com.mcampos.ejb.inep.InepSession;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepRevisor;
import br.com.mcampos.ejb.inep.entity.InepTask;
import br.com.mcampos.ejb.inep.revisor.InepRevisorSession;
import br.com.mcampos.ejb.user.document.UserDocument;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseDialogWindow;
import br.com.mcampos.web.core.event.IDialogEvent;
import br.com.mcampos.web.core.listbox.BaseDBListController;
import br.com.mcampos.web.inep.controller.renderer.RevisorListRenderer;
import br.com.mcampos.web.locator.ServiceLocator;

public class RevisorController extends BaseDBListController<InepRevisorSession, InepRevisor> implements IDialogEvent
{
	public static final String path = "T:\\temp\\201212_carga_inep.csv";
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
		infoTask.setValue( field.getTask( ).getDescription( ) );
		infoTeamMaster.setValue( field.isCoordenador( ) ? "SIM" : "" );
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
		addRevisor.setVisible( false );
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
		addRevisor.setVisible( comboItem != null && comboItem.getValue( ) != null );
		if ( comboItem != null && comboItem.getValue( ) != null ) {
			list = getSession( ).getAll( (InepTask) comboItem.getValue( ) );
		}
		else if ( getComboEvent( ).getSelectedItem( ) != null )
		{
			list = getSession( ).getAll( (InepPackage) getComboEvent( ).getSelectedItem( ).getValue( ) );
		}
		getListbox( ).setModel( new ListModelList<InepRevisor>( list ) );
	}

	@Listen( "onClick = #addRevisor" )
	public void loadRevisor( Event event )
	{
		Component c = createComponents( "/private/inep/utils/new_revisor.zul", getMainWindow( ), null );

		if ( c != null && c instanceof BaseDialogWindow ) {
			BaseDialogWindow dlg = (BaseDialogWindow) c;
			doModal( dlg, this );
		}
		else {
			if ( c != null )
				c.detach( );
		}
		if ( event != null )
			event.stopPropagation( );
	}

	@Listen( "onClick = #loadRevisor" )
	public void loadRevisor( )
	{
		File sourcefile = new File( path );
		if ( !sourcefile.exists( ) ) {
			logger.info( "Arquivo " + path + " nao existe" );
			return;
		}
		try {
			logger.debug( "Loading records" );
			load( sourcefile );
		}
		catch ( IOException e ) {
			e.printStackTrace( );
		}
	}

	@Listen( "onClick = #distribute" )
	public void distribute( Event evt )
	{

		List<InepTask> tasks = getInepSession( ).getTasks( (InepPackage) getComboEvent( ).getSelectedItem( ).getValue( ) );

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
