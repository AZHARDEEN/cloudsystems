package br.com.mcampos.controller;

import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.util.MultilineMessageBox;

import br.com.mcampos.util.business.LoginLocator;

import org.zkforge.bwcaptcha.Captcha;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class ForgotPasswordController extends BaseLoginOptionsController
{
    protected Textbox identification;
    protected Textbox recapctcha;
    protected Captcha captcha;

    protected static String loginCookieName = "LoginCookieName";


    protected LoginLocator locator;

    public ForgotPasswordController()
    {
        super();
    }

    public ForgotPasswordController( char c )
    {
        super( c );
    }


    protected Boolean validateCaptcha()
    {
        String sCaptcha = null, sRecaptcha = null;

        try {
            sCaptcha = captcha.getValue();
            sRecaptcha = recapctcha.getValue();
        }
        catch ( Exception e ) {
            if ( sRecaptcha == null || sRecaptcha.length() <= 0 ) {
                showErrorMessage( "A validação captcha não está " + "preenchida. Por favor tente de novo" );
                recapctcha.focus();
                return false;
            }
        }

        if ( sCaptcha == null || sCaptcha.equalsIgnoreCase( sRecaptcha ) == false ) {
            showErrorMessage( "A validação captcha não confere. Por favor tente de novo" );
            recapctcha.focus();
            return false;
        }
        return true;
    }

    protected void showErrorMessage( String msg )
    {
        try {
            MultilineMessageBox.show( msg, "Validação", Messagebox.OK, Messagebox.ERROR, true );
        }
        catch ( Exception e ) {
            e = null;
        }
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );

        this.locator = new LoginLocator();
        String csLogin = getCookie( loginCookieName );
        if ( csLogin != null && csLogin.isEmpty() == false ) {
            identification.setValue( csLogin );
            recapctcha.setFocus( true );
        }
        else {
            identification.setFocus( true );
        }
    }

    public void setLocator( LoginLocator locator )
    {
        this.locator = locator;
    }

    public LoginLocator getLocator()
    {
        return locator;
    }


    public void onClick$cmdSubmit()
    {
        String csIdentification;

        csIdentification = identification.getValue();
        if ( validateCaptcha() ) {
            try {
                getLocator().makeNewPasssword( UserDocumentDTO.createUserDocumentEmail( csIdentification ) );
                gotoPage( "/validate_email_sent.zul" );
            }
            catch ( ApplicationException e ) {
                showErrorMessage( e.getMessage() );
            }
        }
    }

}
