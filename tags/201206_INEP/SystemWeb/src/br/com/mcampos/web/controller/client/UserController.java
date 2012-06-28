package br.com.mcampos.web.controller.client;

import java.util.Collections;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.user.UserContact;
import br.com.mcampos.ejb.user.Users;
import br.com.mcampos.ejb.user.address.Address;
import br.com.mcampos.ejb.user.document.UserDocument;
import br.com.mcampos.ejb.user.document.type.DocumentType;
import br.com.mcampos.web.core.BaseController;
import br.com.mcampos.web.core.BaseDBLoggedController;
import br.com.mcampos.web.core.event.IDialogEvent;
import br.com.mcampos.web.core.report.BaseReportWindow;
import br.com.mcampos.web.core.report.ReportItem;
import br.com.mcampos.web.renderer.AddressListRenderer;
import br.com.mcampos.web.renderer.UserContactListRenderer;
import br.com.mcampos.web.renderer.UserDocumentListRenderer;

public abstract class UserController<BEAN> extends BaseDBLoggedController<BEAN> implements IDialogEvent
{
	private static final long serialVersionUID = 4025873645295022522L;

	@Wire
	private Listbox contactList;

	@Wire
	private Listbox documentList;

	@Wire
	private Listbox addressList;

	@Wire
	private Textbox name;

	@Wire
	private Datebox birthdate;

	private Users currentUser;

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

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		getContactList( ).setItemRenderer( new UserContactListRenderer( ) );
		getDocumentList( ).setItemRenderer( new UserDocumentListRenderer( ) );
		getAddressList( ).setItemRenderer( new AddressListRenderer( ) );
	}

	protected void show( Users u )
	{
		if ( u != null )
		{
			getDocumentList( ).setModel( new ListModelList<UserDocument>( u.getDocuments( ) ) );
			getContactList( ).setModel( new ListModelList<UserContact>( u.getContacts( ) ) );
			getAddressList( ).setModel( new ListModelList<Address>( u.getAddresses( ) ) );
			this.name.setValue( u.getName( ) );
			this.birthdate.setValue( u.getBirthDate( ) );
			setCurrentUser( u );
		}
		else
		{
			getDocumentList( ).setModel( new ListModelList<UserDocument>( ) );
			getContactList( ).setModel( new ListModelList<UserContact>( ) );
			this.name.setRawValue( "" );
			this.birthdate.setValue( null );
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
	public void onOK( BaseController<? extends Component> wndController )
	{
		if ( wndController == this ) {

		}
	}

}
