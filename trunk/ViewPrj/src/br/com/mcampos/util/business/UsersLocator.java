package br.com.mcampos.util.business;


import br.com.mcampos.dto.security.AuthenticationDTO;
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


    protected UserFacadeSession getSessionBean()
    {
        return ( UserFacadeSession )getEJBSession( UserFacadeSession.class );
    }


    public Integer getUserRecordCount( AuthenticationDTO auth ) throws ApplicationException
    {
        return getSessionBean().getUserRecordCount( auth );
    }


    public Integer getClientRecordCount( AuthenticationDTO auth, Integer owner ) throws ApplicationException
    {
        return getSessionBean().getClientRecordCount( auth, owner );
    }


    public List<ListUserDTO> getUsersByRange( AuthenticationDTO auth, Integer firstResult, Integer maxResults ) throws ApplicationException
    {
        return getSessionBean().getUsersByRange( auth, firstResult, maxResults );
    }


    public PersonDTO getPerson( AuthenticationDTO auth, Integer userId ) throws ApplicationException
    {
        return getSessionBean().getPerson( auth, userId );
    }


    public UserDTO getUser( AuthenticationDTO auth, Integer userId ) throws ApplicationException
    {
        return getSessionBean().getUser( auth, userId );
    }


    public void add( AuthenticationDTO auth, PersonDTO dto ) throws ApplicationException
    {
        if ( dto == null )
            throw new InvalidParameterException( "Parâmetro dto não pode ser nulo." );
        getSessionBean().add( auth, dto );
    }

    public void add( AuthenticationDTO auth, CompanyDTO dto ) throws ApplicationException
    {
        if ( dto == null )
            throw new InvalidParameterException( "Parâmetro dto não pode ser nulo." );
        getSessionBean().add( auth, dto );
    }

    public UserDTO getUserByDocument( AuthenticationDTO auth, UserDocumentDTO dto ) throws ApplicationException
    {
        return getSessionBean().getUserByDocument( auth, dto );
    }


    /*
     * Estas funcoes abaixo são para o uso das minhas empresas
     */

    public UserDTO getMyCompany( AuthenticationDTO dto, Integer companyId ) throws ApplicationException
    {
        return getSessionBean().getMyCompany( dto, companyId );
    }

    public Integer getMyCompanyCount( AuthenticationDTO dto ) throws ApplicationException
    {
        return getSessionBean().getMyCompanyCount( dto );
    }
}
