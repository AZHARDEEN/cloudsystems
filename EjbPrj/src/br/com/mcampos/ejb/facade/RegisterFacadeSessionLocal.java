package br.com.mcampos.ejb.facade;

import br.com.mcampos.dto.RegisterDTO;
import br.com.mcampos.dto.user.login.LoginDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.dto.user.UserDTO;

import br.com.mcampos.dto.user.login.LoginCredentialDTO;

import java.util.List;

import javax.ejb.Local;

@Local
public interface RegisterFacadeSessionLocal
{
    List getAllCivilState ();
    List getAllAddressType ();
    List getAllContactType ();
    List getAllDocumentType ();
    List getAllGender ();
    List getAllTitle ();
    List getAllState ( Integer countryId );
    List getAllCity ( Integer countryId, Integer stateId );
    void createNewLogin ( RegisterDTO user );
    PersonDTO findUserByDocument ( String document );
    LoginDTO makePassword ( String identification );
    void changePassword ( String document, String oldPassword, String newPassword );
    void validateEmail ( String token, String password );
    void addPerson ( PersonDTO person );
    Boolean documentExists ( String document );
    void sendValidationEmail ( String document );
}
