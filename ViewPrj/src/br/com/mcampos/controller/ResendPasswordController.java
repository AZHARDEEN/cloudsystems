package br.com.mcampos.controller;

import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.exception.ApplicationException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Textbox;

public class ResendPasswordController extends BaseLoginOptionsController
{
    protected Textbox identification;

    protected static String loginCookieName = "LoginCookieName";


    public ResendPasswordController ()
    {
        super();
    }

    public ResendPasswordController ( char c )
    {
        super( c );
    }


    @Override
    public void doAfterCompose ( Component comp ) throws Exception
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


    public void onClick$cmdSubmit ()
    {
        String csIdentification;

        if ( validateCaptcha() ) {
            csIdentification = identification.getValue();
            try {
                getLocator().sendValidationEmail( UserDocumentDTO.createUserDocumentEmail( csIdentification ) );
                gotoPage( "/registered.zul" );
            }
            catch ( ApplicationException e ) {
                showErrorMessage( e.getMessage() );
            }
        }
    }
}
