package br.com.mcampos.controller;

import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.exception.ApplicationException;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Textbox;

public class ForgotPasswordController extends BaseLoginOptionsController
{
    protected Textbox identification;

    protected static String loginCookieName = "LoginCookieName";

    public ForgotPasswordController()
    {
        super();
    }

    public ForgotPasswordController( char c )
    {
        super( c );
    }



    @Override
    public void doAfterCompose( Component comp ) throws Exception
    {
        super.doAfterCompose( comp );

        String csLogin = getCookie( loginCookieName );
        if ( csLogin != null && csLogin.isEmpty() == false ) {
            identification.setValue( csLogin );
            recapctcha.setFocus( true );
        }
        else {
            identification.setFocus( true );
        }
    }


    public void onClick$cmdSubmit()
    {
        String csIdentification;

        csIdentification = identification.getValue();
        if ( validateCaptcha() ) {
            try {
                getLocator().makeNewPasssword( UserDocumentDTO.createUserDocumentEmail( csIdentification ) );
                gotoPage( "/forgot_password_sent.zul" );
            }
            catch ( ApplicationException e ) {
                showErrorMessage( e.getMessage() );
            }
        }
    }

}
