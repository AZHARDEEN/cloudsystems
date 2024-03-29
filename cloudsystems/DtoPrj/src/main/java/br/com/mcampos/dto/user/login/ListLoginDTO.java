package br.com.mcampos.dto.user.login;

import br.com.mcampos.dto.core.DisplayNameDTO;

import br.com.mcampos.dto.user.attributes.UserStatusDTO;

public class ListLoginDTO extends DisplayNameDTO
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4025788703084454023L;
	Integer id;
    String name;
    UserStatusDTO userStatus;
    
    
    
    public ListLoginDTO()
    {
        super();
    }

    public ListLoginDTO( Integer id, String name, UserStatusDTO userStatus )
    {
        super();
        setId ( id );
        setName ( name );
        setUserStatus ( userStatus );
    }

    public String getDisplayName()
    {
        return getName ();
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setUserStatus( UserStatusDTO userStatus )
    {
        this.userStatus = userStatus;
    }

    public UserStatusDTO getUserStatus()
    {
        return userStatus;
    }
}
