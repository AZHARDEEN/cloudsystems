package br.com.mcampos.dto.anoto;


import br.com.mcampos.sysutils.SysUtils;

import java.io.Serializable;

import java.security.InvalidParameterException;


public class PenDTO implements Comparable<PenDTO>, Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = -5445481184895354233L;
    private String id;
    private String description;
    private LinkedUserDTO user;


    public PenDTO( String id, String description )
    {
        setId( id );
        setDescription( description );
    }

    public PenDTO()
    {

    }

    public void setId( String id )
    {
        if ( SysUtils.isEmpty( id ) )
            throw new InvalidParameterException( "Pen identification could not be empty" );
        this.id = id;
    }

    public String getId()
    {
        return id;
    }

    public int compareTo( PenDTO o )
    {
        if ( o == null )
            return 1;
        if ( SysUtils.isEmpty( getId() ) )
            return -1;
        return getId().compareTo( o.getId() );
    }

    @Override
    public int hashCode()
    {
        return SysUtils.isEmpty( id ) ? 0 : getId().hashCode();
    }

    @Override
    public String toString()
    {
        return getId();
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj == null || ( obj instanceof PenDTO ) == false )
            return false;
        return getId().equals( ( ( PenDTO )obj ).getId() );
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public void setUser( LinkedUserDTO user )
    {
        this.user = user;
        if ( this.user != null )
            this.user.setLinkCode( this );
    }

    public LinkedUserDTO getUser()
    {
        return user;
    }
}
