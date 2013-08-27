package br.com.mcampos.web.controller.client.commom;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import br.com.mcampos.ejb.user.UserContact;
import br.com.mcampos.ejb.user.contact.type.ContactType;
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
		return listbox;
	}

	@Override
	protected void showRecord( UserContact data )
	{
		if( data != null ) {
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
		return contactType;
	}

	protected Textbox getContactId( )
	{
		return contactId;
	}

	protected Textbox getContactComent( )
	{
		return contactComent;
	}

	@Override
	protected void update( UserContact c )
	{
		c.setType( getContactType( ).getSelectedValue( ) );
		c.setDescription( getContactId( ).getValue( ) );
		c.setObs( getContactComent( ).getValue( ) );
	}

	@Override
	@Listen( "onSelect=#contactType" )
	public void onSelect( Event evt )
	{
		Object obj = getContactType( ).getSelectedItem( ).getValue( );
		if( obj != null || obj instanceof ContactType ) {
			ContactType c = (ContactType) obj;
			if( c.getId( ).compareTo( 3 ) < 0 ) {
				setMask( contactType, "phone" );
			}
			else {
				setMask( contactType, null );
			}
		}
		if( evt != null )
			evt.stopPropagation( );
	}

	@Override
	protected boolean validate( UserContact data )
	{
		// TODO Auto-generated method stub
		return false;
	}
}
