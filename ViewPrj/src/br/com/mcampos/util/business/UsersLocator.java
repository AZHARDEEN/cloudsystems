package br.com.mcampos.util.business;


import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.ejb.facade.UserFacadeSession;

import br.com.mcampos.exception.ApplicationException;

import java.security.InvalidParameterException;

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


    
    public List<ListUserDTO> getUsersByRange ( Integer firstResult, Integer maxResults )
    {
        return getSessionBean().getUsersByRange( firstResult, maxResults );
    }
    
 
   
    public PersonDTO getPerson ( Integer userId )
    {
        return getSessionBean().getPerson( userId );
    }


    public UserDTO getUser ( Integer userId )
    {
        return getSessionBean().getUser( userId );
    }

    
    
    public void add ( PersonDTO dto ) throws ApplicationException
    {
        if ( dto == null )
            throw new InvalidParameterException ( "Parâmetro dto não pode ser nulo." );
        getSessionBean().add( dto );
    }
    
    public void add ( CompanyDTO dto ) throws ApplicationException
    {
        if ( dto == null )
            throw new InvalidParameterException ( "Parâmetro dto não pode ser nulo." );
        getSessionBean().add( dto );
    }
    
    public UserDTO getUserByDocument ( UserDocumentDTO dto )
    {
        return getSessionBean().getUserByDocument( dto );
    }
}
