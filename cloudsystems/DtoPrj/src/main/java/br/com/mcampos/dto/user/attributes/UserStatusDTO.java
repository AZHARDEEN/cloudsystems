package br.com.mcampos.dto.user.attributes;

import br.com.mcampos.dto.core.SimpleTableDTO;

public class UserStatusDTO extends SimpleTableDTO
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5517950894393679963L;
	public static final int statusOk = 1;
    public static final int statusInativo = 2;
    public static final int statusEmailNotValidated = 3;
    public static final int statusMaxLoginTryCount = 4;
    public static final int statusFullfillRecord = 5;
    public static final int statusExpiredPassword = 6;
    
    protected Boolean allowLogin;

    public UserStatusDTO( SimpleTableDTO simpleTableDTO )
    {
        super( simpleTableDTO );
    }

    public UserStatusDTO( Integer integer, String string )
    {
        super( integer, string );
    }

    public UserStatusDTO( Integer integer )
    {
        super( integer );
    }

    public UserStatusDTO()
    {
        super();
    }

    public void setAllowLogin( Boolean allowLogin )
    {
        this.allowLogin = allowLogin;
    }

    public Boolean getAllowLogin()
    {
        return allowLogin;
    }
}
