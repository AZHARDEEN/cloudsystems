package br.com.mcampos.web.controller;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import br.com.mcampos.ejb.security.Login;
import br.com.mcampos.ejb.security.LoginSession;
import br.com.mcampos.ejb.user.company.collaborator.Collaborator;
import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseDialogController;
import br.com.mcampos.web.core.LoggedInterface;

public class ChangePasswordController extends BaseDialogController<LoginSession> implements LoggedInterface
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
			Messagebox.show( "Sua senha foi alterada com sucesso. Um email de confirmação será enviado para sua caixa como seguraça",
					"Alterar Senha", Messagebox.OK, Messagebox.INFORMATION );
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
		String aux;

		aux = SysUtils.trim( getOld_password( ).getValue( ) );
		if ( SysUtils.isEmpty( aux ) ) {
			showErrorMessage( "A senha antiga está em branco. Por favor, informe a senha antiga", errorTitle );
			getOld_password( ).setFocus( true );
			return false;
		}
		if ( getSession( ).verifyPassword( getLoggedUser( ).getId( ), getOld_password( ).getValue( ) ) == false ) {
			showErrorMessage( "A senha atual é inválida, ou seja, não é a sua senha. Por favor informe a senha corretamente", errorTitle );
			getOld_password( ).setFocus( true );
			return false;
		}
		aux = SysUtils.trim( getPassword( ).getValue( ) );
		if ( SysUtils.isEmpty( aux ) ) {
			showErrorMessage( "A nova está em branco. Por favor, informe a nova senha.", errorTitle );
			getPassword( ).setFocus( true );
			return false;
		}
		aux = SysUtils.trim( getRe_password( ).getValue( ) );
		if ( getPassword( ).getValue( ).equals( getRe_password( ).getValue( ) ) == false ) {
			showErrorMessage( "As novas senhas não são iguais. Verifique se foi digitado corretamente.", errorTitle );
			getPassword( ).setFocus( true );
			return false;
		}
		if ( getOld_password( ).getValue( ).equals( getRe_password( ).getValue( ) ) ) {
			showErrorMessage( "A nova senha é igual a antiga", errorTitle );
			getOld_password( ).setFocus( true );
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

	@Listen( "onClick=#btnPasswdHint" )
	public void onHint( MouseEvent evt )
	{
		String hint = getSession( ).randomPassword( 8 );
		Messagebox.show( hint, "Sugestão de Senha", Messagebox.OK, Messagebox.INFORMATION );
		if ( evt != null )
			evt.stopPropagation( );
	}

}
