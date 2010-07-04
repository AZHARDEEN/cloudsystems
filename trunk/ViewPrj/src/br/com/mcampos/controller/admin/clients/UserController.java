package br.com.mcampos.controller.admin.clients;


import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.address.AddressDTO;
import br.com.mcampos.dto.address.AddressTypeDTO;
import br.com.mcampos.dto.address.CityDTO;
import br.com.mcampos.dto.address.CountryDTO;
import br.com.mcampos.dto.address.StateDTO;
import br.com.mcampos.dto.user.UserContactDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.dto.user.attributes.ContactTypeDTO;
import br.com.mcampos.dto.user.attributes.DocumentTypeDTO;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.util.MultilineMessageBox;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;


public abstract class UserController extends LoggedBaseController
{
    private Listbox contactList;
    private Listbox documentList;


    protected Combobox addressType;
    protected Combobox state;
    protected Combobox city;
    protected Combobox country;
    protected Textbox zip;
    protected Textbox address;
    protected Textbox hood;
    protected Textbox addressComment;
    private Button cmdSubmit;


    protected abstract List<StateDTO> getStates( CountryDTO dto );

    protected abstract List<CityDTO> getCities( StateDTO dto );


    public UserController( char c )
    {
        super( c );
    }

    public UserController()
    {
        super();
    }


    protected abstract Boolean persist() throws ApplicationException;


    protected void preparePage()
    {
    }


    public Combobox getAddressType()
    {
        return addressType;
    }


    public Combobox getState()
    {
        return state;
    }


    public Combobox getCity()
    {
        return city;
    }


    protected boolean validateAddress()
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


    protected void showErrorMessage( String msg )
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

    protected void addAddresses( UserDTO user )
    {
        user.getAddressList().clear();
        if ( getCity().getSelectedItem() != null ) {
            AddressDTO dto = new AddressDTO();
            dto.setAddressType( ( AddressTypeDTO )getAddressType().getSelectedItem().getValue() );
            if ( getCity().getSelectedItem() != null )
                dto.setCity( ( CityDTO )getCity().getSelectedItem().getValue() );
            else
                dto.setCity( null );
            dto.setAddress( getAddress().getValue() );
            dto.setDistrict( getHood().getValue() );
            dto.setComment( getAddressComment().getValue() );
            dto.setZip( getZip().getValue() );
            user.add( dto );
        }
    }


    protected void showAddresses( List<AddressDTO> dto )
    {
        for ( AddressDTO item : dto ) {
            findAddresTypeComboitem( item.getAddressType() );
            zip.setValue( item.getZip() );
            address.setValue( item.getAddress() );
            hood.setValue( item.getDistrict() );
            addressComment.setValue( item.getComment() );
            findCountryComboitem( item.getCity().getState().getRegion().getCountry(), country, getState() );
            findStateComboitem( item.getCity().getState(), getState(), getCity() );
            findCityComboitem( item.getCity(), getCity() );
            break; /*Just now, There must be only One!!!! Teoria do highlander*/
        }
    }


    @SuppressWarnings( { "unchecked", "Unnecessary" } )
    protected void findAddresTypeComboitem( AddressTypeDTO targetAddressType )
    {
        List<Comboitem> comboList;
        AddressTypeDTO item;

        comboList = ( List<Comboitem> )getAddressType().getItems();
        for ( Comboitem comboItem : comboList ) {
            item = ( AddressTypeDTO )comboItem.getValue();
            if ( item.compareTo( targetAddressType ) == 0 ) {
                getAddressType().setSelectedItem( comboItem );
                break;
            }
        }
    }

    @SuppressWarnings( "unchecked" )
    protected void findStateComboitem( StateDTO targetDTO, Combobox comboState, Combobox comboCity )
    {
        List<Comboitem> comboList;
        StateDTO item;

        if ( targetDTO == null || comboState == null )
            return;

        comboList = ( List<Comboitem> )comboState.getItems();
        for ( Comboitem comboItem : comboList ) {
            item = ( StateDTO )comboItem.getValue();
            if ( item.compareTo( targetDTO ) == 0 ) {
                comboState.setSelectedItem( comboItem );
                if ( comboCity != null )
                    loadCombobox( comboCity, getCities( item ) );
                break;
            }
        }
    }

