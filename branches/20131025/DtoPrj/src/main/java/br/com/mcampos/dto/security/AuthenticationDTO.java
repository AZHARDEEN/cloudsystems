package br.com.mcampos.dto.security;

public class AuthenticationDTO extends BasicSecurityDTO
{
    /**
     *
     */
    private static final long serialVersionUID = -382248201246261519L;
    private Integer userId;
    private String authenticationId;
    private Integer currentCompany;


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

    public void setCurrentCompany( Integer currentCompany )
    {
        this.currentCompany = currentCompany;
    }

    public Integer getCurrentCompany()
    {
        return currentCompany;
    }
}
