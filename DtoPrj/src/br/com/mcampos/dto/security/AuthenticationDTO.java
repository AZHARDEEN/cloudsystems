package br.com.mcampos.dto.security;

public class AuthenticationDTO extends BasicSecurityDTO
{
    private Integer userId;
    private String authenticationId;


    public AuthenticationDTO()
    {
        super();
    }

    public void setUserId( Integer userId )
    {
        this.userId = userId;
    }

    public Integer getUserId()
    {
        return userId;
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
