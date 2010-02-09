package br.com.mcampos.dto.security;

import br.com.mcampos.dto.user.UserDocumentDTO;

import br.com.mcampos.dto.user.attributes.DocumentTypeDTO;

import java.util.ArrayList;

public class LoginCredentialDTO extends BasicSecurityDTO
{

    protected String password;
    protected ArrayList<UserDocumentDTO> documents;

    public LoginCredentialDTO()
    {
        super();
    }

    public LoginCredentialDTO( String password, String remoteAddr, String remoteHost )
    {
        super();
        setPassword( password );
        setRemoteAddr( remoteAddr );
        setRemoteHost( remoteHost );
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public String getPassword()
    {
        return password;
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

    public void addDocument( DocumentTypeDTO type, String document )
    {
        UserDocumentDTO dto = new UserDocumentDTO();
        dto.setDocumentType( type );
        dto.setCode( document );
        getDocuments().add( dto );
    }
}
