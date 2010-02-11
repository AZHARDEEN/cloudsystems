package br.com.mcampos.ejb.entity.system;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
    private String object;
    private String url;

    public Media()
    {
    }

    @Column( name = "med_description_tx" )
    public String getDescription()
    {
        return description;
    }

    public void setDescription( String med_description_tx )
    {
        this.description = med_description_tx;
    }

    @Column( name = "med_embedded_code_tx" )
    public String getEmbeddedCode()
    {
        return embeddedCode;
    }

    public void setEmbeddedCode( String med_embedded_code_tx )
    {
        this.embeddedCode = med_embedded_code_tx;
    }

    @Id
    @Column( name = "med_id_in", nullable = false )
    public Integer getId()
    {
        return id;
    }

    public void setId( Integer med_id_in )
    {
        this.id = med_id_in;
    }

    @Column( name = "med_mime_ch" )
    public String getMimeType()
    {
        return mimeType;
    }

    public void setMimeType( String med_mime_ch )
    {
        this.mimeType = med_mime_ch;
    }

    @Column( name = "med_name_ch", nullable = false )
    public String getName()
    {
        return name;
    }

    public void setName( String med_name_ch )
    {
        this.name = med_name_ch;
    }

    @Column( name = "med_object_bin" )
    public String getObject()
    {
        return object;
    }

    public void setObject( String med_object_bin )
    {
        this.object = med_object_bin;
    }

    @Column( name = "med_url_ch" )
    public String getUrl()
    {
        return url;
    }

    public void setUrl( String med_url_ch )
    {
        this.url = med_url_ch;
    }
}
