package br.com.mcampos.dto;

import br.com.mcampos.dto.core.DisplayNameDTO;
import br.com.mcampos.dto.user.UserDocumentDTO;
import br.com.mcampos.dto.user.attributes.DocumentTypeDTO;
import br.com.mcampos.dto.user.attributes.UserTypeDTO;
import br.com.mcampos.dto.user.login.LoginCredentialDTO;

import java.util.ArrayList;

public class RegisterDTO extends LoginCredentialDTO
{
	protected String name;
	protected ArrayList<UserDocumentDTO> documents;

	public RegisterDTO()
	{
		super();
	}

	public RegisterDTO( String name, String email, String password )
	{
		super( email, password, null, null );
		this.name = name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setDocuments( ArrayList<UserDocumentDTO> documents )
	{
		this.documents = documents;
	}

	public ArrayList<UserDocumentDTO> getDocuments()
	{
		if ( documents == null )
			documents = new ArrayList<UserDocumentDTO>();
		return documents;
	}
	
	public void addDocument ( DocumentTypeDTO type, String document )
	{
        UserDocumentDTO dto = new UserDocumentDTO ();
        dto.setDocumentType( type );
        dto.setCode( document );
		getDocuments().add( dto );
	}
}
