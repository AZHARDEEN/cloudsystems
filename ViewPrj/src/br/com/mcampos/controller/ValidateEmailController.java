package br.com.mcampos.controller;

import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.exception.ApplicationException;
import br.com.mcampos.util.MultilineMessageBox;
import br.com.mcampos.util.business.LoginLocator;

import javax.ejb.EJBException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Captcha;
import org.zkoss.zul.Div;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

public class ValidateEmailController extends BaseLoginOptionsController
{


    protected Textbox token;
    protected Textbox password;
    protected Textbox recapctcha;
    protected Captcha captcha;
    protected Row tokenRow;

    protected static String loginCookieName = "LoginCookieName";


    protected LoginLocator locator = null;
    
    public ValidateEmailController()
    {
        super();
    }

    public ValidateEmailController( char c )
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

        String csToken;

        csToken = Executions.getCurrent().getParameter( "token" );
        if ( csToken != null && csToken.isEmpty() == false ) {
            token.setValue( csToken );
            tokenRow.setVisible( false );
            password.setFocus( true );
        }
        else
            token.setFocus( true );
    }

    public LoginLocator getLocator()
    {
        if ( this.locator == null )
            this.locator = new LoginLocator();
        return locator;
    }


    public void onClick$cmdSubmit()
    {
        String csIdentification;
        String csPassword;


        csIdentification = token.getValue();
        csPassword = password.getValue();
        if ( validateCaptcha() ) {
            try {
                getLocator().validateEmail( csIdentification, csPassword );
                Sessions.getCurrent().invalidate();
                gotoPage( "/email_validated.zul" );
            }
            catch ( ApplicationException e ) {
                showErrorMessage( e.getMessage() );
            }
        }
    }
}
