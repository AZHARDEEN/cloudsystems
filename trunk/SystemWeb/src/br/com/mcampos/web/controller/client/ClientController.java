package br.com.mcampos.web.controller.client;

import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import br.com.mcampos.ejb.core.DBPaging;
import br.com.mcampos.ejb.user.UserContact;
import br.com.mcampos.ejb.user.address.Address;
import br.com.mcampos.ejb.user.client.Client;
import br.com.mcampos.ejb.user.client.ClientSession;
import br.com.mcampos.ejb.user.document.UserDocument;
import br.com.mcampos.ejb.user.person.Person;
import br.com.mcampos.ejb.user.person.gender.Gender;
import br.com.mcampos.ejb.user.person.title.Title;
import br.com.mcampos.sysutils.CPF;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseDBLoggedController;

public class ClientController extends BaseDBLoggedController<ClientSession>
{
	private static final long serialVersionUID = -2213603323351941998L;
	private static final int defaultRows = 30;

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
	private Combobox gender;
	@Wire
	private Listbox contactList;
	@Wire
	private Listbox documentList;
	@Wire
	protected Combobox addressType;
	@Wire
	protected Combobox state;
	@Wire
	protected Combobox city;
	@Wire
	protected Combobox country;
	@Wire
	protected Textbox zip;
	@Wire
	protected Textbox address;
	@Wire
	protected Textbox hood;
	@Wire
	protected Textbox addressComment;
	@Wire
	protected Textbox name;
	@Wire
	private Combobox title;
	@Wire
	private Combobox maritalStatus;
	@Wire
	private Datebox birthdate;
	@Wire
	private Textbox fatherName;
	@Wire
	private Textbox motherName;
	@Wire
	private Combobox bornState;
	@Wire
	private Combobox bornCity;
	@Wire
	private Combobox bornCountry;

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
		configPaging( );
		getListBox( ).setItemRenderer( new ClientPersonRenderer( ) );
		loadListbox( );
	}

	private void loadListbox( )
	{
		List<Client> clients = getSession( ).getAllPerson( getCurrentCollaborator( ), getPaging( ) );
		getListBox( ).setModel( new ListModelList<Client>( clients ) );
	}

	@Listen( "onDoubleClick = listbox" )
	public void onDoubleClick( Event evt )
	{
		evt.stopPropagation( );
		getDivList( ).setVisible( false );
		getDivData( ).setVisible( true );
		Client client = getListBox( ).getSelectedItem( ).getValue( );
		Person person = (Person) getSession( ).getUser( getCurrentCollaborator( ), client.getClient( ).getId( ) );
		showRecord( person );
	}

	@Listen( "onClick = #cmdSubmit, #cmdCancel " )
	public void onClickSubmitCancel( Event evt )
	{
		evt.stopPropagation( );
		getDivList( ).setVisible( true );
		getDivData( ).setVisible( false );
	}

	@Listen( "onPaging = paging" )
	public void onPaging( PagingEvent evt )
	{
		int page = getListobxPaging( ).getActivePage( );
		getPaging( ).setPage( page );
		loadListbox( );
		evt.stopPropagation( );
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

	@Listen( "onBlur=#cpf" )
	public void onBlur( Event evt )
	{
		String doc = this.cpf.getValue( );
		if ( SysUtils.isEmpty( doc ) ) {
			return;
		}
		Person person = (Person) getSession( ).getUser( getCurrentCollaborator( ), CPF.removeMask( doc ) );
		showRecord( person );
		if ( person == null ) {
			this.cpf.setValue( doc );
		}
		if ( evt != null ) {
			evt.stopPropagation( );
		}
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

	private void showRecord( Person person )
	{
		Date birth = new Date( );
		if ( person != null ) {
			this.name.setValue( person.getName( ) );
			if ( person.getBirthDate( ) != null ) {
				birth.setTime( person.getBirthDate( ).getTime( ) );
				this.birthdate.setValue( birth );
			}
			showAddresses( person.getAddresses( ) );
			this.documentList.setModel( new ListModelList<UserDocument>( person.getDocuments( ) ) );
			this.contactList.setModel( new ListModelList<UserContact>( person.getContacts( ) ) );
			find( this.gender, person.getGender( ) );
			if ( this.gender.getSelectedItem( ) != null ) {
				onSelectGender( null );
			}
			find( this.title, person.getTitle( ) );
			find( this.maritalStatus, person.getTitle( ) );
			this.fatherName.setValue( person.getFatherName( ) );
			this.motherName.setValue( person.getMotherName( ) );
			showCPF( person.getDocuments( ) );
			/*
			 * TODO: Find Address
			 */
		}
		else {
			this.cpf.setValue( "" );
			this.name.setValue( "" );
			this.birthdate.setValue( null );
			showAddresses( null );
			this.documentList.setModel( new ListModelList<UserDocument>( ) );
			this.contactList.setModel( new ListModelList<UserContact>( ) );
			this.fatherName.setValue( "" );
			this.motherName.setValue( "" );
		}
	}

	private void find( Combobox combo, Object item )
	{
		if ( combo == null || combo.getChildren( ).size( ) <= 0 || item == null ) {
			return;
		}
		combo.setSelectedIndex( 0 );
		for ( Component c : combo.getChildren( ) ) {
			Comboitem cbItem = (Comboitem) c;
			if ( cbItem.getValue( ).equals( item ) ) {
				combo.setSelectedItem( cbItem );
				break;
			}
		}
	}

	@Listen( "onSelect=#gender" )
	public void onSelectGender( Event evt )
	{
		if ( evt != null ) {
			evt.stopPropagation( );
		}
		Comboitem item = this.gender.getSelectedItem( );
		if ( item != null ) {
			loadTitle( (Gender) item.getValue( ) );
		}
	}

	private void loadTitle( Gender gender )
	{
		List<Title> titles = getSession( ).getTitle( gender );
		if ( this.title.getChildren( ) != null ) {
			this.title.getChildren( ).clear( );
		}
		if ( SysUtils.isEmpty( titles ) == false ) {
			for ( Title t : titles ) {
				Comboitem item = this.title.appendItem( t.getDescription( ) );
				if ( item != null ) {
					item.setValue( t );
				}
			}
			if ( this.title.getChildren( ).size( ) > 0 ) {
				this.title.setSelectedIndex( 0 );
			}
		}
	}

	protected void showAddresses( List<Address> dto )
	{
		if ( dto == null || SysUtils.isEmpty( dto ) ) {
			return;
		}
		for ( Address item : dto ) {
			find( this.addressType, item.getAddressType( ) );
			this.zip.setValue( item.getZip( ) );
			this.address.setValue( item.getAddress( ) );
			this.hood.setValue( item.getDistrict( ) );
			this.addressComment.setValue( item.getObs( ) );
			/*
			 * TODO: Find Address
			 */
			break; /* Just now, There must be only One!!!! Teoria do highlander */
		}
	}

	@Listen( "onClick=#cmdCreate" )
	public void onNewPerson( Event evt )
	{
		getDivList( ).setVisible( false );
		getDivData( ).setVisible( true );
		showRecord( null );
		if ( evt != null ) {
			evt.stopPropagation( );
		}
	}

}
