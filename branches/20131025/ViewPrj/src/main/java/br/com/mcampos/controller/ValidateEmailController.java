package br.com.mcampos.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Label;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import br.com.mcampos.dto.ApplicationException;

public class ValidateEmailController extends BaseLoginOptionsController
{
	private Textbox token;
	private Textbox password;
	private Row tokenRow;
	private Panel titleValidateEmail;
	private Label validateEmailLabelMsg;
	private Label labelCode;
	private Label labelPassword;

	protected static String loginCookieName = "LoginCookieName";

	public ValidateEmailController( )
	{
		super( );
	}

	@Override
	public void doAfterCompose( Component comp ) throws Exception
	{
		super.doAfterCompose( comp );

		String csToken;

		csToken = Executions.getCurrent( ).getParameter( "token" );
		if ( csToken != null && csToken.isEmpty( ) == false ) {
			token.setValue( csToken );
			tokenRow.setVisible( false );
			password.setFocus( true );
		}
		else
			token.setFocus( true );
		setLabel( titleValidateEmail );
		setLabel( validateEmailLabelMsg );
		setLabel( labelCode );
		setLabel( labelPassword );
	}

	@Override
	public void onClick$cmdSubmit( )
	{
		String csIdentification;
		String csPassword;

		csIdentification = token.getValue( );
		csPassword = password.getValue( );
		if ( validateCaptcha( ) )
			try {
				getLocator( ).validateEmail( csIdentification, csPassword );
				Sessions.getCurrent( ).invalidate( );
				gotoPage( "/email_validated.zul" );
			}
			catch ( ApplicationException e ) {
				showErrorMessage( e.getMessage( ) );
			}
	}
}
