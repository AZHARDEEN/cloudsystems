package br.com.mcampos.dto.core;

import br.com.mcampos.sysutils.SysUtils;

import java.security.InvalidParameterException;

public abstract class SimpleTableDTO extends DisplayNameDTO implements SimpleTable, Comparable<SimpleTableDTO>
{
    protected Integer id;
    protected String description;


    public SimpleTableDTO()
    {
        super();
    }

    public SimpleTableDTO( Integer id )
    {
        super();
        this.id = id;
    }

    public SimpleTableDTO( Integer id, String description )
    {
        super();
        init( id, description );
    }

    public SimpleTableDTO( SimpleTableDTO fromObj )
    {
        super();
        init( fromObj.getId(), fromObj.getDescription() );
    }

    protected void init( Integer id, String description )
    {
        this.id = id;
        this.description = description;
    }

    public SimpleTableDTO setId( Integer id )
    {
        if ( SysUtils.isZero( id ) )
            throw new InvalidParameterException( "O id não pode ser nulo ou zero" );
        this.id = id;
        return this;
    }

    public Integer getId()
    {
        return id;
    }

    public SimpleTableDTO setDescription( String description )
    {
        this.description = description;
        return this;
    }

    public String getDescription()
    {
        return description;
    }

    public String getDisplayName()
    {
        return "" + getId() + " - " + getDescription();
    }

    public int compareTo( SimpleTableDTO o )
    {
        if ( o == null || o.getId() == null )
            return 1;
        if ( getId() == null )
            return -1;
        return getId().compareTo( o.getId() );
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj == null )
            return false;
        if ( obj instanceof SimpleTableDTO ) {
            SimpleTableDTO o = ( SimpleTableDTO )obj;

            return getId().equals( o.getId() );
        }
        else
            return false;
    }

    @Override
    public String toString()
    {
        return getDescription();
    }

    @Override
    public int hashCode()
    {
        return SysUtils.isZero( getId() ) ? 0 : getId().hashCode();
    }
}
