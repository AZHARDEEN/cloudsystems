package br.com.mcampos.dto.user;

import br.com.mcampos.dto.core.DisplayNameDTO;
import br.com.mcampos.dto.user.attributes.UserTypeDTO;

import br.com.mcampos.sysutils.SysUtils;

import java.sql.Timestamp;

public class ListUserDTO extends DisplayNameDTO
{
    Integer id;
    String name;
    String nickName;
    UserTypeDTO userType;
    Timestamp lastUpdate;
    
    
    
    public ListUserDTO()
    {
        super();
    }

    public String getDisplayName()
    {
        if ( getUserType().getId() == 1 ) 
        {
            return getName ();
        }
        else 
        {
            return SysUtils.isEmpty( getNickName() ) ? getName() : getNickName();
        }
    }

    public void setId( Integer userId )
    {
        this.id = userId;
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

    public void setUserType( UserTypeDTO userType )
    {
        this.userType = userType;
    }

    public UserTypeDTO getUserType()
    {
        return userType;
    }

    public void setLastUpdate( Timestamp lastUpdate )
    {
        this.lastUpdate = lastUpdate;
    }

    public Timestamp getLastUpdate()
    {
        return lastUpdate;
    }

    public void setNickName( String nickName )
    {
        this.nickName = nickName;
    }

    public String getNickName()
    {
        return nickName;
    }
}
