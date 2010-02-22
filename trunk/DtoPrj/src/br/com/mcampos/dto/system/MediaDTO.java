package br.com.mcampos.dto.system;

import br.com.mcampos.dto.core.SimpleTableDTO;

import java.io.Serializable;

public class MediaDTO implements Comparable<MediaDTO>, Serializable
{
    protected Integer id;
    private String mimeType;
    private String name;
    private byte[] object;
    private Integer size;
    private String format;


    public MediaDTO()
    {
        super();
    }

    public MediaDTO( Integer id, String name )
    {
        super();
        setId( id ).setName( name );
    }


    public MediaDTO setMimeType( String mimeType )
    {
        this.mimeType = mimeType;
        return this;
    }

    public String getMimeType()
    {
        return mimeType;
    }

    public MediaDTO setName( String name )
    {
        this.name = name;
        return this;
    }

    public String getName()
    {
        return name;
    }

    public MediaDTO setObject( byte[] object )
    {
        this.object = object;
        return this;
    }

    public byte[] getObject()
    {
        return object;
    }

    public MediaDTO setSize( Integer size )
    {
        this.size = size;
        return this;
    }

    public Integer getSize()
    {
        return size;
    }

    public MediaDTO setFormat( String format )
    {
        this.format = format;
        return this;
    }

    public String getFormat()
    {
        return format;
    }

    public MediaDTO setId( Integer id )
    {
        this.id = id;
        return this;
    }

    public Integer getId()
    {
        if ( id == null )
            id = 0;
        return id;
    }

    public int compareTo( MediaDTO o )
    {
        if ( o == null )
            return 1;
        return getId().compareTo( o.getId() );
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj == null || ( obj instanceof MediaDTO ) == false )
            return false;
        return getId().equals( ( ( MediaDTO )obj ).getId() );
    }
}
