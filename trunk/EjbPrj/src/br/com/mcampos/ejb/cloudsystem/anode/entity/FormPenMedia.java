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
    private Integer form_id;
    private Integer media_id;
    private Integer pen_id;
    private FormPen formPen;

    public FormPenMedia()
    {
    }

    public FormPenMedia( FormPen formPen, Integer med_id_in )
    {
        this.formPen = formPen;
        this.media_id = med_id_in;
    }

    @Id
    @Column( name = "frm_id_in", nullable = false, insertable = false, updatable = false )
    public Integer getForm_id()
    {
        return form_id;
    }

    public void setForm_id( Integer frm_id_in )
    {
        this.form_id = frm_id_in;
    }

    @Id
    @Column( name = "med_id_in", nullable = false )
    public Integer getMedia_id()
    {
        return media_id;
    }

    public void setMedia_id( Integer med_id_in )
    {
        this.media_id = med_id_in;
    }

    @Id
    @Column( name = "pen_id_in", nullable = false, insertable = false, updatable = false )
    public Integer getPen_id()
    {
        return pen_id;
    }

    public void setPen_id( Integer pen_id_in )
    {
        this.pen_id = pen_id_in;
    }

    @ManyToOne
    @JoinColumns( { @JoinColumn( name = "frm_id_in", referencedColumnName = "frm_id_in" ),
                    @JoinColumn( name = "pen_id_in", referencedColumnName = "pen_id_in" ) } )
    public FormPen getFormPen()
    {
        return formPen;
    }

    public void setFormPen( FormPen formPen )
    {
        this.formPen = formPen;
        if ( formPen != null ) {
            this.pen_id = formPen.getPen_id();
            this.form_id = formPen.getForm_id();
        }
    }
}
