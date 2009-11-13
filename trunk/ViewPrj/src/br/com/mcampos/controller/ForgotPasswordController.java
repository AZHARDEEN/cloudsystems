package br.com.mcampos.controller;

import br.com.mcampos.controller.core.BaseController;

import br.com.mcampos.dto.user.login.LoginDTO;
import br.com.mcampos.util.MultilineMessageBox;

import br.com.mcampos.util.business.RegisterLocator;

import javax.ejb.EJBException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Captcha;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

public class ForgotPasswordController extends BaseLoginOptionsController
{
    protected Textbox identification;
    protected Textbox recapctcha;
    protected Captcha captcha;

    protected static String loginCookieName = "LoginCookieName";


    protected RegisterLocator locator;

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

        this.locator = new RegisterLocator();
        String csLogin = getCookie( loginCookieName );
        if ( csLogin != null && csLogin.isEmpty() == false ) {
            identification.setValue( csLogin );
            recapctcha.setFocus( true );
        }
        else {
            identification.setFocus( true );
        }
    }

    public void setLocator( RegisterLocator locator )
    {
        this.locator = locator;
    }

    public RegisterLocator getLocator()
    {
        return locator;
    }


    public void onClick$cmdSubmit()
    {
        String csIdentification;

        csIdentification = identification.getValue();
        if ( validateCaptcha() ) {
            try {
                if ( getLocator().makePasssword( csIdentification ) != null )
                    gotoPage( "/validate_email_sent.zul" );
                else
                    showErrorMessage( "Não foi possível outro email de confirmação" );
            }
            catch ( EJBException ejbException ) {
                showErrorMessage( ejbException.getMessage() );
            }
        }
    }

}
