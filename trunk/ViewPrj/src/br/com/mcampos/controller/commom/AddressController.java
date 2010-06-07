package br.com.mcampos.controller.commom;


import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.ejb.cloudsystem.user.address.facade.AddressFacade;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;


public class AddressController extends LoggedBaseController
{
    private AddressFacade session;

    private Label labelAddressType;
    private Label labelAddressZipCode;
    private Label labelAddress;
    private Label labelAddressDistrict;
    private Label labelAddressState;
    private Label labelAddressCity;
    private Label labelAddressObs;

    private Combobox addressType;
    private Combobox state;
    private Combobox country;

    public AddressController()
    {
        super();
    }

    public AddressController( char c )
    {
        super( c );
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        setLabel( labelAddressType );
        setLabel( labelAddressZipCode );
        setLabel( labelAddress );
        setLabel( labelAddressDistrict );
        setLabel( labelAddressState );
        setLabel( labelAddressCity );
        setLabel( labelAddressObs );
        loadCombobox( addressType, getSession().getTypes() );
        loadCombobox( country, getSession().getCountries() );
    }

    private AddressFacade getSession()
    {
        if ( session == null )
            session = ( AddressFacade )getRemoteSession( AddressFacade.class );
        return session;
    }
}
