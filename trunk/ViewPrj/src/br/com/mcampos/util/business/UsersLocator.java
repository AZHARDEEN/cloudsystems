package br.com.mcampos.util.business;


import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.dto.user.login.ListLoginDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.dto.user.login.LoginCredentialDTO;
import br.com.mcampos.dto.user.login.LoginDTO;
import br.com.mcampos.ejb.facade.UserFacadeSession;

import com.bea.common.engine.InvalidParameterException;

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
    
    public UserDTO getUserByDocument ( UserDocumentDTO dto )
    {
        return getSessionBean().getUserByDocument( dto );
    }
}
