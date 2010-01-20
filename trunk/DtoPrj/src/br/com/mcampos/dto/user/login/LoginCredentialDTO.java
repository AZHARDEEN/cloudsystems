package br.com.mcampos.dto.user.login;

import br.com.mcampos.dto.user.UserDocumentDTO;

import br.com.mcampos.dto.user.attributes.DocumentTypeDTO;

import java.io.Serializable;

import java.util.ArrayList;

public class LoginCredentialDTO implements Serializable {

    protected String password;
    protected String remoteAddr;
    protected String remoteHost;
    protected ArrayList<UserDocumentDTO> documents;
    
    public LoginCredentialDTO() {
        super();
    }

    public LoginCredentialDTO( String password, String remoteAddr, String remoteHost ) {
        super();
        setPassword ( password );
        setRemoteAddr ( remoteAddr );
        setRemoteHost ( remoteHost );
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setRemoteAddr( String remoteAddr ) {
        this.remoteAddr = remoteAddr;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteHost( String remoteHost ) {
        this.remoteHost = remoteHost;
    }

    public String getRemoteHost() {
        return remoteHost;
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
