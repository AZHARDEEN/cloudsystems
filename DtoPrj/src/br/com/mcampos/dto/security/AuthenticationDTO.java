package br.com.mcampos.dto.security;

import java.io.Serializable;

public class AuthenticationDTO implements Serializable
{
    private Integer userId;
    private String sessionId;
    private String authenticationId;
    
    
    public AuthenticationDTO()
    {
        super();
    }

    public AuthenticationDTO( Integer userId, String sessionId, String authenticationId )
    {
        super();
        this.userId = userId;
        this.sessionId = sessionId;
        this.authenticationId = authenticationId;
    }

    public void setUserId( Integer userId )
    {
        this.userId = userId;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setSessionId( String sessionId )
    {
        this.sessionId = sessionId;
    }

    public String getSessionId()
    {
        return sessionId;
    }

    public void setAuthenticationId( String authenticationId )
    {
        this.authenticationId = authenticationId;
    }

    public String getAuthenticationId()
    {
        return authenticationId;
    }
}
