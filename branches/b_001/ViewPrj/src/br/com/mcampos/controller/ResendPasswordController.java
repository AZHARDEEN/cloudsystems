package br.com.mcampos.controller;


import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.exception.ApplicationException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Textbox;

public class ResendPasswordController extends BaseLoginOptionsController
{
	protected static String loginCookieName = "LoginCookieName";

	protected Textbox identification;
	private Panel titleResendPassword;
	private Label resendEmailLabelMsg;
	private Label labelEmail;

	public ResendPasswordController()
	{
		super();
	}

	public ResendPasswordController( char c )
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
		setLabel( titleResendPassword );
		setLabel( resendEmailLabelMsg );
		setLabel( labelEmail );
	}


	public void onClick$cmdSubmit()
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
