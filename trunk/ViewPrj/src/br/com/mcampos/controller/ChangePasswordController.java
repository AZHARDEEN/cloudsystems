package br.com.mcampos.controller;

import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.util.MultilineMessageBox;


import br.com.mcampos.util.business.LoginLocator;

import org.zkforge.bwcaptcha.Captcha;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;




public class ChangePasswordController extends BaseLoginOptionsController {

    protected Textbox identity;
    protected Textbox old_password; 
    protected Textbox password;
    protected Textbox re_password;
    protected Textbox recapctcha; 
    protected Captcha captcha;
    
    protected static String loginCookieName = "LoginCookieName";
    

    protected LoginLocator locator;


    public ChangePasswordController() {
        super();
    }

    public ChangePasswordController( char c ) {
        super( c );
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
        
        this.locator = new LoginLocator ();
        String csLogin = getCookie( loginCookieName );
        if ( csLogin != null && csLogin.isEmpty() == false ) {
            identity.setValue( csLogin );
            old_password.setFocus( true );
        }
        else {
            identity.setFocus( true );
        }   
    }
    
    public void setLocator( LoginLocator locator ) {
        this.locator = locator;
    }

    public LoginLocator getLocator() {
        return locator;
    }
    

    public void onClick$cmdSubmit () {
        String csIdentification;
        String csPassword, csRePassword, csOldPassword;
        
        csOldPassword = old_password.getValue();
        csPassword = password.getValue();
        csRePassword = re_password.getValue ();
        
        if ( csPassword.equals( csRePassword) == false ) {
            showErrorMessage( "As novas senhas informadas são diferentes" );
            return;
        }
        if ( validateCaptcha() ) {
            csIdentification = identity.getValue();
            try {
                getLocator().changePassword( UserDocumentDTO.createUserDocumentEmail( csIdentification ), csOldPassword, csPassword );
                Sessions.getCurrent().invalidate();
                gotoPage ("/password_changed.zul");
            }
            catch ( ApplicationException e ) {
                showErrorMessage( e.getMessage() );
            }
        }
    }
    
    public void onOK$recapctcha ()
    {
        onClick$cmdSubmit ();
    }
}
