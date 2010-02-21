package br.com.mcampos.ejb.cloudsystem.anode.entity;

import br.com.mcampos.ejb.cloudsystem.media.entity.Media;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.criteria.Fetch;

@Entity
@NamedQueries( { @NamedQuery( name = "FormMedia.findAll", query = "select o from FormMedia o" ) } )
@Table( name = "\"form_media\"" )
@IdClass( FormMediaPK.class )
public class FormMedia implements Serializable
{
    private Integer formId;
    private Integer mediaId;
    private Form form;
    private Media media;

    public FormMedia()
    {
    }

    public FormMedia( Form form, Media media )
    {
        setForm( form );
        setMedia( media );
    }

    @Id
    @Column( name = "frm_id_in", nullable = false, insertable = false, updatable = false )
    public Integer getFormId()
    {
        return formId;
    }

    public FormMedia setFormId( Integer frm_id_in )
    {
        this.formId = frm_id_in;
        return this;
    }

    @Id
    @Column( name = "med_id_in", nullable = false, insertable = false, updatable = false )
    public Integer getMediaId()
    {
        return mediaId;
    }

    public FormMedia setMediaId( Integer med_id_in )
    {
        this.mediaId = med_id_in;
        return this;
    }

    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "frm_id_in" )
    public Form getForm()
    {
        return form;
    }

    public FormMedia setForm( Form form )
    {
        this.form = form;
        setFormId( form != null ? form.getId() : 0 );
        return this;
    }

    public FormMedia setMedia( Media media )
    {
        this.media = media;
        setMediaId( media != null ? media.getId() : 0 );
        return this;
    }

    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "med_id_in" )
    public Media getMedia()
    {
        return media;
    }
}
