package br.com.mcampos.ejb.cloudsystem.anode.entity;


import br.com.mcampos.dto.anoto.FormMediaDTO;
import br.com.mcampos.ejb.cloudsystem.anode.entity.key.FormMediaPK;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.entity.core.EntityCopyInterface;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = "FormMedia.findAll", query = "select o from FormMedia o" ), @NamedQuery( name = FormMedia.formGetFiles, query = "select o from FormMedia o where o.form = ?1" ) } )
@Table( name = "form_media" )
@IdClass( FormMediaPK.class )
public class FormMedia implements Serializable, EntityCopyInterface<FormMediaDTO>
{
    public static final String formGetFiles = "FormMedia.formGetFiles";
    @Id
    @Column( name = "frm_id_in", nullable = false, insertable = false, updatable = false )
    private Integer formId;
    @Id
    @Column( name = "med_id_in", nullable = false, insertable = false, updatable = false )
    private Integer mediaId;

    @ManyToOne
    @JoinColumn( name = "frm_id_in" )
    private AnotoForm form;

    @ManyToOne
    @JoinColumn( name = "med_id_in" )
    private Media media;


    public FormMedia()
    {
    }

    public FormMedia( AnotoForm form, Media media )
    {
        setForm( form );
        setMedia( media );
    }

    public Integer getFormId()
    {
        return formId;
    }

    public void setFormId( Integer frm_id_in )
    {
        this.formId = frm_id_in;
    }

    public Integer getMediaId()
    {
        return mediaId;
    }

    public void setMediaId( Integer med_id_in )
    {
        this.mediaId = med_id_in;
    }

    public void setForm( AnotoForm form )
    {
        this.form = form;
        this.setFormId( form != null ? form.getId() : 0 );
    }

    public AnotoForm getForm()
    {
        return form;
    }

    public void setMedia( Media media )
    {
        this.media = media;
        setMediaId( media != null ? media.getId() : 0 );
    }

    public Media getMedia()
    {
        return media;
    }

    public FormMediaDTO toDTO()
    {
        return new FormMediaDTO( getForm().toDTO(), getMedia().toDTO() );
    }
}
