package br.com.mcampos.web.controller.client;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.ejb.user.client.Client;
import br.com.mcampos.ejb.user.client.ClientSession;
import br.com.mcampos.ejb.user.document.UserDocument;
import br.com.mcampos.ejb.user.document.type.DocumentType;
import br.com.mcampos.ejb.user.person.Person;
import br.com.mcampos.sysutils.SysUtils;

public class PersonController extends BasePersonController<ClientSession>
{
	private static final long serialVersionUID = -2213603323351941998L;
	private static final int defaultRows = 30;
	private static final Logger logger = LoggerFactory.getLogger( PersonController.class );

	private DBPaging paging;

	@Wire
	private Div divList;

	@Wire
	private Div divData;

	@Wire( "#listTable" )
	private Listbox listBox;

	@Wire( "paging" )
	private Paging listboxPage;

	@Wire
	private Combobox comboMaxRecords;

	@Wire( "#cmdUpdate,#cmdDelete" )
	Button[ ] itemButtons;

	private Client currentClient;

	@Override
	protected Class<ClientSession> getSessionClass( )
	{
		return ClientSession.class;
	}

	protected DBPaging getPaging( )
	{
		if ( this.paging == null ) {
			int rows = this.getListBox( ).getRows( );
			if ( rows == 0 ) {
				rows = defaultRows;
				getListBox( ).setRows( rows );
			}
			this.paging = new DBPaging( 0, rows );
		}
		return this.paging;
	}

	@Override
	protected void show( Person person )
	{
		super.show( person );
		if ( person != null ) {
			getCurrentClient( ).setClient( person );
		}
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		setCurrentClient( null );
		configPaging( );
		getListBox( ).setItemRenderer( new ClientPersonRenderer( ) );
		loadListbox( );
	}

	private void loadListbox( )
	{
		List<Client> clients = getSession( ).getAllPerson( getCurrentCollaborator( ), getPaging( ) );
		getListBox( ).setModel( new ListModelList<Client>( clients ) );
		enableItemButtons( false );
	}

	private Listbox getListBox( )
	{
		return this.listBox;
	}

	private Div getDivList( )
	{
		return this.divList;
	}

	private void configPaging( )
	{
		getListobxPaging( ).setPageSize( getListBox( ).getRows( ) );
		getListobxPaging( ).setTotalSize( getSession( ).countPerson( getCurrentCollaborator( ) ).intValue( ) );
	}

	private Paging getListobxPaging( )
	{
		return this.listboxPage;
	}

	private Div getDivData( )
	{
		return this.divData;
	}

	public Combobox getComboMaxRecords( )
	{
		return this.comboMaxRecords;
	}

	private Client update( Client client )
	{
		if ( client == null ) {
			return null;
		}
		if ( validate( client.getClient( ) ) ) {
			updatePerson( (Person) client.getClient( ) );
			client = getSession( ).addNewPerson( getCurrentCollaborator( ), client );
			int nIndex = getModel( ).indexOf( client );
			if ( nIndex >= 0 ) {
				getModel( ).set( nIndex, client );
			}
			else {
				getModel( ).add( client );
			}
		}
		return client;
	}

	@SuppressWarnings( "unchecked" )
	private ListModelList<Client> getModel( )
	{
		Object obj = getListBox( ).getModel( );
		return (ListModelList<Client>) obj;
	}

	private Client getCurrentClient( )
	{
		if ( this.currentClient == null ) {
			this.currentClient = new Client( getCurrentCollaborator( ).getCompany( ), new Person( ) );
		}
		return this.currentClient;
	}

	private void setCurrentClient( Client currentClient )
	{
		this.currentClient = currentClient;
	}

	private void enableItemButtons( boolean enable )
	{
		if ( this.itemButtons != null ) {
			for ( Button b : this.itemButtons ) {
				b.setDisabled( !enable );
			}
		}
	}

