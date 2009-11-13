package br.com.mcampos.controller.admin.clients;

import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.address.AddressDTO;
import br.com.mcampos.dto.address.AddressTypeDTO;
import br.com.mcampos.dto.address.CityDTO;
import br.com.mcampos.dto.address.StateDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.dto.user.UserContactDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.dto.user.attributes.CivilStateDTO;
import br.com.mcampos.dto.user.attributes.ContactTypeDTO;
import br.com.mcampos.dto.user.attributes.DocumentTypeDTO;
import br.com.mcampos.dto.user.attributes.GenderDTO;
import br.com.mcampos.dto.user.attributes.TitleDTO;
import br.com.mcampos.dto.user.login.LoginDTO;
import br.com.mcampos.util.MultilineMessageBox;
import br.com.mcampos.util.business.RegisterLocator;
import br.com.mcampos.util.business.SimpleTableLoaderLocator;
import br.com.mcampos.util.business.UsersLocator;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public abstract class UserClientController extends LoggedBaseController
{
    protected RegisterLocator loaderLocator;
    protected UsersLocator userLocator;
    protected SimpleTableLoaderLocator simpleLoader;
    private Listbox contactList;
    private Listbox documentList;
    

    private Combobox addressType;
    private Combobox state;
    private Combobox city;
    private Textbox zip;
    private Textbox address;
    private Textbox hood;
    private Textbox addressComment;
    private Button cmdSubmit;

    
    public UserClientController( char c )
    {
        super( c );
    }

    public UserClientController()
    {
        super();
    }
    
    
    protected abstract Boolean persist ();
    

    protected void setLoaderLocator( RegisterLocator loaderLocator )
    {
        this.loaderLocator = loaderLocator;
    }

    protected RegisterLocator getLoaderLocator()
    {
        if ( loaderLocator == null )
            loaderLocator = new RegisterLocator ();
        return loaderLocator;
    }

    protected void preparePage ()
    {
		if ( addressType != null )
			getLoaderLocator().loadAddressType( addressType );
		if ( state != null ) {
			getLoaderLocator().loadState( state );
			if ( state.getSelectedIndex() > 0 )
				onSelect$state();
		}
    }


    public void onSelect$state ()
    {
        Comboitem item;
        
        item = state.getSelectedItem();
        if ( item == null )
            return;
        StateDTO dto = ( StateDTO ) item.getValue();
        if ( dto != null ) {
            getLoaderLocator().loadCity( city, dto.getCountryId(), dto.getId() );
        }
    }

    public void setAddressType( Combobox addressType )
    {
        this.addressType = addressType;
    }

    public Combobox getAddressType()
    {
        return addressType;
    }

    public void setState( Combobox state )
    {
        this.state = state;
    }

    public Combobox getState()
    {
        return state;
    }

    public void setCity( Combobox city )
    {
        this.city = city;
    }

    public Combobox getCity()
    {
        return city;
    }


    protected boolean validateAddress ( )
    {
        String sValue;
        
        if ( getAddressType().getSelectedItem() == null ) {
            showErrorMessage( "Por favor, informe um tipo de endereço" );
            getAddressType().setFocus( true );
            return false;
        }
        sValue = address.getValue();
        if ( sValue.isEmpty() ) {
            showErrorMessage( "Por favor, informe o seu endereço" );
            address.setFocus( true );
            return false;
        }
        sValue = zip.getValue();
        if ( sValue.isEmpty() ) {
            showErrorMessage( "Por favor, informe o seu CEP" );
            zip.setFocus( true );
            return false;
        }
        if ( getAddressType().getSelectedItem() == null ) {
            showErrorMessage( "Por favor, informe a sua cidade" );
            getAddressType().setFocus( true );
            return false;
        }
        return true;        
    }


    protected void showErrorMessage ( String msg )
    {
        try {
            MultilineMessageBox.doSetTemplate();
            MultilineMessageBox.show( msg, "Validação", Messagebox.OK, Messagebox.ERROR, true );
        }
        catch ( Exception e ) {
            e = null;
        }
    }

    public void setZip( Textbox zip )
    {
        this.zip = zip;
    }

    public Textbox getZip()
    {
        return zip;
    }

    public void setAddress( Textbox address )
    {
        this.address = address;
    }

    public Textbox getAddress()
    {
        return address;
    }

    public void setHood( Textbox hood )
    {
        this.hood = hood;
    }

    public Textbox getHood()
    {
        return hood;
    }

    public void setAddressComment( Textbox addressComment )
    {
        this.addressComment = addressComment;
    }

    public Textbox getAddressComment()
    {
        return addressComment;
    }

    protected void addAddresses ( UserDTO user )
    {
        user.getAddressList().clear();
        AddressDTO dto = new AddressDTO ( );
        dto.setUser( user );
        dto.setAddressType( (AddressTypeDTO )getAddressType().getSelectedItem().getValue() );
        dto.setCity( (CityDTO ) getCity().getSelectedItem().getValue() );
        dto.setAddress(  getAddress().getValue() );
        dto.setDistrict( getHood().getValue() );
        dto.setComment( getAddressComment().getValue() );
        dto.setZip( getZip().getValue() );
        user.add( dto );
    }


    protected void showAddresses( List<AddressDTO> dto )
    {
        
        for ( AddressDTO item : dto )
        {
            findAddresTypeComboitem( item.getAddressType() );
            zip.setValue( item.getZip() );
            address.setValue( item.getAddress() );
            hood.setValue( item.getDistrict() );
            addressComment.setValue( item.getComment() );
            findStateComboitem( item.getCity().getState(), getState(), getCity() );
            findCityComboitem( item.getCity(), getCity() ); 
            break; /*Just now, There must be only One!!!! Teoria do highlander*/
        }
    }


    @SuppressWarnings({ "unchecked", "Unnecessary" } )
    protected void findAddresTypeComboitem ( AddressTypeDTO targetAddressType )
    {
        List<Comboitem> comboList;
        AddressTypeDTO item;
        
        comboList = ( List<Comboitem> ) getAddressType().getItems();
        for ( Comboitem comboItem : comboList )
        {
            item = ( AddressTypeDTO ) comboItem.getValue();
            if ( item.compareTo( targetAddressType ) == 0 ) {
                getAddressType().setSelectedItem( comboItem );
                break;
            }
        }
    }

    @SuppressWarnings( "unchecked" )
    protected void findStateComboitem ( StateDTO targetDTO, Combobox comboState, Combobox comboCity )
    {
        List<Comboitem> comboList;
        StateDTO item;
        
        if ( targetDTO == null || comboState == null || comboCity == null )
            return;
        
        comboList = ( List<Comboitem> ) comboState.getItems();
        for ( Comboitem comboItem : comboList )
        {
            item = ( StateDTO ) comboItem.getValue();
            if ( item.compareTo( targetDTO ) == 0 ) {
                comboState.setSelectedItem( comboItem );
                getLoaderLocator().loadCity( comboCity, item.getCountryId(), item.getId() );
                break;
            }
        }
    }

    protected void findCityComboitem ( CityDTO targetDTO, Combobox comboCity )
    {
        List<Comboitem> comboList;
        CityDTO item;
        
        comboList = ( List<Comboitem> ) comboCity.getItems();
        for ( Comboitem comboItem : comboList )
        {
            item = ( CityDTO ) comboItem.getValue();
            if ( item.compareTo( targetDTO ) == 0 ) {
                comboCity.setSelectedItem( comboItem );
                break;
            }
        }
    }

    protected Boolean validate ()
    {
        if ( validateAddress () == false ) {
            return false;
        }
        return true;
    }

    public void setCmdSubmit( Button cmdSubmit )
    {
        this.cmdSubmit = cmdSubmit;
    }

    public Button getCmdSubmit()
    {
        return cmdSubmit;
    }


    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        preparePage();
    }


    public void setUserLocator( UsersLocator userLocator )
    {
        this.userLocator = userLocator;
    }

    public UsersLocator getUserLocator()
    {
        if ( userLocator == null )
            userLocator = new UsersLocator ();
        return userLocator;
    }


    public void setSimpleLoader( SimpleTableLoaderLocator simpleLoader )
    {
        this.simpleLoader = simpleLoader;
    }

    public SimpleTableLoaderLocator getSimpleLoader()
    {
        if ( simpleLoader == null )
            simpleLoader = new SimpleTableLoaderLocator();
        return simpleLoader;
    }
    
    
    protected void addDocuments ( UserDTO user )
    {
        UserDocumentDTO dto;
        
        
        user.getDocumentList().clear();
        for ( Listitem item: (List<Listitem>) documentList.getItems() ) 
        {
            String doc;
            String comment;
            
            List cells = item.getChildren();
            if ( cells != null && cells.size() > 1 ) {
                doc = ( (Listcell) cells.get(1)).getLabel();
                comment = ( (Listcell) cells.get(2)).getLabel();
                dto = new  UserDocumentDTO ( );
                dto.setDocumentType( (DocumentTypeDTO) item.getValue()  );
                dto.setCode( doc );
                dto.setAdditionalInfo( comment );
                user.add( dto );
            }
        }
    }
    

    protected void addContacts ( UserDTO user )
    {
        UserContactDTO dto;
        
        user.getContactList().clear(); 
        for ( Listitem item: (List<Listitem>) contactList.getItems() ) 
        {
            String doc;
            String comment;
            
            List cells = item.getChildren();
            if ( cells != null && cells.size() > 1 ) {
                doc = ( (Listcell) cells.get(1)).getLabel();
                comment = ( (Listcell) cells.get(2)).getLabel();
                dto = new  UserContactDTO ( (ContactTypeDTO) item.getValue(), doc, comment );
                user.add( dto );
            }
        }
    }

    protected void showContacts( List<UserContactDTO> dto )
    {
        contactList.getItems().clear ();
        for ( UserContactDTO item : dto )
        {
            insertContact( item.getDescription(), item.getComment(), item.getContactType() );
        }
    }


    protected void insertContact ( String description, String comment, Object contactType )
    {
        Listitem item;
        ContactTypeDTO dto = ( ContactTypeDTO ) contactType;
        
        if ( dto != null ) {
            item = contactList.appendItem( dto.getDisplayName(), dto.getId().toString() );
            item.setValue( dto );
            item.appendChild( new Listcell ( description ) );
            item.appendChild( new Listcell ( comment ) );
        }
    }
    
    protected void insertDocument( String description, String comment, Object obj )
    {
        Listitem item;
        DocumentTypeDTO dto = ( DocumentTypeDTO ) obj;
        
        if ( dto != null ) {
            item = documentList.appendItem( dto.getDisplayName(), dto.getId().toString() );
            item.setValue( dto );
            item.appendChild( new Listcell ( description ) );
            item.appendChild( new Listcell ( comment ) );
        }
    }


    public void setContactList( Listbox contactList )
    {
        this.contactList = contactList;
    }

    public Listbox getContactList()
    {
        return contactList;
    }

    public void setDocumentList( Listbox documentList )
    {
        this.documentList = documentList;
    }

    public Listbox getDocumentList()
    {
        return documentList;
    }

    public void onClick$cmdSubmit ( )
    {
        if ( validate () == false )
            return;
        if ( persist() == true )
            removeMe();
        
    }
}
