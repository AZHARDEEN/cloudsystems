package br.com.mcampos.web.controller.client.commom;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import br.com.mcampos.ejb.user.address.Address;
import br.com.mcampos.web.core.combobox.AddressTypeCombobox;
import br.com.mcampos.web.core.combobox.CityCombobox;
import br.com.mcampos.web.core.combobox.StateCombobox;

public class AddressController extends BaseUserAttrListController<Address>
{
	private static final long serialVersionUID = -3981301497156139554L;

	@Wire( "#addressList" )
	private Listbox listbox;

	@Wire
	private AddressTypeCombobox addressType;
	@Wire
	private StateCombobox state;
	@Wire
	private CityCombobox city;
	@Wire
	private Textbox zip;
	@Wire
	private Textbox address;
	@Wire
	private Textbox hood;
	@Wire
	private Textbox addressComment;

	@Override
	protected Listbox getListbox( )
	{
		return this.listbox;
	}

	@Override
	protected void showRecord( Address data )
	{
		if ( data != null ) {
			getZip( ).setValue( data.getZip( ) );
			getAddress( ).setValue( data.getAddress( ) );
			getHood( ).setValue( data.getDistrict( ) );
			getAddressComment( ).setValue( data.getObs( ) );
			getAddressType( ).find( data.getType( ) );
			getState( ).find( data.getCity( ).getState( ) );
			getCity( ).find( data.getCity( ) );
		}
		else {
			getZip( ).setRawValue( "" );
			getAddress( ).setRawValue( "" );
			getHood( ).setRawValue( "" );
			getAddressComment( ).setRawValue( "" );
			getState( ).setSelectedIndex( 0 );
			getCity( ).setSelectedIndex( 0 );
			getAddressType( ).setSelectedIndex( 0 );
		}
	}

	@Override
	protected Address createNew( )
	{
		Address a = new Address( );
		update( a );
		return a;
	}

	@Override
	protected void update( Address data )
	{
		data.setAddress( getAddress( ).getValue( ) );
		data.setType( getAddressType( ).getSelectedValue( ) );
		data.setCity( getCity( ).getSelectedValue( ) );
		data.setDistrict( getHood( ).getValue( ) );
		data.setObs( getAddressComment( ).getValue( ) );
		data.setZip( getZip( ).getValue( ) );
	}

	private AddressTypeCombobox getAddressType( )
	{
		return this.addressType;
	}

	private Textbox getZip( )
	{
		return this.zip;
	}

	private Textbox getHood( )
	{
		return this.hood;
	}

	private StateCombobox getState( )
	{
		return this.state;
	}

	private CityCombobox getCity( )
	{
		return this.city;
	}

	private Textbox getAddress( )
	{
		return this.address;
	}

	private Textbox getAddressComment( )
	{
		return this.addressComment;
	}

	@Override
	public void doAfterCompose( Div comp ) throws Exception
	{
		super.doAfterCompose( comp );
		getState( ).addDetail( getCity( ) );
	}

}
