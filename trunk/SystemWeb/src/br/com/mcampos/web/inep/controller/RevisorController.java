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
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Window;

import br.com.mcampos.dto.inep.CorretorDTO;
import br.com.mcampos.ejb.inep.InepSession;
import br.com.mcampos.ejb.inep.entity.InepPackage;
import br.com.mcampos.ejb.inep.entity.InepRevisor;
import br.com.mcampos.ejb.inep.entity.InepTask;
import br.com.mcampos.ejb.inep.revisor.InepRevisorSession;
import br.com.mcampos.ejb.user.document.UserDocument;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.listbox.BaseDBListController;
import br.com.mcampos.web.inep.controller.renderer.RevisorListRenderer;
import br.com.mcampos.web.locator.ServiceLocator;

public class RevisorController extends BaseDBListController<InepRevisorSession, InepRevisor>
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

	@Listen( "onClick = #loadRevisor" )
	public void loadRevisor( )
	{
		File sourcefile = new File( path );
		if ( !sourcefile.exists( ) ) {
			System.out.println( "Arquivo " + path + " nao existe" );
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
	public void distribute( )
	{
		List<InepTask> tasks = getInepSession( ).getTasks( );

		for ( InepTask task : tasks ) {
			System.out.println( "Distributing task: " + task.getDescription( ) );
			getInepSession( ).distribute( task );
		}
	}

	public void load( File file ) throws IOException
	{
		BufferedReader input = new BufferedReader( new FileReader( file ) );
		String line;
		InepSession is = getInepSession( );

		while ( ( line = input.readLine( ) ) != null ) {
			System.out.println( line );
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
					e.printStackTrace( );
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
				this.inepSession = (InepSession) ServiceLocator.getInstance( ).getRemoteSession( InepSession.class );
			}
		}
		catch ( NamingException e ) {
			e.printStackTrace( );
		}
		return this.inepSession;
	}

}
