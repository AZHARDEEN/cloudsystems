package br.com.mcampos.web.controller.client.commom;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.user.UserContact;
import br.com.mcampos.ejb.user.contact.type.ContactType;
import br.com.mcampos.web.core.combobox.ContactTypeCombobox;

public class ContactDialogController extends BaseUserItemDlgController<UserContact>
{
	private static final long serialVersionUID = 7270335543787468962L;

	@Wire
	private ContactTypeCombobox contactType;
	@Wire
	private Textbox contactId;
	@Wire
	private Textbox contactComent;

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

	@Listen( "onSelect=#contactType" )
	public void onSelect( Event evt )
	{
		ContactType type = contactType.getSelectedValue( );
		setMask( type );
		if( evt != null ) {
			evt.stopPropagation( );
		}
	}

	private void setMask( ContactType type )
	{
		if( type != null ) {
			setMask( getContactId( ), type.getMask( ) );
		}
	}

	@Override
	protected void showRecord( UserContact data )
	{
		if( data != null ) {
			getContactType( ).find( data.getType( ) );
			setMask( data.getType( ) );
			getContactId( ).setValue( data.getDescription( ) );
			getContactComent( ).setValue( data.getObs( ) );
		}
		else {
			if( getContactType( ).getItemCount( ) > 0 )
				getContactType( ).setSelectedIndex( 0 );

			getContactId( ).setValue( "" );
			getContactComent( ).setValue( "" );
		}
	}

	@Override
	protected void update( UserContact c )
	{
		c.setType( getContactType( ).getSelectedValue( ) );
		c.setDescription( getContactId( ).getValue( ) );
		c.setObs( getContactComent( ).getValue( ) );
	}

	@Override
	protected boolean validate( UserContact data )
	{
		return true;
	}

	@Override
	protected UserContact createEntity( )
	{
		return new UserContact( );
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		showRecord( getEntity( ) );
	}

}
