package br.com.mcampos.dto;

import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.dto.user.attributes.DocumentTypeDTO;

import br.com.mcampos.dto.security.LoginCredentialDTO;

import java.io.Serializable;

import java.util.ArrayList;

public class RegisterDTO extends LoginCredentialDTO
{
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
