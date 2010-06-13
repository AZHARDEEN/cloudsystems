package br.com.mcampos.ejb.cloudsystem.anoto.form;


import br.com.mcampos.dto.anoto.FormDTO;
import br.com.mcampos.ejb.entity.core.EntityCopyInterface;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@NamedQueries( { @NamedQuery( name = "Form.findAll", query = "select o from AnotoForm o" ),
                 @NamedQuery( name = "Form.nextId", query = "select MAX(o.id) from AnotoForm o" ) } )
@Table( name = "anoto_form" )
public class AnotoForm implements Serializable, EntityCopyInterface<FormDTO>, Comparable<AnotoForm>
{
    @Id
    @Column( name = "frm_id_in", nullable = false )
    private Integer id;

    @Column( name = "frm_description_ch", nullable = false )
    private String description;

    @Column( name = "frm_ip_ch", nullable = false )
    private String application;

    @Column( name = "frm_insert_dt", nullable = false, insertable = true, updatable = false )
    @Temporal( TemporalType.DATE )
    private Date insertDate;

    @Column( name = "frm_icr_image_bt" )
    private Boolean icrImage;

    @Column( name = "frm_image_filepath_ch", length = 1024 )
    private String imagePath;

    @Column( name = "frm_concat_pgc_bt" )
    private Boolean concatenatePgc;


    public AnotoForm()
    {
    }

    public AnotoForm( Integer id, String application, String description )
    {
        setDescription( description );
        setId( id );
        setApplication( application );
    }

    public AnotoForm( Integer id, String application )
    {
        setId( id );
        setApplication( application );
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public String getApplication()
    {
        return application;
    }

    public void setApplication( String application )
    {
        this.application = application;
    }

    public FormDTO toDTO()
    {
        FormDTO dto = new FormDTO( this.getId(), this.getDescription() );

        dto.setApplication( this.getApplication() );
        dto.setIcrImage( getIcrImage() );
        dto.setImagePath( getImagePath() );
        dto.setConcatenatePgc( getConcatenatePgc() );
        return dto;
    }

    public void setInsertDate( Date insertDate )
    {
        this.insertDate = insertDate;
    }

    public Date getInsertDate()
    {
        return insertDate;
    }

    public int compareTo( AnotoForm o )
    {
        return getId().compareTo( o.getId() );
    }

    public void setIcrImage( Boolean icrImage )
    {
        this.icrImage = icrImage;
    }

    public Boolean getIcrImage()
    {
        return icrImage;
    }

    public void setImagePath( String imagePath )
    {
        this.imagePath = imagePath;
    }

    public String getImagePath()
    {
        return imagePath;
    }

    public void setConcatenatePgc( Boolean concatenatePgc )
    {
        this.concatenatePgc = concatenatePgc;
    }

    public Boolean getConcatenatePgc()
    {
        return concatenatePgc;
    }
}