    protected void findCityComboitem( CityDTO targetDTO, Combobox comboCity )
    {
        List<Comboitem> comboList;
        CityDTO item;

        comboList = ( List<Comboitem> )comboCity.getItems();
        for ( Comboitem comboItem : comboList ) {
            item = ( CityDTO )comboItem.getValue();
            if ( item.compareTo( targetDTO ) == 0 ) {
                comboCity.setSelectedItem( comboItem );
                break;
            }
        }
    }

    protected void findCountryComboitem( CountryDTO targetDTO, Combobox comboCountry, Combobox comboState )
    {
        List<Comboitem> comboList;
        CountryDTO item;

        if ( targetDTO == null || comboCountry == null )
            return;
        comboList = ( List<Comboitem> )comboCountry.getItems();
        for ( Comboitem comboItem : comboList ) {
            item = ( CountryDTO )comboItem.getValue();
            if ( item.compareTo( targetDTO ) == 0 ) {
                comboCountry.setSelectedItem( comboItem );
                if ( comboState != null )
                    loadCombobox( comboState, getStates( targetDTO ) );
                break;
            }
        }
    }

    protected Boolean validate()
    {

        if ( validateAddress() == false ) {
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


    protected void addDocuments( UserDTO user )
    {
        UserDocumentDTO dto;


        user.getDocumentList().clear();
        for ( Listitem item : ( List<Listitem> )documentList.getItems() ) {
            String doc;
            String comment;

            List cells = item.getChildren();
            if ( cells != null && cells.size() > 1 ) {
                doc = ( ( Listcell )cells.get( 1 ) ).getLabel();
                comment = ( ( Listcell )cells.get( 2 ) ).getLabel();
                dto = new UserDocumentDTO();
                dto.setDocumentType( ( DocumentTypeDTO )item.getValue() );
                dto.setCode( doc );
                dto.setAdditionalInfo( comment );
                user.add( dto );
            }
        }
    }


    protected void addContacts( UserDTO user )
    {
        UserContactDTO dto;

        user.getContactList().clear();
        for ( Listitem item : ( List<Listitem> )contactList.getItems() ) {
            String doc;
            String comment;

            List cells = item.getChildren();
            if ( cells != null && cells.size() > 1 ) {
                doc = ( ( Listcell )cells.get( 1 ) ).getLabel();
                comment = ( ( Listcell )cells.get( 2 ) ).getLabel();
                dto = new UserContactDTO( ( ContactTypeDTO )item.getValue(), doc, comment );
                user.add( dto );
            }
        }
    }

    protected void showContacts( List<UserContactDTO> dto )
    {
        contactList.getItems().clear();
        for ( UserContactDTO item : dto ) {
            insertContact( item.getDescription(), item.getComment(), item.getContactType() );
        }
    }


    protected void insertContact( String description, String comment, Object contactType )
    {
        Listitem item;
        ContactTypeDTO dto = ( ContactTypeDTO )contactType;

        if ( dto != null ) {
            item = contactList.appendItem( dto.getDisplayName(), dto.getId().toString() );
            item.setValue( dto );
            item.appendChild( new Listcell( description ) );
            item.appendChild( new Listcell( comment ) );
        }
    }

    protected void insertDocument( String description, String comment, Object obj )
    {
        Listitem item;
        DocumentTypeDTO dto = ( DocumentTypeDTO )obj;

        if ( dto != null ) {
            item = documentList.appendItem( dto.getDisplayName(), dto.getId().toString() );
            item.setValue( dto );
            item.appendChild( new Listcell( description ) );
            item.appendChild( new Listcell( comment ) );
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

    public void onClick$cmdSubmit()
    {
        try {
            if ( persist() == true )
                removeMe();
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage() );
        }

    }
}
