package br.com.mcampos.web.controller.client.commom;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.com.mcampos.entity.user.Address;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.combobox.AddressTypeCombobox;
import br.com.mcampos.web.core.combobox.CityCombobox;
import br.com.mcampos.web.core.combobox.StateCombobox;

public class AddressDialogController extends BaseUserItemDlgController<Address>
{
	private static final long serialVersionUID = -6727236216037415083L;

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
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		state.addDetail( city );
		state.load( );
		showRecord( getEntity( ) );
		setMask( zip, "cep" );
	}

	@Override
	protected boolean validate( Address data )
	{
		if( data == null )
			return false;
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

	@Override
	protected void update( Address data )
	{
		if( data == null )
			return;
		String aux;

		aux = address.getValue( );
		if( aux != null ) {
			aux = SysUtils.unaccent( aux.trim( ).toUpperCase( ) );
		}
		data.setAddress( aux );
		data.setType( addressType.getSelectedValue( ) );
		data.setCity( city.getSelectedValue( ) );
		aux = hood.getValue( );
		if( aux != null ) {
			aux = SysUtils.unaccent( aux.trim( ).toUpperCase( ) );
		}
		data.setDistrict( aux );
		data.setObs( addressComment.getValue( ) );
		data.setZip( zip.getValue( ) );

		state.storeLastUsedSelection( );
		city.storeLastUsedSelection( );
	}

	@Override
	protected void showRecord( Address data )
	{
		if( data != null ) {
			zip.setValue( data.getZip( ) );
			address.setValue( data.getAddress( ) );
			hood.setValue( data.getDistrict( ) );
			addressComment.setValue( data.getObs( ) );
			addressType.find( data.getType( ) );
			if( data.getCity( ) != null ) {
				state.find( data.getCity( ).getState( ) );
				city.find( data.getCity( ) );
			}
			else {
				if( state.getItemCount( ) > 0 ) {
					state.loadLastUsedSelection( );
					city.loadLastUsedSelection( );
				}
			}
		}
		else {
			zip.setRawValue( "" );
			address.setRawValue( "" );
			hood.setRawValue( "" );
			addressComment.setRawValue( "" );
			state.loadLastUsedSelection( );
			city.loadLastUsedSelection( );
			addressType.setSelectedIndex( 0 );
		}
	}

	@Override
	protected Address createEntity( )
	{
		return new Address( );
	}

}
