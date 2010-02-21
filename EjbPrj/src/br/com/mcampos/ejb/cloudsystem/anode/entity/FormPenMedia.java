package br.com.mcampos.ejb.cloudsystem.anode.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery( name = "FormPenMedia.findAll", query = "select o from FormPenMedia o" ) } )
@Table( name = "\"form_pen_media\"" )
@IdClass( FormPenMediaPK.class )
public class FormPenMedia implements Serializable
{
    private Integer formId;
    private Integer mediaId;
    private String penId;
    private FormPen formPen;

    public FormPenMedia()
    {
    }

    public FormPenMedia( FormPen formPen, Integer med_id_in )
    {
        this.formPen = formPen;
        this.mediaId = med_id_in;
    }

    @Id
    @Column( name = "frm_id_in", nullable = false, insertable = false, updatable = false )
    public Integer getFormId()
    {
        return formId;
    }

    public void setFormId( Integer frm_id_in )
    {
        this.formId = frm_id_in;
    }

    @Id
    @Column( name = "med_id_in", nullable = false )
    public Integer getMediaId()
    {
        return mediaId;
    }

    public void setMediaId( Integer med_id_in )
    {
        this.mediaId = med_id_in;
    }

    @Id
    @Column( name = "pen_id_ch", nullable = false, insertable = false, updatable = false )
    public String getPenId()
    {
        return penId;
    }

    public void setPenId( String pen_id_in )
    {
        this.penId = pen_id_in;
    }

    @ManyToOne
    @JoinColumns( { @JoinColumn( name = "frm_id_in", referencedColumnName = "frm_id_in" ),
                    @JoinColumn( name = "pen_id_ch", referencedColumnName = "pen_id_ch" ) } )
    public FormPen getFormPen()
    {
        return formPen;
    }

    public void setFormPen( FormPen formPen )
    {
        this.formPen = formPen;
        if ( formPen != null ) {
            this.penId = formPen.getPenId();
            this.formId = formPen.getFormId();
        }
    }
}
