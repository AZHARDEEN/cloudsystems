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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.ejb.user.client.Client;
import br.com.mcampos.ejb.user.client.ClientSession;
import br.com.mcampos.ejb.user.document.UserDocument;
import br.com.mcampos.ejb.user.person.Person;
import br.com.mcampos.sysutils.CPF;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.combobox.CityCombobox;
import br.com.mcampos.web.core.combobox.CivilStateCombobox;
import br.com.mcampos.web.core.combobox.GenderCombobox;
import br.com.mcampos.web.core.combobox.StateCombobox;
import br.com.mcampos.web.core.combobox.TitleCombobox;

public class ClientController extends UserController<ClientSession>
{
	private static final long serialVersionUID = -2213603323351941998L;
	private static final int defaultRows = 30;
	private static final Logger logger = LoggerFactory.getLogger( ClientController.class );

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

	@Wire
	private Textbox cpf;

	@Wire
	private GenderCombobox gender;
	@Wire
	protected StateCombobox state;
	@Wire
	private TitleCombobox title;
	@Wire
	private CivilStateCombobox maritalStatus;
	@Wire
	private CityCombobox bornCity;
	@Wire
	private StateCombobox bornState;

	@Wire( "#cmdUpdate,#cmdDelete" )
	Button[ ] itemButtons;

	@Wire
	private Textbox fatherName;
	@Wire
	private Textbox motherName;

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
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		setCurrentClient( null );
		this.gender.addDetail( this.title );
		this.bornState.addDetail( this.bornCity );
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

	private void merge( )
	{
		Client client = getCurrentClient( );
		if ( client.getClient( ) == null ) {
			client.setClient( new Person( ) );
		}
		update( client );

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

	private void showCPF( List<UserDocument> docs )
	{
		this.cpf.setValue( "" );
		for ( UserDocument doc : docs ) {
			if ( doc.getType( ).getId( ).equals( UserDocument.typeCPF ) ) {
				this.cpf.setValue( doc.getCode( ) );
				break;
			}
		}
	}

	protected void show( Person person )
	{
		super.show( person );
		if ( person != null ) {
			this.gender.find( person.getGender( ) );
			this.title.find( person.getTitle( ) );
			this.maritalStatus.find( person.getCivilState( ) );
			this.fatherName.setValue( person.getFatherName( ) );
			this.motherName.setValue( person.getMotherName( ) );
			showCPF( person.getDocuments( ) );
			if ( person.getBornCity( ) != null ) {
				this.bornState.find( person.getBornCity( ).getState( ) );
				this.bornCity.find( person.getBornCity( ) );
			}
			getCurrentClient( ).setClient( person );
		}
		else {
			this.cpf.setValue( "" );
			this.fatherName.setValue( "" );
			this.motherName.setValue( "" );
			this.bornState.setSelectedIndex( 0 );
			this.gender.setSelectedIndex( 0 );
			this.maritalStatus.setSelectedIndex( 0 );
		}
	}

	private Client update( Client client )
	{
		if ( client == null ) {
			return null;
		}
		updatePerson( (Person) client.getClient( ) );
		client = getSession( ).addNewPerson( getCurrentCollaborator( ), client );
		int nIndex = getModel( ).indexOf( client );
		if ( nIndex >= 0 ) {
			getModel( ).set( nIndex, client );
		}
		else {
			getModel( ).add( client );
		}
		return client;
	}

	@SuppressWarnings( "unchecked" )
	private ListModelList<Client> getModel( )
	{
		Object obj = getListBox( ).getModel( );
		return (ListModelList<Client>) obj;
	}

	private void updatePerson( Person person )
	{
		if ( person == null ) {
			return;
		}
		super.update( person );
		person.setTitle( this.title.getSelectedValue( ) );
		person.setCivilState( this.maritalStatus.getSelectedValue( ) );
		person.setFatherName( this.fatherName.getValue( ) );
		person.setMotherName( this.motherName.getValue( ) );
		person.setGender( this.gender.getSelectedValue( ) );
		person.setBornCity( this.bornCity.getSelectedValue( ) );
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

	private void onUpdate( )
	{
		Client client = getListBox( ).getSelectedItem( ).getValue( );
		setCurrentClient( client );
		Person person = (Person) getSession( ).getUser( getCurrentCollaborator( ), client.getClient( ).getId( ) );
		show( person );
		getName( ).setFocus( true );
	}

	private void onNew( )
	{
		setCurrentClient( null );
		show( null );
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

	@Listen( "onClick = #cmdSubmit, #cmdCancel " )
	public void onClickSubmitCancel( Event evt )
	{
		if ( evt != null ) {
			if ( evt.getTarget( ).getId( ).equals( "cmdSubmit" ) ) {
				merge( );
			}
			evt.stopPropagation( );
			getDivList( ).setVisible( true );
			getDivData( ).setVisible( false );
		}
		if ( evt != null ) {
			logger.info( "onClickSubmitCancel: " + evt.getTarget( ).getId( ) );
			evt.stopPropagation( );
		}
		else {
			logger.warn( "onClickSubmitCancel with evt null " );
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

	@Listen( "onBlur=#cpf" )
	public void onBlur( Event evt )
	{
		String doc = this.cpf.getValue( );
		if ( SysUtils.isEmpty( doc ) ) {
			return;
		}
		Person person = (Person) getSession( ).getUser( getCurrentCollaborator( ), CPF.removeMask( doc ) );
		show( person );
		if ( person == null ) {
			this.cpf.setValue( doc );
			addDocument( CPF.removeMask( doc ), getSession( ).getDocumentType( UserDocument.typeCPF ) );
		}
		if ( evt != null ) {
			logger.info( "onBlur: " + evt.getTarget( ).getId( ) );
			evt.stopPropagation( );
		}
		else {
			logger.warn( "onBlur with evt null " );
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
