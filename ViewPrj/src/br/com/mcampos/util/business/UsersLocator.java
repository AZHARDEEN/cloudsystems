package br.com.mcampos.util.business;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.system.MenuDTO;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.ejb.entity.security.Role;
import br.com.mcampos.ejb.facade.UserFacadeSession;

import br.com.mcampos.exception.ApplicationException;

import br.com.mcampos.sysutils.SysUtils;

import java.security.InvalidParameterException;

import java.util.Collections;
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


    public List<ListUserDTO> getUsersByRange( AuthenticationDTO auth, Integer firstResult,
                                              Integer maxResults ) throws ApplicationException
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
        if ( dto == null )
            return null;
        return getSessionBean().getMyCompany( dto, companyId );
    }

    public Integer getMyCompanyCount( AuthenticationDTO dto ) throws ApplicationException
    {
        if ( dto == null )
            return 0;
        return getSessionBean().getMyCompanyCount( dto );
    }

    public List<ListUserDTO> getMyCompaniesByRange( AuthenticationDTO dto, Integer startNumber,
                                                    Integer pageSize ) throws ApplicationException
    {
        if ( dto == null )
            return Collections.EMPTY_LIST;
        return getSessionBean().getMyCompaniesByRange( dto, startNumber, pageSize );
    }

    /**
     * Obtem todas as roles do usuário autenticado.
     * As roles são a base para todo o esquema de segurança do sistema.
     * Inclusive para obter o menu de acesso ao sistema.
     *
     * @param auth DTO do usuário autenticado.
     * @return A lista de roles do usuário ou null.
     */
    public List<MenuDTO> getRoles( AuthenticationDTO auth ) throws ApplicationException
    {
        if ( auth == null )
            return Collections.EMPTY_LIST;
        return getSessionBean().getMenuList( auth );
    }
}
