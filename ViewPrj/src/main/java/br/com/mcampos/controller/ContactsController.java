package br.com.mcampos.controller;

import java.util.List;
import java.util.ListIterator;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Toolbarbutton;

public class ContactsController extends BaseTabboxController
{

    protected static final String windowsAddressNameID = "winContacts_";

    public ContactsController()
    {
        super();
    }


    public void onCreate ()
    {
        prepareTab ( "contactType" );
    }

    public void addContactTab( )
    {
        addTab( "Novo Contato", "contactTabs", "contactPanels", 
                             "cmdRemoveContact", "cmdAddContact", windowsAddressNameID );
    }

    public void deleteContactTab( ) 
    {          
        deleteTab ( "cmdAddContact", "cmdRemoveContact" );
    }
}
