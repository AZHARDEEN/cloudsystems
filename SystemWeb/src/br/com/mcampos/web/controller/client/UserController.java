package br.com.mcampos.web.controller.client;

import java.util.Collections;
import java.util.List;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.user.UserContact;
import br.com.mcampos.ejb.user.Users;
import br.com.mcampos.ejb.user.document.UserDocument;
import br.com.mcampos.ejb.user.document.type.DocumentType;
import br.com.mcampos.web.core.BaseDBLoggedController;
import br.com.mcampos.web.renderer.UserContactListRenderer;
import br.com.mcampos.web.renderer.UserDocumentListRenderer;

public abstract class UserController<BEAN> extends BaseDBLoggedController<BEAN>
{
	private static final long serialVersionUID = 4025873645295022522L;

	@Wire
	private Listbox contactList;

	@Wire
	private Listbox documentList;

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

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		getContactList( ).setItemRenderer( new UserContactListRenderer( ) );
		getDocumentList( ).setItemRenderer( new UserDocumentListRenderer( ) );
	}

	protected void show( Users u )
	{
		if ( u != null )
		{
			getDocumentList( ).setModel( new ListModelList<UserDocument>( u.getDocuments( ) ) );
			getContactList( ).setModel( new ListModelList<UserContact>( u.getContacts( ) ) );
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

	protected void update( Users u )
	{
		u.setName( this.name.getValue( ) );
		u.setBirthDate( this.birthdate.getValue( ) );
		u.setDocuments( getDocuments( ) );
		u.setContacts( getContacts( ) );
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
}