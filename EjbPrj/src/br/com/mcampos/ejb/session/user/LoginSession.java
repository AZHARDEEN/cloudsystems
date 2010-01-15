package br.com.mcampos.ejb.session.user;

import br.com.mcampos.dto.RegisterDTO;
import br.com.mcampos.dto.user.login.ListLoginDTO;
import br.com.mcampos.dto.user.login.LoginCredentialDTO;
import br.com.mcampos.dto.user.login.LoginDTO;

import br.com.mcampos.ejb.entity.user.Person;

import br.com.mcampos.ejb.entity.user.Users;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface LoginSession
{
    void add( RegisterDTO login );
    void add( RegisterDTO login, Person person );
    void update( LoginDTO login );
    void delete( Integer id );
    List<LoginDTO> getLoginFindAll();
    LoginDTO loginUser ( LoginCredentialDTO dto );
    public void logoutUser ( LoginDTO dto );
    LoginDTO makeNewPassword ( String document );
    void changePassword ( String document, String oldPassword, String newPassword );
    void validateEmail ( String token, String password );
    public List<ListLoginDTO> getList();
    Long getRecordCount ();
    List<ListLoginDTO> getLoginByRange( int firstResult, int maxResults );
    void delete( Integer[] logins );
    void updateLoginStatus ( Integer id, Integer newStatus );
}
