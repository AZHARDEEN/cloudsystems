package br.com.mcampos.dto;

import br.com.mcampos.dto.security.LoginCredentialDTO;

public class RegisterDTO extends LoginCredentialDTO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1322649437759313194L;
	protected String name;

	public RegisterDTO()
	{
		super();
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
}
