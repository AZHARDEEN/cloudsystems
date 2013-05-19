package br.com.mcampos.web.inep.controller;

import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import br.com.mcampos.sysutils.SysUtils;
import br.com.mcampos.web.core.BaseDialogWindow;

public class NewRevisorWindow extends BaseDialogWindow
{
	private static final long serialVersionUID = -4750285281120096962L;

	private Textbox name;
	private Textbox email;
	private Textbox cpf;
	private Checkbox coordinator;

	@Override
	protected boolean validate( )
	{
		if ( SysUtils.isEmpty( getName( ).getText( ) ) ) {
			Messagebox.show( "O nome não pode estar vazio ou em branco" );
			getName( ).setFocus( true );
			return false;
		}
		if ( SysUtils.isEmpty( getCpf( ).getText( ) ) && SysUtils.isEmpty( getEmail( ).getText( ) ) ) {
			Messagebox.show( "Por favor, preencha ou o CPF ou o email" );
			getEmail( ).setFocus( true );
			return false;
		}
		return true;
	}

	public Textbox getName( )
	{
		if ( name == null )
			name = (Textbox) getFellow( "name" );
		return name;
	}

	public Textbox getEmail( )
	{
		if ( email == null )
			email = (Textbox) getFellow( "email" );
		return email;
	}

	public Textbox getCpf( )
	{
		if ( cpf == null )
			cpf = (Textbox) getFellow( "cpf" );
		return cpf;
	}

	public Checkbox getCoordinator( )
	{
		if ( coordinator == null )
			coordinator = (Checkbox) getFellow( "coordinator" );
		return coordinator;
	}

}
