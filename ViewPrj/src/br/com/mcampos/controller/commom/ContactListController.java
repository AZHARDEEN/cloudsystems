package br.com.mcampos.controller.commom;


import br.com.mcampos.controller.core.BaseController;
import br.com.mcampos.dto.user.attributes.ContactTypeDTO;
import br.com.mcampos.ejb.cloudsystem.user.attribute.contacttype.facade.ContactTypeFacade;

import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;


public class ContactListController extends BaseController
{
    private ContactTypeFacade session;

    private Listbox contactList;
    private Combobox contactType;
    private Textbox contactId;
    private Textbox contactComent;

    private Listheader headerContactType;
    private Listheader headerContactDescription;
    private Listheader headerContactObs;
    private Label labelContactType;
    private Label labelContactDescription;
    private Label labelContactObs;
    private Button addContact;
    private Button updateContact;
    private Button removeContact;

    public ContactListController( char c )
    {
        super( c );
    }

    public ContactListController()
    {
        super();
    }


    protected void insertContact( String description, String comment, Object contactType )
    {
        Listitem item;
        ContactTypeDTO dto = ( ContactTypeDTO )contactType;

        if ( dto != null ) {
            item = getContactList().appendItem( dto.getDisplayName(), dto.getId().toString() );
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


    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        setLabels();
        loadCombobox( contactType, getSession().getAll() );
    }

    private void setLabels()
    {
        setLabel( headerContactType );
        setLabel( headerContactDescription );
        setLabel( headerContactObs );
        setLabel( labelContactType );
        setLabel( labelContactDescription );
        setLabel( labelContactObs );
        setLabel( addContact );
        setLabel( updateContact );
        setLabel( removeContact );

    }

    public Boolean validateContact()
    {
        String id;
        if ( contactType.getSelectedIndex() < 0 ) {
            contactType.focus();
            return false;
        }
        id = contactId.getValue();
        if ( id.isEmpty() ) {
            contactType.focus();
            return false;
        }
        return true;
    }


    public void onClick$addContact()
    {
        if ( validateContact() ) {
            Comboitem type = contactType.getSelectedItem();

            if ( type != null ) {
                ContactTypeDTO dto = ( ContactTypeDTO )type.getValue();
                if ( dto != null ) {
                    insertContact( contactId.getValue(), contactComent.getValue(), dto );
                    contactId.setRawValue( "" );
                    contactComent.setRawValue( "" );
                }
            }
        }
    }


    public void onClick$updateContact()
    {
        Listitem selectedItem;
        List<Listcell> cells;
        Comboitem contactTypeItem;


        if ( contactList.getSelectedIndex() < 0 ) {
            if ( contactList.getItems().size() > 0 )
                contactList.focus();
            return;
        }
        selectedItem = contactList.getSelectedItem();
        contactTypeItem = contactType.getSelectedItem();

        if ( selectedItem != null && contactType != null ) {
            int nIndex = 0;

            cells = ( List<Listcell> )selectedItem.getChildren();
            ContactTypeDTO dto = ( ContactTypeDTO )contactTypeItem.getValue();

            selectedItem.setValue( dto );
            for ( Listcell cell : cells ) {
                switch ( nIndex ) {
                case 0:
                    cell.setLabel( contactType.getValue() );
                    break;
                case 1:
                    cell.setLabel( contactId.getValue() );
                    break;
                case 2:
                    cell.setLabel( contactComent.getValue() );
                    break;
                }
                nIndex++;
            }
        }
    }

    public void onClick$removeContact()
    {
        Integer nIndex;

        nIndex = contactList.getSelectedIndex();
        if ( nIndex < 0 ) {
            if ( contactList.getItems().size() > 0 )
                contactList.focus();
        }
        else {
            contactList.removeItemAt( nIndex );
            if ( contactList.getItems().size() > 0 ) {
                if ( nIndex == contactList.getItems().size() )
                    nIndex--;
                setContactListSelectedIndex( nIndex );
            }
        }
    }


    protected void setContactListSelectedIndex( Integer nIndex )
    {
        if ( nIndex >= 0 && nIndex < contactList.getItems().size() ) {
            contactList.setSelectedIndex( nIndex );
            onSelect$contactList();
        }
    }


    public void onSelect$contactList()
    {
        Listitem selectedItem;
        List cells;

        selectedItem = contactList.getSelectedItem();
        if ( selectedItem != null ) {
            cells = selectedItem.getChildren();
            Iterator iterator = cells.iterator();

            if ( iterator.hasNext() )
                contactType.setValue( ( ( Listcell )iterator.next() ).getLabel() );
            if ( iterator.hasNext() )
                contactId.setValue( ( ( Listcell )iterator.next() ).getLabel() );
            if ( iterator.hasNext() )
                contactComent.setValue( ( ( Listcell )iterator.next() ).getLabel() );
        }
    }

    public ContactTypeFacade getSession()
    {
        if ( session == null )
            session = ( ContactTypeFacade )getRemoteSession( ContactTypeFacade.class );
        return session;
    }
}
