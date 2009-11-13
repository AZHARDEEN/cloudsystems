package br.com.mcampos.dto.user.login;

import java.io.Serializable;

public class LoginCredentialDTO implements Serializable {

    protected String document;
    protected String password;
    protected String remoteAddr;
    protected String remoteHost;
    
    public LoginCredentialDTO() {
        super();
    }

    public LoginCredentialDTO( String document, String password, String remoteAddr, String remoteHost ) {
        super();
        setDocument ( document );
        setPassword ( password );
        setRemoteAddr ( remoteAddr );
        setRemoteHost ( remoteHost );
    }

    public void setDocument( String document ) {
        this.document = document;
    }

	/**
	 * @return
	 */
	public String getDocument() {
        return document;
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
}