	@Override
	protected boolean onOk( )
	{

		Client client = getCurrentClient( );
		if ( validate( client.getClient( ) ) ) {
			if ( client.getClient( ) == null ) {
				client.setClient( new Person( ) );
			}
			update( client );
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	protected void onCancel( )
	{
		getDivList( ).setVisible( true );
		getDivData( ).setVisible( false );
	}

	private void onUpdate( )
	{
		Client client = getListBox( ).getSelectedItem( ).getValue( );
		setCurrentClient( client );
		Person person = (Person) getSession( ).getUser( getCurrentCollaborator( ), client.getClient( ).getId( ) );
		show( person );
		if ( SysUtils.isEmpty( getCpf( ).getValue( ) ) ) {
			getCpf( ).setFocus( true );
		}
		else {
			getName( ).setFocus( true );
		}
	}

	private void onNew( )
	{
		setCurrentClient( null );
		show( null );
	}

	@Override
	protected Person getPerson( String doc )
	{
		Person person = (Person) getSession( ).getUser( getCurrentCollaborator( ), doc );
		return person;
	}

	@Override
	protected DocumentType getDocumentType( Integer type )
	{
		return getSession( ).getDocumentType( UserDocument.typeCPF );
	}

	/*
	 * ***********************************************************************
	 * ***********************************************************************
	 * 
	 * EVENTS only - Please
	 * 
	 * ***********************************************************************
	 * ***********************************************************************
	 */

	@Listen( "onDoubleClick = #listTable; onSelect = #listTable" )
	public void onDoubleClick( Event evt )
	{
		if ( evt != null ) {
			evt.stopPropagation( );

			if ( evt.getName( ).equals( "onDoubleClick" ) ) {
				getDivList( ).setVisible( false );
				getDivData( ).setVisible( true );
				onUpdate( );
			}
			else {
				enableItemButtons( true );
			}
		}
		if ( evt != null ) {
			logger.info( evt.getName( ) + ": " + evt.getTarget( ).getId( ) );
			evt.stopPropagation( );
		}
		else {
			logger.warn( "onDoubleClick/OnSelect with evt null " );
		}
	}

	@Listen( "onPaging = paging" )
	public void onPaging( PagingEvent evt )
	{
		int page = getListobxPaging( ).getActivePage( );
		getPaging( ).setPage( page );
		loadListbox( );
		if ( evt != null ) {
			logger.info( "onPaging: " + evt.getTarget( ).getId( ) );
			evt.stopPropagation( );
		}
		else {
			logger.warn( "onPaging with evt null " );
		}
	}

	@Listen( "onClick=#cmdCreate, #cmdUpdate" )
	public void onNewPerson( Event evt )
	{
		if ( evt != null ) {
			if ( evt.getTarget( ).getId( ).equals( "cmdCreate" ) ) {
				onNew( );
			}
			else {
				onUpdate( );
			}
			getDivList( ).setVisible( false );
			getDivData( ).setVisible( true );
		}
		if ( evt != null ) {
			logger.info( "onNewPerson: " + evt.getTarget( ).getId( ) );
			evt.stopPropagation( );
		}
		else {
			logger.warn( "onNewPerson with evt null " );
		}
	}

	@Listen( "onClick=#cmdRefresh" )
	public void onRefresh( Event evt )
	{
		loadListbox( );
		if ( evt != null ) {
			logger.info( "onRefresh: " + evt.getTarget( ).getId( ) );
			evt.stopPropagation( );
		}
		else {
			logger.warn( "onRefresh with evt null " );
		}
	}

	@Listen( "onClick=#cmdDelete" )
	public void onDelete( Event evt )
	{
		if ( getListBox( ) != null && getListBox( ).getSelectedItem( ) != null ) {
			Client client = getListBox( ).getSelectedItem( ).getValue( );
			if ( client != null ) {
				getSession( ).remove( client );
				getModel( ).remove( client );
				enableItemButtons( false );
			}
		}
		if ( evt != null ) {
			logger.info( "onDelete: " + evt.getTarget( ).getId( ) );
			evt.stopPropagation( );
		}
		else {
			logger.warn( "onDelete with evt null " );
		}
	}
}
