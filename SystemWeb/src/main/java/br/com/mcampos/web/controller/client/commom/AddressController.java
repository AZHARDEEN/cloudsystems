package br.com.mcampos.web.controller.client.commom;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.user.address.Address;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.combobox.AddressTypeCombobox;
import br.com.mcampos.web.core.combobox.CityCombobox;
import br.com.mcampos.web.core.combobox.StateCombobox;
import br.com.mcampos.web.core.event.DialogEvent;
import br.com.mcampos.web.renderer.AddressListRenderer;

public class AddressController extends BaseUserAttrListController<Address>
{
	private static final long serialVersionUID = -3981301497156139554L;
	private static final Logger logger = LoggerFactory.getLogger( AddressController.class );

	private static final String dialogPath = "/private/client/commom/address_dlg.zul";

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
		if ( aux != null ) {
			aux = SysUtils.unaccent( aux.trim( ).toUpperCase( ) );
		}
		data.setAddress( aux );
		data.setType( getAddressType( ).getSelectedValue( ) );
		data.setCity( getCity( ).getSelectedValue( ) );
		aux = getHood( ).getValue( );
		if ( aux != null ) {
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
		// getState( ).addDetail( getCity( ) );
		subscribe( );
		listbox.setItemRenderer( new AddressListRenderer( ) );
	}

	@Override
	protected boolean validate( Address data )
	{
		if ( SysUtils.isEmpty( data.getZip( ) ) ) {
			Messagebox.show( "O campo CEP está vazio. Por favor, informe um CEP válido", "Endereço", Messagebox.OK, Messagebox.ERROR );
			return false;
		}
		if ( SysUtils.isEmpty( data.getAddress( ) ) ) {
			Messagebox.show( "O campo logradouro está vazio. Por favor, informe um logradouro válido", "Endereço", Messagebox.OK, Messagebox.ERROR );
			return false;
		}
		if ( SysUtils.isEmpty( data.getDistrict( ) ) ) {
			Messagebox.show( "O campo bairro está vazio. Por favor, informe um bairro válido", "Endereço", Messagebox.OK, Messagebox.ERROR );
			return false;
		}
		return true;
	}

	@Listen( "onClick=#cmdCreateAddress" )
	public void onAddNew( MouseEvent evt )
	{
		showDialog( null, false );
		if ( evt != null )
			evt.stopPropagation( );
	}

	@Listen( "onClick=#cmdUpdateAddress" )
	public void onUpdate( MouseEvent evt )
	{
		showDialog( getSelected( ), true );
		if ( evt != null )
			evt.stopPropagation( );
	}

	private void showDialog( Address data, boolean bUpdate )
	{
		Map<String, Object> param = new HashMap<String, Object>( );
		Window main = getMainWindow( );

		param.put( "targetWindow", main );
		param.put( "entity", data );
		param.put( "isUpdate", bUpdate );
		Component c = createComponents( dialogPath, main, param );
		if ( c instanceof Window ) {
			Window w = (Window) c;
			w.doModal( );
		}
	}

	public void onDialog( DialogEvent<Window> evt )
	{
		if ( evt != null ) {
			if ( evt.getTarget( ) == getMainWindow( ) ) {
				evt.stopPropagation( );
			}
		}
	}

	private void subscribe( )
	{
		EventQueues.lookup( DialogEvent.eventName, EventQueues.DESKTOP, true ).subscribe( new EventListener<Event>( )
		{
			@SuppressWarnings( "unchecked" )
			@Override
			public void onEvent( Event evt )
			{
				if ( evt instanceof DialogEvent ) {
					DialogEvent<Window> dlgEvent = (DialogEvent<Window>) evt;
					try {
						onDialog( dlgEvent );
					}
					catch ( Exception e ) {
						logger.error( "Subscribe error", e );
					}
				}
			}
		} );
	}
}
