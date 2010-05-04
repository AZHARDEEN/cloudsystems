package br.com.mcampos.dto.security;

import java.io.Serializable;

public class BasicSecurityDTO implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5553506158590594461L;
	protected String sessionId;
    protected String remoteAddr;
    protected String remoteHost;


    public BasicSecurityDTO()
    {
        super();
    }

    public void setSessionId( String sessionId )
    {
        this.sessionId = sessionId;
    }

    public String getSessionId()
    {
        return sessionId;
    }

    public void setRemoteAddr( String remoteAddr )
    {
        this.remoteAddr = remoteAddr;
    }

    public String getRemoteAddr()
    {
        return remoteAddr;
    }

    public void setRemoteHost( String remoteHost )
    {
        this.remoteHost = remoteHost;
    }

    public String getRemoteHost()
    {
        return remoteHost;
    }
}
