package br.com.mcampos.controller.commom;


import br.com.mcampos.controller.core.LoggedBaseController;
import br.com.mcampos.dto.address.CityDTO;
import br.com.mcampos.dto.address.CountryDTO;
import br.com.mcampos.dto.address.StateDTO;
import br.com.mcampos.ejb.cloudsystem.user.address.facade.AddressFacade;
import br.com.mcampos.exception.ApplicationException;

import java.util.Collections;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
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
    private Label labelAddressCountry;

    private Combobox addressType;
    private Combobox state;
    private Combobox country;
    private Combobox city;

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
        setLabel( labelAddressCountry );
        loadCombobox( addressType, getSession().getTypes() );
        loadCombobox( country, getSession().getCountries() );
    }

    private AddressFacade getSession()
    {
        if ( session == null )
            session = ( AddressFacade )getRemoteSession( AddressFacade.class );
        return session;
    }

    public void onSelect$country()
    {
        Comboitem item;

        item = country.getSelectedItem();
        if ( item == null )
            return;
        loadCombobox( state, getStates( ( CountryDTO )item.getValue() ) );
    }

    private List<StateDTO> getStates( CountryDTO country )
    {
        try {
            return getSession().getStates( country );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage() );
            return Collections.emptyList();
        }
    }

    public void onSelect$state()
    {
        Comboitem item;

        item = state.getSelectedItem();
        if ( item == null )
            return;
        loadCombobox( city, getCities( ( StateDTO )item.getValue() ) );
    }


    private List<CityDTO> getCities( StateDTO state )
    {
        try {
            return getSession().getCities( state );
        }
        catch ( ApplicationException e ) {
            showErrorMessage( e.getMessage() );
            return Collections.emptyList();
        }
    }

}
