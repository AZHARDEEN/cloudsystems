package br.com.mcampos.controller;

import br.com.mcampos.dto.security.AuthenticationDTO;
import br.com.mcampos.dto.user.attributes.DocumentTypeDTO;
import br.com.mcampos.dto.user.login.LoginCredentialDTO;
import br.com.mcampos.dto.user.login.LoginDTO;
import br.com.mcampos.exception.ApplicationException;


import javax.ejb.EJBException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Textbox;

public class LoginController extends BaseLoginOptionsController
{

    protected Textbox identification;
    protected Textbox password;

    protected static String loginCookieName = "LoginCookieName";

    public LoginController ( char c )
    {
        super( c );
    }

    public LoginController ()
    {
        super();
    }

    public void onClick$cmdSubmit ()
    {
        String csLogin;
        String csIdentification;
        String csPassword;

        if ( validateCaptcha() ) {
            csIdentification = identification.getValue();
            csPassword = password.getValue();
            try {
                Execution exec = Executions.getCurrent();
                LoginCredentialDTO credential;

                /*
                 * TODO: regra: permitir o login por qualquer tipo de documento, nao apenas com o email.
                 */
                credential = new LoginCredentialDTO( csPassword, exec.getRemoteAddr(), exec.getRemoteHost() );
                credential.setSessionId( getSessionID() );
                credential.addDocument( DocumentTypeDTO.createDocumentTypeEmail(), csIdentification );
                AuthenticationDTO user = getLocator().loginUser( credential );
                if ( user != null ) {
                    csLogin = identification.getValue();
                    setCookie( loginCookieName, csLogin, 5 );
                    setLoggedInUser( user );
                    Executions.sendRedirect( "/private/index.zul" );
                }
                else
                    showErrorMessage( "Usuário ou senha inválida." );
            }
            catch ( EJBException ejbException ) {
                showErrorMessage( ejbException.getCause().getMessage() );
            }
            catch ( ApplicationException e ) {
                showErrorMessage( e.getMessage() );
                e = null;
            }
        }
    }


    @Override
    public void doAfterCompose ( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );

        String csLogin = getCookie( loginCookieName );
        if ( csLogin != null && csLogin.isEmpty() == false ) {
            identification.setValue( csLogin );
            password.setFocus( true );
        }
        else {
            identification.setFocus( true );
        }

    }
}
