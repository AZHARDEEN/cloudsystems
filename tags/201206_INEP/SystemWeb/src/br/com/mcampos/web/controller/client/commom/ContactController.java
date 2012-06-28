package br.com.mcampos.web.controller.client.commom;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import br.com.mcampos.ejb.user.UserContact;
import br.com.mcampos.web.core.combobox.ContactTypeCombobox;

public class ContactController extends BaseUserAttrListController<UserContact>
{
	private static final long serialVersionUID = 6544906635795366755L;

	@Wire( "#contactList" )
	private Listbox listbox;

	@Wire
	private ContactTypeCombobox contactType;
	@Wire
	private Textbox contactId;
	@Wire
	private Textbox contactComent;

	@Override
	protected Listbox getListbox( )
	{
		return this.listbox;
	}

	@Override
	protected void showRecord( UserContact data )
	{
		if ( data != null ) {
			getContactType( ).find( data.getType( ) );
			getContactId( ).setValue( data.getDescription( ) );
			getContactComent( ).setValue( data.getObs( ) );
		}
	}

	@Override
	protected UserContact createNew( )
	{
		UserContact c = new UserContact( );
		update( c );
		return c;
	}

	protected ContactTypeCombobox getContactType( )
	{
		return this.contactType;
	}

	protected Textbox getContactId( )
	{
		return this.contactId;
	}

	protected Textbox getContactComent( )
	{
		return this.contactComent;
	}

	@Override
	protected void update( UserContact c )
	{
		c.setType( getContactType( ).getSelectedValue( ) );
		c.setDescription( getContactId( ).getValue( ) );
		c.setObs( getContactComent( ).getValue( ) );
	}
}
