package br.com.mcampos.web.controller.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.com.mcampos.ejb.security.LoginSession;
import br.com.mcampos.jpa.security.Login;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.sysutils.dto.CredentialDTO;
import br.com.mcampos.sysutils.dto.PrincipalDTO;
import br.com.mcampos.web.core.BaseCaptchaDialogController;
import br.com.mcampos.web.core.LoggedInterface;

public class LoginController extends BaseCaptchaDialogController<LoginSession>
{
	private static final long serialVersionUID = -3608637091684592684L;
	private static final Logger logger = LoggerFactory.getLogger( LoginController.class );

	@Wire( "#identification" )
	private Textbox identification;

	@Wire( "#password" )
	private Textbox password;

	public LoginController( )
	{
		super( );
	}

	@Override
	protected void onOk( )
	{
		redirect( null );
	}

	@Override
	protected Class<LoginSession> getSessionClass( )
	{
		return LoginSession.class;
	}

	@Override
	protected boolean validate( )
	{
		String value;

		boolean bRet = super.validate( );
		if ( bRet == false ) {
			return false;
		}

		value = identification.getValue( );
		if ( SysUtils.isEmpty( value ) ) {
			identification.setFocus( true );
			return false;
		}

		value = password.getValue( );
		if ( SysUtils.isEmpty( value ) ) {
			password.setFocus( true );
			return false;
		}
		LoginSession session = getSession( );

		Login login = session.loginByDocument( getCredential( ) );
		if ( login == null ) {
			showErrorMessage( "Erro ao realizar login. Identificação inexistente no sistema", "Login" );
			identification.setFocus( true );
			return false;
		}
		logger.info( "Login from " + login.getPerson( ).getName( ) );
		setSessionParameter( currentPrincipal, new PrincipalDTO( login.getId( ), login.getPerson( ).getFriendlyName( ) ) );
		setCookie( LoggedInterface.lastLoggedUserId, getCredential( ).getIdentification( ) );
		if ( isDebugMode( ) ) {
			setCookie( LoggedInterface.lastLoggedUserPassword, getCredential( ).getPassword( ) );
		}
		return true;
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );

		setLastLoginInfo( identification, password );

	}

	@Listen( "onClick = #cmdForgotPassword" )
	public void recoverPassword( Event evt )
	{
		gotoPage( "/public/forgot_password.zul", true );
	}

	@Override
	protected CredentialDTO getCredential( )
	{
		CredentialDTO c = super.getCredential( );
		c.setIdentification( identification.getValue( ) );
		c.setPassword( password.getValue( ) );
		return c;
	}
}
