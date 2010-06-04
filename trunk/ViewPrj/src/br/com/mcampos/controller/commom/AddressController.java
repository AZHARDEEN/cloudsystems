package br.com.mcampos.controller.commom;


import br.com.mcampos.controller.core.BaseController;
import br.com.mcampos.dto.address.AddressTypeDTO;
import br.com.mcampos.ejb.cloudsystem.address.addresstype.facade.AddressTypeFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
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
        loadCombobox();
    }

    private AddressTypeFacade getSession()
    {
        if ( session == null )
            session = ( AddressTypeFacade )getRemoteSession( AddressTypeFacade.class );
        return session;
    }

    private void loadCombobox()
    {
        if ( addressType == null )
            return;

        List<AddressTypeDTO> list = null;

        try {
            if ( addressType.getChildren() != null )
                addressType.getChildren().clear();
            list = getSession().getAll();
        }
        catch ( ApplicationException e ) {
            e = null;
            list = null;
        }
        if ( list != null ) {
            for ( AddressTypeDTO item : list ) {
                Comboitem comboItem = addressType.appendItem( item.toString() );
                if ( comboItem != null )
                    comboItem.setValue( item );
            }
            if ( addressType.getChildren() != null && addressType.getChildren().size() > 0 )
                addressType.setSelectedIndex( 0 );
        }
    }
}
