package br.com.mcampos.ejb.cloudsystem.media.entity;

import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.entity.core.EntityCopyInterface;
import br.com.mcampos.sysutils.SysUtils;

import java.io.Serializable;

import java.security.InvalidParameterException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery( name = "Media.findAll", query = "select o from Media o" ) } )
@Table( name = "\"media\"" )
public class Media implements Serializable, EntityCopyInterface<MediaDTO>, Comparable<Media>
{
    private Integer id;
    private String mimeType;
    private String name;
    private byte[] object;
    private Integer size;
    private String format;

    public Media()
    {
    }


    @Id
    @Column( name = "med_id_in", nullable = false )
    @SequenceGenerator( name = "mediaIdGenerator", sequenceName = "seq_media", allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "mediaIdGenerator" )
    public Integer getId()
    {
        return id;
    }

    public Media setId( Integer id )
    {
        this.id = id;
        return this;
    }

    @Column( name = "med_mime_ch" )
    public String getMimeType()
    {
        return mimeType;
    }

    public Media setMimeType( String mime )
    {
        this.mimeType = mime;
        return this;
    }

    @Column( name = "med_name_ch", nullable = false )
    public String getName()
    {
        return name;
    }

    public Media setName( String name )
    {
        if ( SysUtils.isEmpty( name ) )
            throw new InvalidParameterException( "Media name could not be null" );
        this.name = name;
        return this;
    }

    @Lob
    @Column( name = "med_object_bin", nullable = false )
    public byte[] getObject()
    {
        return object;
    }

    public Media setObject( byte[] object )
    {
        this.object = object;
        setSize( object == null ? 0 : object.length );
        return this;
    }

    public MediaDTO toDTO()
    {
        MediaDTO target = new MediaDTO( getId(), getName() );

        /*
         * Não será retornado o objeto nesta cópia.
         */
        target.setMimeType( getMimeType() );
        target.setName( getName() );
        target.setFormat( getFormat() );
        target.setSize( getSize() );
        return target;
    }

    protected Media setSize( Integer size )
    {
        this.size = size;
        return this;
    }

    @Column( name = "med_size_in" )
    public Integer getSize()
    {
        return size;
    }

    public Media setFormat( String format )
    {
        this.format = format;
        return this;
    }

    @Column( name = "med_format_ch" )
    public String getFormat()
    {
        return format;
    }

    public int compareTo( Media o )
    {
        if ( o == null )
            return -1;
        if ( getId() == null )
            return 1;
        return getId().compareTo( o.getId() );
    }

    @Override
    public int hashCode()
    {
        return getId() == null ? 0 : getId().hashCode();
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj == null || ( obj instanceof Media ) == false )
            return false;
        if ( getId() == null )
            return false;
        return getId().equals( ( ( Media )obj ).getId() );
    }
}
