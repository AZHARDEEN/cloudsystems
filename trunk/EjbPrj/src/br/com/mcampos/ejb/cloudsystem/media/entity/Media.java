package br.com.mcampos.ejb.cloudsystem.media.entity;


import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.ejb.entity.core.EntityCopyInterface;
import br.com.mcampos.sysutils.SysUtils;

import java.io.Serializable;

import java.security.InvalidParameterException;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table( name = "media" )
public class Media implements Serializable, EntityCopyInterface<MediaDTO>, Comparable<Media>
{
    @Id
    @Column( name = "med_id_in", nullable = false )
    @SequenceGenerator( name = "mediaIdGenerator", sequenceName = "seq_media", allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "mediaIdGenerator" )
    private Integer id;


    @Column( name = "med_mime_ch", length = 64 )
    private String mimeType;


    @Column( name = "med_name_ch", nullable = false, length = 128 )
    private String name;

    @Basic( fetch = FetchType.LAZY, optional = false )
    @Lob
    @Column( name = "med_object_bin", nullable = false )
    private byte[] object;

    @Column( name = "med_size_in" )
    private Integer size;

    @Column( name = "med_format_ch" )
    private String format;

    public Media()
    {
    }

    public Media( Integer id )
    {
        setId( id );
    }


    public Integer getId()
    {
        return id;
    }

    public Media setId( Integer id )
    {
        this.id = id;
        return this;
    }

    public String getMimeType()
    {
        return mimeType;
    }

    public Media setMimeType( String mime )
    {
        this.mimeType = mime;
        return this;
    }

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

    @Override
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

    public Integer getSize()
    {
        return size;
    }

    public Media setFormat( String format )
    {
        this.format = format;
        return this;
    }

    public String getFormat()
    {
        return format;
    }

    @Override
    public int compareTo( Media o )
    {
        if ( o == null )
            return -1;
        if ( getId() == null )
            return 1;
        return getId().compareTo( o.getId() );
    }
}
