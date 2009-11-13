package br.com.mcampos.util.business;

import br.com.mcampos.dto.RegisterDTO;
import br.com.mcampos.dto.address.StateDTO;
import br.com.mcampos.dto.core.DisplayNameDTO;
import br.com.mcampos.dto.core.SimpleTableDTO;
import br.com.mcampos.dto.user.login.LoginDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.dto.user.attributes.ContactTypeDTO;
import br.com.mcampos.dto.user.login.LoginCredentialDTO;
import br.com.mcampos.ejb.facade.BasicTableSession;
import br.com.mcampos.ejb.facade.RegisterFacadeSession;

import java.util.List;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Label;

public class RegisterLocator extends BusinessDelegate
{
    
    public RegisterLocator()
    {
        super();
    }
    
    protected RegisterFacadeSession getSessionBean ()
    {
        return ( RegisterFacadeSession ) getEJBSession( RegisterFacadeSession.class );
    }





    
    public void loadState ( Combobox target )
    {
        loadSimpleDTO( target, getSessionBean().getAllState( 33 ), true );
    }

    public void loadContactType ( Combobox target )
    {
        loadSimpleDTO( target, getSessionBean().getAllContactType(), true );
    }

    public void loadDocumentType ( Combobox target )
    {
        loadSimpleDTO( target, getSessionBean().getAllDocumentType(), true );
    }

    
    public void loadAddressType ( Combobox target )
    {
        loadSimpleDTO( target, getSessionBean().getAllAddressType(), true );
    }


    public void loadGender ( Combobox target )
    {
        loadSimpleDTO( target, getSessionBean().getAllGender(), true );
    }

    public void loadCivilState ( Combobox target )
    {
        loadSimpleDTO( target, getSessionBean().getAllCivilState(), true );
    }


    public void loadCity ( Combobox target, Integer countryId, Integer stateId )
    {
        loadSimpleDTO( target, getSessionBean().getAllCity( countryId, stateId ), false );
    }


    public void loadSimpleDTO ( Combobox target, List l, Boolean bDefaultSelected )
    {
        Comboitem comboItem;
        List<DisplayNameDTO> list = (List<DisplayNameDTO>) l;
        
        
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
    
    
    public void addNewUser ( RegisterDTO dto )
    {
        getSessionBean().createNewLogin( dto );
    }


    public void add ( PersonDTO dto )
    {
        getSessionBean().addPerson( dto );
    }

    
    public PersonDTO findPersonByDocument ( String document )
    {
        return getSessionBean().findUserByDocument( document );
    }
    
    public LoginDTO makePasssword ( String identification ) {
        return getSessionBean().makePassword( identification );
    }
    
    public void changePassword ( String identification, String oldPassword, String newPassword ) {
        getSessionBean().changePassword( identification, oldPassword, newPassword );       
   }
    public void validateEmail ( String token, String password ) {
        getSessionBean().validateEmail( token, password );
    }
    
    public Boolean documentExists ( String document )
    {
        return getSessionBean().documentExists ( document );
    }
    
    public void sendValidationEmail ( String document )
    {
        getSessionBean().sendValidationEmail ( document );
    }
}
