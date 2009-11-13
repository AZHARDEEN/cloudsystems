package br.com.mcampos.ejb.facade;

import br.com.mcampos.dto.user.CompanyDTO;
import br.com.mcampos.dto.user.login.ListLoginDTO;
import br.com.mcampos.dto.user.ListUserDTO;

import br.com.mcampos.dto.user.PersonDTO;

import br.com.mcampos.dto.user.UserDTO;

import br.com.mcampos.dto.user.login.LoginCredentialDTO;
import br.com.mcampos.dto.user.login.LoginDTO;

import java.util.List;

import javax.ejb.Local;

@Local
public interface UserFacadeSessionLocal
{
    Long getUserRecordCount ();
    Long getClientRecordCount( Integer owner );
    List<ListUserDTO> getUsersByRange ( Integer firstResult, Integer maxResults );
    Boolean documentExists ( String document );
    List<ListLoginDTO> getLoginList();
    Long getLoginRecordCount ();
    List<ListLoginDTO> getLoginByRange ( Integer firstResult, Integer maxResults );    
    PersonDTO getPerson ( Integer userId );
    void deleteLogin ( Integer[] logins );
    void add ( PersonDTO dto );
    void add ( CompanyDTO dto );
    UserDTO getUser ( Integer userId );
    LoginDTO loginUser ( LoginCredentialDTO dto ) ;
    void logoutUser ( LoginDTO dto );
    void updateLoginStatus ( Integer id, Integer newStatus );
    UserDTO getUserByDocument ( String document, Integer docType );
    void addBusinessEntity ( UserDTO dto, LoginDTO login );
    List<ListUserDTO> getBusinessList (Integer userId);
    Long getBusinessentityCount ( Integer userId );
    UserDTO getBusinessEntity ( Integer userId, Integer currentUserId );
    List<ListUserDTO> getBusinessEntityByRange ( Integer curentUserId, Integer itemStartNumber, Integer pageSize );
}
