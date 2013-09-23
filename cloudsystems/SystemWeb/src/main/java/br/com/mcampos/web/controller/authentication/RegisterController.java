package br.com.mcampos.web.controller.authentication;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import br.com.mcampos.dto.RegisterDTO;
import br.com.mcampos.ejb.security.register.RegisterSession;
import br.com.mcampos.sysutils.CPF;
import br.com.mcampos.web.core.BaseCaptchaDialogController;

public class RegisterController extends BaseCaptchaDialogController<RegisterSession>
{
	private static final long serialVersionUID = -5382707561001744108L;

	private static final String errorTitle = "Registro no Sistema";

	@Wire
	private Textbox name;
	@Wire
	private Textbox email;
	@Wire
	private Textbox re_email;
	@Wire
	private Textbox cpf;
	@Wire
	private Textbox password;
	@Wire
	private Textbox repassword;

	public RegisterController( )
	{
		super( );
	}

	@Override
	protected void onOk( )
	{
		try {
			if ( getSession( ).register( createDTO( ) ) == true )
				gotoPage( "/public/registered.zul", true );
			else
				showErrorMessage( "Erro ao registar-se no sistema", errorTitle );
		}
		catch ( Exception e ) {
			showErrorMessage( "Erro ao registar-se no sistema", errorTitle );
		}
	}

	private RegisterDTO createDTO( )
	{
		RegisterDTO dto = new RegisterDTO( );
		dto.setDocument( cpf.getValue( ) );
		dto.setEmail( email.getValue( ) );
		dto.setName( name.getValue( ) );
		dto.setPassword( password.getValue( ) );
		return dto;
	}

	@Override
	protected Class<RegisterSession> getSessionClass( )
	{
		return RegisterSession.class;
	}

	@Override
	protected boolean validate( )
	{
		if ( validateFields( ) == false || super.validate( ) == false )
			return false;
		try {
			RegisterDTO dto = createDTO( );
			if ( getSession( ).hasLogin( dto ) ) {
				showErrorMessage( "Este email e/ou CPF não pode registrar-se no sistema", errorTitle );
				return false;
			}
			return true;
		}
		catch ( Exception e )
		{
			e = null;
			showErrorMessage( "Este email e/ou CPF não pode registrar-se no sistema", errorTitle );
			return false;
		}

	}

	private Textbox getName( )
	{
		return name;
	}

	private Textbox getEmail( )
	{
		return email;
	}

	private Textbox getRe_email( )
	{
		return re_email;
	}

	private Textbox getCpf( )
	{
		return cpf;
	}

	private Textbox getPassword( )
	{
		return password;
	}

	private Textbox getRepassword( )
	{
		return repassword;
	}

	private boolean validateFields( )
	{
		if ( getName( ).isValid( ) == false ) {
			showErrorMessage( "O campo Nome deve ser preenchido", errorTitle );
			getName( ).setFocus( true );
			return false;
		}
		if ( getEmail( ).isValid( ) == false ) {
			showErrorMessage( "O campo Email deve ser preenchido", errorTitle );
			getEmail( ).setFocus( true );
			return false;
		}
		if ( getCpf( ).isValid( ) == false ) {
			showErrorMessage( "O campo CPF deve ser preenchido", errorTitle );
			getCpf( ).setFocus( true );
			return false;
		}
		else if ( CPF.isValid( getCpf( ).getValue( ) ) == false ) {
			showErrorMessage( "O campo CPF é inválido", errorTitle );
			getCpf( ).setFocus( true );
			return false;
		}
		if ( getRe_email( ).isValid( ) == false ) {
			showErrorMessage( "O campo Confirmação de Email deve ser preenchido", errorTitle );
			getRe_email( ).setFocus( true );
			return false;
		}
		if ( getPassword( ).isValid( ) == false ) {
			showErrorMessage( "O campo Senha deve ser preenchido", errorTitle );
			getPassword( ).setFocus( true );
			return false;
		}
		if ( getRepassword( ).isValid( ) == false ) {
			showErrorMessage( "O campo Confirmação da Senha deve ser preenchido", errorTitle );
			getRepassword( ).setFocus( true );
			return false;
		}
		if ( getEmail( ).getValue( ).equals( getRe_email( ).getValue( ) ) == false ) {
			showErrorMessage( "Os EMails informados são diferentes", errorTitle );
			getEmail( ).setFocus( true );
			return false;
		}
		if ( getPassword( ).getValue( ).equals( getRepassword( ).getValue( ) ) == false ) {
			showErrorMessage( "As senhas informadas são diferentes", errorTitle );
			getPassword( ).setFocus( true );
			return false;
		}
		return true;
	}

	@Override
	public void doAfterCompose( Window comp ) throws Exception
	{
		super.doAfterCompose( comp );

		/*
		
		this.name.setValue( "Luzia Fonseca Azevedo" );
		this.cpf.setValue( "61908355115" );
		this.email.setValue( "tr081225@gmail.com" );
		this.re_email.setValue( "tr081225@gmail.com" );
		this.password.setValue( "123456" );
		this.repassword.setValue( "123456" );
		
		*/
	}

}
