package br.com.mcampos.dto.core;

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

    public void setId( Integer id )
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public void setDescription( String description )
    {
        this.description = description;
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
}
