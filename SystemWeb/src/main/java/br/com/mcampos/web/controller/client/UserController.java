package br.com.mcampos.web.controller.client;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import br.com.mcampos.ejb.core.BaseSessionInterface;
import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.entity.user.Address;
import br.com.mcampos.entity.user.Client;
import br.com.mcampos.entity.user.DocumentType;
import br.com.mcampos.entity.user.Person;
import br.com.mcampos.entity.user.UserContact;
import br.com.mcampos.entity.user.UserDocument;
import br.com.mcampos.entity.user.Users;
import br.com.mcampos.web.core.BaseDBLoggedController;
import br.com.mcampos.web.core.event.IDialogEvent;
import br.com.mcampos.web.core.report.BaseReportWindow;
import br.com.mcampos.web.core.report.ReportItem;

public abstract class UserController<BEAN extends BaseSessionInterface> extends BaseDBLoggedController<BEAN> implements IDialogEvent
{
	private static final long serialVersionUID = 4025873645295022522L;
	private static final int defaultRows = 30;
	@SuppressWarnings( "unused" )
	private static final Logger logger = LoggerFactory.getLogger( UserController.class );

	@Wire( "#wndContact #lstItem" )
	private Listbox contactList;

	@Wire( "#wndDocument #lstItem" )
	private Listbox documentList;

	@Wire( "#wndAddress #lstItem" )
	private Listbox addressList;

	@Wire
	private Textbox name;

	@Wire
	private Datebox birthdate;

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
	private Button[ ] itemButtons;

	private DBPaging paging;

	private Users currentUser;

	private Client currentClient;

	protected abstract Users createEmptyEntity( );

	protected abstract List<Client> getClients( );

	protected abstract Long getCount( );

	protected abstract void onUpdate( );

	protected abstract void onNew( );

	protected abstract boolean remove( Client client );

	protected abstract Client update( Client client );

	protected abstract DocumentType getDocumentType( Integer type );

	protected Listbox getContactList( )
	{
		return this.contactList;
	}

	protected Listbox getDocumentList( )
	{
		return this.documentList;
	}

	protected Listbox getAddressList( )
	{
		return this.addressList;
	}

	protected void show( Users u )
	{
		if ( u != null )
		{
			getDocumentList( ).setModel( new ListModelList<UserDocument>( u.getDocuments( ) ) );
			getContactList( ).setModel( new ListModelList<UserContact>( u.getContacts( ) ) );
			getAddressList( ).setModel( new ListModelList<Address>( u.getAddresses( ) ) );
			this.name.setValue( u.getName( ) );
			if ( this.birthdate != null ) {
				this.birthdate.setValue( u.getBirthDate( ) );
			}
			setCurrentUser( u );
		}
		else
		{
			getDocumentList( ).setModel( new ListModelList<UserDocument>( ) );
			getContactList( ).setModel( new ListModelList<UserContact>( ) );
			getAddressList( ).setModel( new ListModelList<Address>( ) );
			this.name.setRawValue( "" );
			if ( this.birthdate != null ) {
				this.birthdate.setValue( null );
			}
			setCurrentUser( null );
		}
	}

	protected Textbox getName( )
	{
		return this.name;
	}

	protected Datebox getBirthdate( )
	{
		return this.birthdate;
	}

	private List<UserDocument> getDocuments( )
	{
		if ( this.documentList != null && this.documentList.getModel( ) != null ) {
			Object objList = this.documentList.getModel( );
			@SuppressWarnings( "unchecked" )
			ListModelList<UserDocument> l = (ListModelList<UserDocument>) objList;
			return l.getInnerList( );
		}
		else {
			return Collections.emptyList( );
		}
	}

	private List<UserContact> getContacts( )
	{
		if ( getContactList( ) != null && getContactList( ).getModel( ) != null ) {
			Object objList = this.contactList.getModel( );
			@SuppressWarnings( "unchecked" )
			ListModelList<UserContact> l = (ListModelList<UserContact>) objList;
			return l.getInnerList( );
		}
		else {
			return Collections.emptyList( );
		}
	}

	private List<Address> getAddresses( )
	{
		if ( getAddressList( ) != null && getAddressList( ).getModel( ) != null ) {
			Object objList = getAddressList( ).getModel( );
			@SuppressWarnings( "unchecked" )
			ListModelList<Address> l = (ListModelList<Address>) objList;
			return l.getInnerList( );
		}
		else {
			return Collections.emptyList( );
		}
	}

