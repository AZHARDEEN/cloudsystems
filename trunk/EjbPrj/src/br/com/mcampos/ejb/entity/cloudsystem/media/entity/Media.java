package br.com.mcampos.ejb.entity.cloudsystem.media.entity;

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
public class Media implements Serializable
{
    private String description;
    private String embeddedCode;
    private Integer id;
    private String mimeType;
    private String name;
    private Byte[] object;
    private String url;

    public Media()
    {
    }

    @Column( name = "med_description_tx" )
    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    @Column( name = "med_embedded_code_tx" )
    public String getEmbeddedCode()
    {
        return embeddedCode;
    }

    public void setEmbeddedCode( String embeddedCode )
    {
        this.embeddedCode = embeddedCode;
    }

    @Id
    @Column( name = "med_id_in", nullable = false )
    @SequenceGenerator( name = "mediaIdGenerator", sequenceName = "seq_media", allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "mediaIdGenerator" )
    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        if ( SysUtils.isZero( id ) )
            throw new InvalidParameterException( "Media ID could not be null" );
        this.id = id;
    }

    @Column( name = "med_mime_ch" )
    public String getMimeType()
    {
        return mimeType;
    }

    public void setMimeType( String mime )
    {
        this.mimeType = mime;
    }

    @Column( name = "med_name_ch", nullable = false )
    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        if ( SysUtils.isEmpty( name ) )
            throw new InvalidParameterException( "Media name could not be null" );
        this.name = name;
    }

    @Lob
    @Column( name = "med_object_bin" )
    public Byte[] getObject()
    {
        return object;
    }

    public void setObject( Byte[] object )
    {
        this.object = object;
    }

    @Column( name = "med_url_ch" )
    public String getUrl()
    {
        return url;
    }

    public void setUrl( String url )
    {
        this.url = url;
    }
}
