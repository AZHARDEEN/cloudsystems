package br.com.mcampos.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Textbox;

import br.com.mcampos.dto.ApplicationException;
import br.com.mcampos.dto.user.UserDocumentDTO;

public class ForgotPasswordController extends BaseLoginOptionsController
{
	protected Textbox identification;

	protected static String loginCookieName = "LoginCookieName";

	private Panel titleForgotPasswd;
	private Label labelIdentification;

	public ForgotPasswordController( )
	{
		super( );
	}

	@Override
	public void doAfterCompose( Component comp ) throws Exception
	{
		super.doAfterCompose( comp );

		String csLogin = getCookie( loginCookieName );
		if ( csLogin != null && csLogin.isEmpty( ) == false ) {
			identification.setValue( csLogin );
			recapctcha.setFocus( true );
		}
		else
			identification.setFocus( true );
		setLabel( titleForgotPasswd );
		setLabel( labelIdentification );
	}

	@Override
	public void onClick$cmdSubmit( )
	{
		String csIdentification;

		csIdentification = identification.getValue( );
		if ( validateCaptcha( ) )
			try {
				getLocator( ).makeNewPasssword( UserDocumentDTO.createUserDocumentEmail( csIdentification ) );
				gotoPage( "/forgot_password_sent.zul" );
			}
			catch ( ApplicationException e ) {
				showErrorMessage( e.getMessage( ) );
			}
	}

}
