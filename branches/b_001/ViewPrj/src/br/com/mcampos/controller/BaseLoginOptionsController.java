package br.com.mcampos.controller;


import br.com.mcampos.controller.core.BaseController;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.util.MultilineMessageBox;
import br.com.mcampos.util.business.LoginLocator;

import org.zkforge.bwcaptcha.Captcha;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;


public abstract class BaseLoginOptionsController extends BaseController
{
    protected static String alternativePath = "/index.zul";
    protected Textbox recapctcha;
    protected Captcha captcha;
    protected LoginLocator locator = null;
    protected Button cmdForgotPassword;


    public BaseLoginOptionsController()
    {
        super();
    }

    public BaseLoginOptionsController( char c )
    {
        super( c );
    }


    public abstract void onClick$cmdSubmit();


    public void onClick$cmdForgotPassword( Event evt )
    {
        if ( getRootParent() != null )
            gotoPage( "/forgot_password.zul", getRootParent().getParent() );
        else
            gotoPage( "/forgot_password.zul" );
    }

    public void onOK$identification()
    {
        onClick$cmdSubmit();
    }

    public void onOK$recapctcha()
    {
        onClick$cmdSubmit();
    }

    public void onOK$password()
    {
        onClick$cmdSubmit();
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
                showErrorMessage( "A validação captcha não está preenchida. Por favor tente de novo" );
                recapctcha.focus();
                return false;
            }
        }

        if ( SysUtils.isEmpty( sRecaptcha ) ) {
            showErrorMessage( "A validação captcha não está preenchida. Por favor tente de novo" );
            recapctcha.focus();
            return false;
        }
        if ( sCaptcha.equalsIgnoreCase( sRecaptcha ) == false ) {
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

    protected LoginLocator getLocator()
    {
        if ( this.locator == null )
            this.locator = new LoginLocator();
        return locator;
    }

    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );
        setLabel( cmdForgotPassword );
    }
}