	protected void update( Users u )
	{
		u.setName( this.name.getValue( ) );
		u.setBirthDate( this.birthdate.getValue( ) );
		u.setDocuments( getDocuments( ) );
		u.setContacts( getContacts( ) );
		u.setAddresses( getAddresses( ) );
	}

	protected Users getCurrentUser( )
	{
		return this.currentUser;
	}

	protected void setCurrentUser( Users currentUser )
	{
		this.currentUser = currentUser;
	}

	protected void addDocument( String document, DocumentType type )
	{
		@SuppressWarnings( "unchecked" )
		ListModelList<UserDocument> model = ( (ListModelList<UserDocument>) ( (Object) getDocumentList( ).getModel( ) ) );
		if ( model == null ) {
			model = new ListModelList<UserDocument>( );
			getDocumentList( ).setModel( model );
		}
		UserDocument u = new UserDocument( );
		u.setType( type );
		u.setCode( document );
		model.add( u );
	}

	protected boolean validate( Users user )
	{
		return true;
	}

	@Listen( "onClick=#cmdReport" )
	public void onReport( Event evt )
	{
		Component c = createComponents( BaseReportWindow.reportTemplateName, getMainWindow( ), null );
		if ( c != null && c instanceof BaseReportWindow ) {
			BaseReportWindow w = (BaseReportWindow) c;

			w.setReports( getReports( ) );
			doModal( w, this );
		}
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	protected List<ReportItem> getReports( )
	{
		return Collections.emptyList( );
	}

	@Override
	public void onOK( Window wnd )
	{
	}

	protected Listbox getListBox( )
	{
		return this.listBox;
	}

	protected Div getDivList( )
	{
		return this.divList;
	}

	protected Paging getListobxPaging( )
	{
		return this.listboxPage;
	}

	protected Div getDivData( )
	{
		return this.divData;
	}

	protected Combobox getComboMaxRecords( )
	{
		return this.comboMaxRecords;
	}

	protected Client getCurrentClient( )
	{
		if ( this.currentClient == null ) {
			this.currentClient = new Client( getPrincipal( ).getCompanyID( ), createEmptyEntity( ) );
		}
		return this.currentClient;
	}

	protected void setCurrentClient( Client currentClient )
	{
		this.currentClient = currentClient;
	}

	protected void loadListbox( )
	{
		List<Client> clients = getClients( );
		getListBox( ).setModel( new ListModelList<Client>( clients ) );
		enableItemButtons( false );
	}

	protected void enableItemButtons( boolean enable )
	{
		if ( this.itemButtons != null ) {
			for ( Button b : this.itemButtons ) {
				b.setDisabled( !enable );
			}
		}
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

	protected void configPaging( )
	{
		if ( getListBox( ).isAutopaging( ) == false ) {
			if ( getListobxPaging( ) != null && getListBox( ).getRows( ) > 0 ) {
				getListobxPaging( ).setPageSize( getListBox( ).getRows( ) );
			}
			Long count = getCount( );
			getListobxPaging( ).setTotalSize( count != null ? count.intValue( ) : 0 );
		}
	}

	@SuppressWarnings( "unchecked" )
	protected ListModelList<Client> getModel( )
	{
		Object obj = getListBox( ).getModel( );
		return (ListModelList<Client>) obj;
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
			evt.stopPropagation( );
		}
	}

	@Listen( "onPaging = paging" )
	public void onPaging( PagingEvent evt )
	{
		int page = getListobxPaging( ).getActivePage( );
		getPaging( ).setPage( page );
		loadListbox( );
		if ( evt != null ) {
			evt.stopPropagation( );
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
			evt.stopPropagation( );
		}
	}

	@Listen( "onClick=#cmdRefresh" )
	public void onRefresh( Event evt )
	{
		loadListbox( );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	@Listen( "onClick=#cmdDelete" )
	public void onDelete( Event evt )
	{
		if ( getListBox( ) != null && getListBox( ).getSelectedItem( ) != null ) {
			Client client = getListBox( ).getSelectedItem( ).getValue( );
			if ( client != null ) {
				if ( remove( client ) ) {
					getModel( ).remove( client );
					enableItemButtons( false );
				}
			}
		}
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

	@Listen( "onClick = #cmdSubmit, #cmdCancel " )
	public void onClickSubmitCancel( Event evt )
	{
		if ( evt != null ) {
			if ( evt.getTarget( ).getId( ).equals( "cmdSubmit" ) ) {
				if ( !onOk( ) ) {
					return;
				}
			}
			evt.stopPropagation( );
			onCancel( );
		}
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

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

	protected void onCancel( )
	{
		getDivList( ).setVisible( true );
		getDivData( ).setVisible( false );
	}

}
