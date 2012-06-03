package br.com.mcampos.web.controller.authentication;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.security.register.RegisterSession;
import br.com.mcampos.web.core.BaseCaptchaDialogController;

public class EmailValidationController extends BaseCaptchaDialogController<RegisterSession>
{
	private static final long serialVersionUID = -9141522631750900935L;

	@Wire
	private Textbox token;
	@Wire
	private Textbox password;
	@Wire
	private Row tokenRow;

	@Override
	protected void onOk( )
	{
		try {
			if ( getSession( ).validate( this.token.getValue( ), this.password.getValue( ) ) == true ) {
				Sessions.getCurrent( ).invalidate( ); // Just in case....
				gotoPage( "/public/validate_email_sent.zul", true );
			}
			else {
				showErrorMessage( "Erro ao validar o email", "Validar Email" );
			}
		}
		catch ( Exception e ) {
			showErrorMessage( "Erro ao validar o email", "Validar Email" );
		}

	}

	@Override
	protected Class<RegisterSession> getSessionClass( )
	{
		return RegisterSession.class;
	}

	@Override
	protected boolean validate( )
	{
		if ( this.token.isValid( ) == false )
		{
			showErrorMessage( "O Código de validação deve ser informado", "Validar Email" );
			this.token.setFocus( true );
			return false;
		}
		if ( this.password.isValid( ) == false )
		{
			showErrorMessage( "A senha deve ser informada", "Validar Email" );
			this.password.setFocus( true );
			return false;
		}
		return super.validate( );
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );
		setLastLoginInfo( null, this.password );
		String csToken = Executions.getCurrent( ).getParameter( "token" );
		if ( csToken != null && csToken.isEmpty( ) == false ) {
			this.token.setValue( csToken );
			this.tokenRow.setVisible( false );
			this.password.setFocus( true );
		}
		else {
			this.token.setFocus( true );
		}
	}
}
