package br.com.mcampos.controller.commom;


import br.com.mcampos.controller.core.BaseController;
import br.com.mcampos.ejb.cloudsystem.address.addresstype.facade.AddressTypeFacade;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;


public class AddressController extends BaseController
{
    private AddressTypeFacade session;

    private Label labelAddressType;
    private Label labelAddressZipCode;
    private Label labelAddress;
    private Label labelAddressDistrict;
    private Label labelAddressState;
    private Label labelAddressCity;
    private Label labelAddressObs;

    private Combobox addressType;

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
        loadCombobox( addressType, getSession().getAll() );
    }

    private AddressTypeFacade getSession()
    {
        if ( session == null )
            session = ( AddressTypeFacade )getRemoteSession( AddressTypeFacade.class );
        return session;
    }
}
