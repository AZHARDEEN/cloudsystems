package br.com.mcampos.dto.anoto;


import br.com.mcampos.dto.user.ListUserDTO;

import java.io.Serializable;

public class LinkedUserDTO implements Serializable
{
    private transient Object linkCode;
    private ListUserDTO user;


    public LinkedUserDTO()
    {
        super();
    }

    public void setLinkCode( Object linkCode )
    {
        this.linkCode = linkCode;
    }

    public Object getLinkCode()
    {
        return linkCode;
    }

    public void setUser( ListUserDTO user )
    {
        this.user = user;
    }

    public ListUserDTO getUser()
    {
        return user;
    }

    @Override
    public String toString()
    {
        return getUser().toString();
    }
}
