package br.com.mcampos.ejb.facade;


import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.dto.user.ListUserDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.exception.ApplicationException;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface UserFacadeSession
{
    Integer getUserRecordCount( AuthenticationDTO auth ) throws ApplicationException;

    Integer getClientRecordCount( AuthenticationDTO auth, Integer owner ) throws ApplicationException;

    List<ListUserDTO> getUsersByRange( AuthenticationDTO auth, Integer firstResult,
                                       Integer maxResults ) throws ApplicationException;

    PersonDTO getPerson( AuthenticationDTO auth, Integer userId ) throws ApplicationException;

    void add( AuthenticationDTO auth, PersonDTO dto ) throws ApplicationException;

    void add( AuthenticationDTO auth, CompanyDTO dto ) throws ApplicationException;

    UserDTO getUser( AuthenticationDTO auth, Integer userId ) throws ApplicationException;

    UserDTO getUserByDocument( AuthenticationDTO auth, UserDocumentDTO dto ) throws ApplicationException;

    /*
    UserDTO getMyCompany( AuthenticationDTO auth, Integer userID ) throws ApplicationException;

    Integer getMyCompanyCount( AuthenticationDTO auth ) throws ApplicationException;

    List<ListUserDTO> getMyCompaniesByRange( AuthenticationDTO dto, Integer startNumber,
                                             Integer pageSize ) throws ApplicationException;


    List<MenuDTO> getMenuList( AuthenticationDTO auth ) throws ApplicationException;
    */
}
