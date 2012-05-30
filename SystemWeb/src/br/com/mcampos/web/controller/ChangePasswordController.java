package br.com.mcampos.web.controller;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;

import br.com.mcampos.ejb.security.LoginSession;
import br.com.mcampos.web.core.BaseCaptchaDialogController;

public class ChangePasswordController extends BaseCaptchaDialogController<LoginSession>
{
	private static final long serialVersionUID = 883237007110952631L;

	@Wire
	Textbox old_password;
	@Wire
	Textbox password;
	@Wire
	Textbox re_password;

	@Override
	protected Class<LoginSession> getSessionClass( )
	{
		return LoginSession.class;
	}

	@Override
	protected void onOk( )
	{
		onClickCancel( );
	}

	public Textbox getOld_password( )
	{
		return this.old_password;
	}

	public Textbox getPassword( )
	{
		return this.password;
	}

	public Textbox getRe_password( )
	{
		return this.re_password;
	}

	@Override
	protected boolean validate( )
	{
		if ( !super.validate( ) ) {
			return false;
		}
		if ( getPassword( ).getValue( ).equals( getRe_password( ).getValue( ) ) == false ) {
			showErrorMessage( "As novas senhas não são iguais", "Alterar Senha" );
		}
		/*
		 * TODO: inserir a rotina para alterar a senha
		 */
		return true;
	}
}
