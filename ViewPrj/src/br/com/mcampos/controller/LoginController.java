package br.com.mcampos.controller;

import br.com.mcampos.dto.user.attributes.DocumentTypeDTO;
import br.com.mcampos.dto.user.login.LoginCredentialDTO;
import br.com.mcampos.dto.user.login.LoginDTO;
import br.com.mcampos.util.MultilineMessageBox;


import br.com.mcampos.util.business.UsersLocator;

import javax.ejb.EJBException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Captcha;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class LoginController extends BaseLoginOptionsController {
    
    protected Textbox identification;
    protected Textbox password;
    protected Textbox recapctcha;
    protected Captcha captcha;

    protected UsersLocator locator;

    
    protected static String loginCookieName = "LoginCookieName";

    public LoginController( char c ) {
        super( c );
    }

    public LoginController() {
        super();
    }
    
    public void onClick$cmdSubmit () {
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
                credential = new LoginCredentialDTO ( csPassword, 
                                                      exec.getRemoteAddr(), 
                                                      exec.getRemoteHost() );
                credential.addDocument( DocumentTypeDTO.createDocumentTypeEmail(), csIdentification );
                LoginDTO user = getLocator().loginUser( credential );
                if ( user != null ) {
                    csLogin = identification.getValue ();
                    setCookie( loginCookieName, csLogin, 5 );
                    setLoggedInUser( user );
                    Executions.sendRedirect("/private/index.zul" );
                }
                else
                    showErrorMessage ( "Usuário ou senha inválida." );
            }
            catch ( EJBException ejbException ) {
                showErrorMessage( ejbException.getCause().getMessage() );
            }
        }
    }


    protected Boolean validateCaptcha ( )
    {
        String sCaptcha = null, sRecaptcha = null;
        
        try {
            sCaptcha = captcha.getValue();
            sRecaptcha = recapctcha.getValue();
        }
        catch ( Exception e )
        {
            if ( sRecaptcha == null || sRecaptcha.length() <= 0 )
            {
                showErrorMessage ( "A validação captcha não está " +
                                   "preenchida. Por favor tente de novo" );
                recapctcha.focus ();
                return false;
            }
        }
        
        if ( sCaptcha == null || sCaptcha.equalsIgnoreCase( sRecaptcha) == false ) {
            showErrorMessage ( "A validação captcha não confere. Por favor tente de novo" );
            recapctcha.focus ();
            return false;
        }
        return true;
    }
    
    
    protected void showErrorMessage ( String msg )
    {
        try {
            MultilineMessageBox.show( msg, "Validação", Messagebox.OK, Messagebox.ERROR, true );
        }
        catch ( Exception e ) {
            e = null;
        }
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception {
        super.doAfterCompose( comp );
        
        this.locator = new UsersLocator ();
        String csLogin = getCookie( loginCookieName );
        if ( csLogin != null && csLogin.isEmpty() == false ) {
            identification.setValue( csLogin );
            password.setFocus( true );
        }
        else {
            identification.setFocus( true );
        }
        
    }

    public void setLocator( UsersLocator locator ) {
        this.locator = locator;
    }

    public UsersLocator getLocator() {
        return locator;
    }
    
    public void onOK$identification ()
    {
        onClick$cmdSubmit();
    }

    public void onOK$password ()
    {
        onClick$cmdSubmit();
    }


    public void onOK$recapctcha ()
    {
        onClick$cmdSubmit();
    }
}
