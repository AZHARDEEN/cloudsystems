package br.com.mcampos.ejb.facade;

import br.com.mcampos.dto.RegisterDTO;

import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.dto.user.login.LoginDTO;
import br.com.mcampos.ejb.session.system.SystemMessagesSessionLocal;

import br.com.mcampos.ejb.session.user.LoginSessionLocal;

import br.com.mcampos.sysutils.SysUtils;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

@Stateless( name = "LoginFacadeSession", mappedName = "CloudSystems-EjbPrj-LoginFacadeSession" )
public class LoginFacadeSessionBean implements LoginFacadeSession
{
    @EJB SystemMessagesSessionLocal systemMessage;
    @EJB LoginSessionLocal login;
    
    public LoginFacadeSessionBean()
    {
    }


    public Boolean add(RegisterDTO dto)
    {
        if ( dto == null )
            systemMessage.throwMessage( 26 );
        login.add( dto );
        return true;
    }


    public void makeNewPassword ( UserDocumentDTO dto ) {
        if ( dto == null )
            systemMessage.throwMessage( 26 );
        getLogin().makeNewPassword( dto );
    }
    
    public void changePassword ( UserDocumentDTO dto, String oldPassword, String newPassword ) {
        if ( dto == null || SysUtils.isEmpty( oldPassword ) || SysUtils.isEmpty( newPassword ) )
            systemMessage.throwMessage( 26 );
        
        getLogin().changePassword( dto, oldPassword, newPassword );
    }

    public void validateEmail ( String token, String password ) {
        if ( SysUtils.isEmpty( token ) || SysUtils.isEmpty( password ) )
            systemMessage.throwMessage( 26 );
        getLogin().validateEmail( token, password );
    }


    public LoginSessionLocal getLogin ()
    {
        return login;
    }


    public void sendValidationEmail( UserDocumentDTO dto ) {
        if ( dto == null )
            systemMessage.throwMessage( 26 );
        getLogin().sendValidationEmail ( dto );
    }
}
