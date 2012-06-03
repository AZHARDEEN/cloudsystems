package br.com.mcampos.web.core;

import org.zkforge.bwcaptcha.Captcha;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;

import br.com.mcampos.ejb.security.LoginSession;
import br.com.mcampos.sysutils.SysUtils;

public abstract class BaseCaptchaDialogController<BEAN> extends BaseDialogController<BEAN>
{
	private static final long serialVersionUID = 1420645161349142471L;

	@Wire( "#captcha" )
	protected Captcha captcha;

	@Wire( "#recapctcha" )
	protected Textbox recapctcha;

	public BaseCaptchaDialogController( )
	{
		super( );
	}

	protected Boolean validateCaptcha( )
	{
		String sCaptcha = null, sRecaptcha = null;

		try {
			sCaptcha = this.captcha.getValue( );
			sRecaptcha = this.recapctcha.getValue( );
		}
		catch ( Exception e ) {
			if ( sRecaptcha == null || sRecaptcha.length( ) <= 0 ) {
				showErrorMessage( "A validação captcha não está preenchida. Por favor tente de novo" );
				this.recapctcha.focus( );
				return false;
			}
		}

		Boolean verifyCaptcha = false;
		if ( verifyCaptcha == true )
		{
			if ( SysUtils.isEmpty( sRecaptcha ) ) {
				showErrorMessage( "A validação captcha não está preenchida. Por favor tente de novo" );
				this.recapctcha.focus( );
				return false;
			}
			if ( sCaptcha.equalsIgnoreCase( sRecaptcha ) == false ) {
				showErrorMessage( "A validação captcha não confere. Por favor tente de novo" );
				this.recapctcha.focus( );
				return false;
			}
		}
		return true;
	}

	private void showErrorMessage( String msg )
	{
		showErrorMessage( msg, "Validação do Captcha" );
	}

	@Override
	protected boolean validate( )
	{
		if ( validateCaptcha( ) == false ) {
			return false;
		}
		return true;
	}

	protected void setLastLoginInfo( Textbox id, Textbox password )
	{
		String lastPassword = "";
		String lastId = "";

		if ( getSession( ) instanceof LoginSession )
		{
			LoginSession s = (LoginSession) getSession( );
			if ( s != null ) {
				lastId = getCookie( LoggedInterface.lastLoggedUserId );
				boolean debugMode = isDebugMode( );
				if ( SysUtils.isEmpty( lastId ) && debugMode ) {
					lastId = s.getProperty( "debug.login" );
				}
				if ( debugMode ) {
					lastPassword = getCookie( LoggedInterface.lastLoggedUserPassword );
					if ( SysUtils.isEmpty( lastPassword ) ) {
						lastPassword = s.getProperty( "debug.password" );
					}
				}
			}
		}
		if ( id != null ) {
			id.setRawValue( lastId );
		}
		if ( password != null ) {
			password.setRawValue( lastPassword );
		}
	}

	protected boolean isDebugMode( )
	{
		try {
			if ( getSession( ) instanceof LoginSession ) {
				LoginSession s = (LoginSession) getSession( );
				String value = s.getProperty( "login.debugMode" );
				if ( SysUtils.isEmpty( value ) ) {
					value = "false";
				}
				return Boolean.parseBoolean( value );
			}
			else {
				return false;
			}
		}
		catch ( Exception e ) {
			e = null;
			return false;
		}
	}
}
