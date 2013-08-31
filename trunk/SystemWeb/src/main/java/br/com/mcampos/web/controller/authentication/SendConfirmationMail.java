package br.com.mcampos.web.controller.authentication;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;

import br.com.mcampos.ejb.security.LoginSession;
import br.com.mcampos.entity.security.Login;
import br.com.mcampos.entity.security.UserStatus;
import br.com.mcampos.web.core.BaseCaptchaDialogController;

public class SendConfirmationMail extends BaseCaptchaDialogController<LoginSession>
{
	private static final long serialVersionUID = -4915781259378610581L;
	@Wire
	private Textbox identification;

	@Override
	protected void onOk( )
	{
		try {
			Login login = getSession( ).getByDocument( getIdentification( ).getValue( ) );
			if ( login == null )
			{
				showErrorMessage( "Este usuário não está cadastrado no sistema", "Enviar Email" );
			}
			else if ( login.getStatus( ).getId( ).equals( UserStatus.statusEmailNotValidated ) == false ) {
				showErrorMessage( "O usuário não está esperando um email de confirmação. Seu status difere desta opção ",
						"Enviar Email" );
			}
			else {
				if ( getSession( ).sendValidationEmail( login.getId( ) ).equals( true ) ) {
					gotoPage( "/public/registered.zul", true );
				}
				else {
					showErrorMessage( "Erro ao enviar o Email", "Enviar Email" );
				}
			}
		}
		catch ( Exception e ) {
			showErrorMessage( e.getMessage( ), "Enviar Email" );
		}
	}

	@Override
	protected Class<LoginSession> getSessionClass( )
	{
		return LoginSession.class;
	}

	private Textbox getIdentification( )
	{
		return this.identification;
	}
}
