package br.com.mcampos.web.controller.client.commom;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import br.com.mcampos.ejb.user.address.Address;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.combobox.AddressTypeCombobox;
import br.com.mcampos.web.core.combobox.CityCombobox;
import br.com.mcampos.web.core.combobox.StateCombobox;
import br.com.mcampos.web.renderer.AddressListRenderer;

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

	@Wire
	private Label lblType;
	@Wire
	private Label lblCEP;
	@Wire
	private Label lblAddress;
	@Wire
	private Label lblHood;
	@Wire
	private Label lblCity;
	@Wire
	private Label lblComments;

	@Override
	protected Listbox getListbox( )
	{
		return listbox;
	}

	@Override
	protected void showRecord( Address data )
	{
		if( data != null ) {
			getZip( ).setValue( data.getZip( ) );
			getAddress( ).setValue( data.getAddress( ) );
			getHood( ).setValue( data.getDistrict( ) );
			getAddressComment( ).setValue( data.getObs( ) );
			getAddressType( ).find( data.getType( ) );
			getState( ).find( data.getCity( ).getState( ) );
			getCity( ).find( data.getCity( ) );

			lblType.setValue( data.getType( ).toString( ) );
			lblCEP.setValue( SysUtils.formatCEP( data.getZip( ) ) );
			lblAddress.setValue( data.getAddress( ) );
			lblCity.setValue( data.getCity( ).toString( ) + " - " + data.getCity( ).getState( ).toString( ) );
			lblHood.setValue( data.getDistrict( ) );
			lblComments.setValue( data.getObs( ) );
		}
		else {
			getZip( ).setValue( "" );
			getAddress( ).setValue( "" );
			getHood( ).setValue( "" );
			getAddressComment( ).setValue( "" );
			// getState( ).setSelectedIndex( 0 );
			// getCity( ).setSelectedIndex( 0 );
			getAddressType( ).setSelectedIndex( 0 );
			getAddressType( ).setFocus( true );

			lblType.setValue( "" );
			lblCEP.setValue( "" );
			lblAddress.setValue( "" );
			lblCity.setValue( "" );
			lblHood.setValue( "" );
			lblComments.setValue( "" );
		}
		setMask( zip, "cep" );
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
		String aux;

		aux = getAddress( ).getValue( );
		if( aux != null ) {
			aux = SysUtils.unaccent( aux.trim( ).toUpperCase( ) );
		}
		data.setAddress( aux );
		data.setType( getAddressType( ).getSelectedValue( ) );
		data.setCity( getCity( ).getSelectedValue( ) );
		aux = getHood( ).getValue( );
		if( aux != null ) {
			aux = SysUtils.unaccent( aux.trim( ).toUpperCase( ) );
		}
		data.setDistrict( aux );
		data.setObs( getAddressComment( ).getValue( ) );
		data.setZip( getZip( ).getValue( ) );
	}

	private AddressTypeCombobox getAddressType( )
	{
		return addressType;
	}

	private Textbox getZip( )
	{
		return zip;
	}

	private Textbox getHood( )
	{
		return hood;
	}

	private StateCombobox getState( )
	{
		return state;
	}

	private CityCombobox getCity( )
	{
		return city;
	}

	private Textbox getAddress( )
	{
		return address;
	}

	private Textbox getAddressComment( )
	{
		return addressComment;
	}

	@Override
	public void doAfterCompose( Component comp ) throws Exception
	{
		super.doAfterCompose( comp );
		getState( ).addDetail( getCity( ) );
		listbox.setItemRenderer( new AddressListRenderer( ) );
	}

	@Override
	protected boolean validate( Address data )
	{
		if( SysUtils.isEmpty( data.getZip( ) ) ) {
			Messagebox.show( "O campo CEP está vazio. Por favor, informe um CEP válido", "Endereço", Messagebox.OK, Messagebox.ERROR );
			return false;
		}
		if( SysUtils.isEmpty( data.getAddress( ) ) ) {
			Messagebox.show( "O campo logradouro está vazio. Por favor, informe um logradouro válido", "Endereço", Messagebox.OK, Messagebox.ERROR );
			return false;
		}
		if( SysUtils.isEmpty( data.getDistrict( ) ) ) {
			Messagebox.show( "O campo bairro está vazio. Por favor, informe um bairro válido", "Endereço", Messagebox.OK, Messagebox.ERROR );
			return false;
		}
		return true;
	}
}
