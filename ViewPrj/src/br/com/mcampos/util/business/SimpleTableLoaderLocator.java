package br.com.mcampos.util.business;


import br.com.mcampos.dto.core.DisplayNameDTO;

import br.com.mcampos.ejb.facade.BasicTableSession;

import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;

public class SimpleTableLoaderLocator extends BusinessDelegate
{
	public SimpleTableLoaderLocator()
	{
        super();
	}
    
    /*
     * Para as funções de get and set de session assumiremos que sempre 
     * será retornado um objeto e nunca null.
     * */
    protected BasicTableSession getSessionBean ()
    {
        return ( BasicTableSession ) getEJBSession( BasicTableSession.class );
    }


    protected void loadSimpleDTO ( Combobox target, List l, Boolean bDefaultSelected )
    {
        Comboitem comboItem;
        List<DisplayNameDTO> list = l;
        
        
        if ( target == null )
            return;
        target.getItems().clear();
        if ( list == null || list.size() == 0 )
            return;
        for ( DisplayNameDTO item : list ) {
            comboItem = target.appendItem( item.getDisplayName() );
            if ( comboItem != null )
                comboItem.setValue( item );
        }
        if ( target.getItemCount() > 0 && bDefaultSelected )
            target.setSelectedIndex( 0 );
    }
    
    public void loadCompanyType ( Combobox target )
    {
        loadSimpleDTO( target, getSessionBean().getAllCompanyType(), true );
    }
    
    
    public void getAddressesType ( Combobox target )
    {
        List lista = getSessionBean().getAllAddressType();
        loadSimpleDTO( target, lista, true );
    }
    
    public void getStates ( Combobox target )
    {
        loadSimpleDTO( target, getSessionBean().getAllStates(), true );
    }
    
    
    public void loadCities ( Combobox target, Integer countryID, Integer stateID ) throws ApplicationException
    {
        loadSimpleDTO( target, getSessionBean().getAllStateCities ( countryID, stateID), true );
    }
}



