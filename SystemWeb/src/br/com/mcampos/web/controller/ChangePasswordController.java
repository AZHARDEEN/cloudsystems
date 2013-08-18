package br.com.mcampos.web.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;

import br.com.mcampos.ejb.security.Login;
import br.com.mcampos.ejb.security.LoginSession;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;
import br.com.mcampos.web.core.BaseCaptchaDialogController;
import br.com.mcampos.web.core.LoggedInterface;

public class ChangePasswordController extends BaseCaptchaDialogController<LoginSession> implements LoggedInterface
{
	private static final long serialVersionUID = 883237007110952631L;
	public static final String errorTitle = "Alterar Senha";

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
		boolean bRet;

		bRet = getSession( ).changePassword( getLoggedUser( ), getCredential( ),
				getOld_password( ).getValue( ), getPassword( ).getValue( ) );
		if ( bRet ) {
			onClickCancel( );
		}
		else {
			showErrorMessage( "Erro ao trocar a senha.", errorTitle );
		}
	}

	public Textbox getOld_password( )
	{
		return old_password;
	}

	public Textbox getPassword( )
	{
		return password;
	}

	public Textbox getRe_password( )
	{
		return re_password;
	}

	@Override
	protected boolean validate( )
	{
		if ( !super.validate( ) ) {
			return false;
		}
		if ( getPassword( ).getValue( ).equals( getRe_password( ).getValue( ) ) == false ) {
			showErrorMessage( "As novas senhas não são iguais", errorTitle );
			return false;
		}
		if ( getSession( ).verifyPassword( getLoggedUser( ).getId( ), getOld_password( ).getValue( ) ) == false ) {
			showErrorMessage( "A senha atual é inválida", errorTitle );
			return false;
		}
		if ( getSession( ).isPasswordUsed( getLoggedUser( ).getId( ), getPassword( ).getValue( ) ) ) {
			showErrorMessage( "A nova senha informada já foi usada antes. Por favor user uma nova senha", errorTitle );
			return false;
		}
		return true;
	}

	@Override
	public boolean isLogged( )
	{
		Login login = getLoggedUser( );
		return login != null;
	}

	@Override
	public ComponentInfo doBeforeCompose( Page page, Component parent, ComponentInfo compInfo )
	{
		if ( isLogged( ) ) {
			return super.doBeforeCompose( page, parent, compInfo );
		}
		else {
			redirect( "/index.zul" );
			return null;
		}
	}

	@Override
	public Login getLoggedUser( )
	{
		return (Login) getSessionParameter( userSessionParamName );
	}

	@Override
	public Collaborator getCurrentCollaborator( )
	{
		Collaborator c = (Collaborator) getSessionParameter( currentCollaborator );
		Login l = getLoggedUser( );
		if ( c.getPerson( ).equals( l.getPerson( ) ) == false ) {
			return null;
		}
		return c;
	}

}
