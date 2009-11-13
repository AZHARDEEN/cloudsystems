package br.com.mcampos.util.business;


import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.dto.user.login.ListLoginDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.dto.user.login.LoginCredentialDTO;
import br.com.mcampos.dto.user.login.LoginDTO;
import br.com.mcampos.ejb.facade.UserFacadeSession;
import br.com.mcampos.ejb.session.user.UserSession;

import com.bea.common.engine.InvalidParameterException;

import java.util.InvalidPropertiesFormatException;
import java.util.List;

public class UsersLocator extends BusinessDelegate
{
    public UsersLocator()
    {
        super();
    }


    protected UserFacadeSession getSessionBean ()
    {
        return ( UserFacadeSession ) getEJBSession( UserFacadeSession.class );
    }


    public Long getUserRecordCount ()
    {
        return getSessionBean().getUserRecordCount();
    }


    public Long getClientRecordCount ( Integer owner )
    {
        return getSessionBean().getClientRecordCount( owner );
    }


    public Long getLoginRecordCount ()
    {
        return getSessionBean().getLoginRecordCount();
    }

    
    public List<ListUserDTO> getUsersByRange ( Integer firstResult, Integer maxResults )
    {
        return getSessionBean().getUsersByRange( firstResult, maxResults );
    }
    
    public Boolean documentExists ( String document )
    {
        return getSessionBean().documentExists( document );   
    }
    
    List<ListLoginDTO> getLoginList()
    {
        return getSessionBean().getLoginList();
    }
    
    public List<ListLoginDTO> getLoginByRange ( Integer firstResult, Integer maxResults )
    {
        return getSessionBean().getLoginByRange( firstResult, maxResults );
    }
    
    public PersonDTO getPerson ( Integer userId )
    {
        return getSessionBean().getPerson( userId );
    }


    public UserDTO getUser ( Integer userId )
    {
        return getSessionBean().getUser( userId );
    }

    
    
    public void deleteLogins ( Integer[] logins ) {
        if ( logins == null || logins.length == 0 )
            return;
        
        getSessionBean().deleteLogin( logins );
    }
    
    public void add ( PersonDTO dto )
    {
        if ( dto == null )
            throw new InvalidParameterException ( "Parâmetro dto não pode ser nulo." );
        getSessionBean().add( dto );
    }
    
    public void add ( CompanyDTO dto )
    {
        if ( dto == null )
            throw new InvalidParameterException ( "Parâmetro dto não pode ser nulo." );
        getSessionBean().add( dto );
    }
    
    public LoginDTO loginUser ( LoginCredentialDTO dto ) {
        return getSessionBean().loginUser( dto );
    }

    public void logoutUser ( LoginDTO dto ) {
        getSessionBean().logoutUser( dto );
    }
    
    
    public void updateLoginStatus ( Integer id, Integer newStatus )
    {
        getSessionBean().updateLoginStatus( id, newStatus );
    }
    
    public UserDTO getUserByDocument ( String document, Integer docType )
    {
        return getSessionBean().getUserByDocument( document, docType );
    }
    
    
    public void addBusinessEntity (  UserDTO dto, LoginDTO loginDTO )
    {
        getSessionBean().addBusinessEntity( dto, loginDTO );
    }
    
    public List<ListUserDTO> getBusinessList ( LoginDTO dto )
    {
        return getSessionBean().getBusinessList ( dto.getUserId() );
    }
    
    public Long getBusinessEntityCount ( Integer userId )
    {
        return getSessionBean().getBusinessentityCount( userId );
    }

    public UserDTO getBusinessEntity ( Integer businessEntityId, LoginDTO currentUser )
    {
        return getSessionBean().getBusinessEntity( businessEntityId, currentUser.getUserId() );
    }
    
    public List<ListUserDTO> getBusinessEntityByRange ( Integer curentUserId, Integer itemStartNumber, Integer pageSize )
    {
        return getSessionBean().getBusinessEntityByRange( curentUserId, itemStartNumber, pageSize );
    }
}