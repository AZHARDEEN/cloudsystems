package br.com.mcampos.ejb.cloudsystem.anode.entity;

import java.io.Serializable;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery( name = "FormPen.findAll", query = "select o from FormPen o" ),
                 @NamedQuery( name = "FormPen.findAvailableFormsForPen", query = "select o from FormPen o where o.pen.id <> ?1" ) } )
@Table( name = "\"form_pen\"" )
@IdClass( FormPenPK.class )
public class FormPen implements Serializable
{
    private Integer formId;
    private String penId;
    private Pen pen;
    private Form form;
    private List<FormPenMedia> medias;

    public FormPen()
    {
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
    @JoinColumn( name = "pen_id_ch" )
    public Pen getPen()
    {
        return pen;
    }

    public void setPen( Pen pen )
    {
        this.pen = pen;
        if ( pen != null ) {
            this.penId = pen.getId();
        }
    }

    @ManyToOne
    @JoinColumn( name = "frm_id_in" )
    public Form getForm()
    {
        return form;
    }

    public void setForm( Form form )
    {
        this.form = form;
        if ( form != null ) {
            this.formId = form.getId();
        }
    }

    @OneToMany( mappedBy = "formPen" )
    public List<FormPenMedia> getMedias()
    {
        return medias;
    }

    public void setMedias( List<FormPenMedia> formPenMediaList )
    {
        this.medias = formPenMediaList;
    }

    public FormPenMedia addFormPenMedia( FormPenMedia formPenMedia )
    {
        getMedias().add( formPenMedia );
        formPenMedia.setFormPen( this );
        return formPenMedia;
    }

    public FormPenMedia removeFormPenMedia( FormPenMedia formPenMedia )
    {
        getMedias().remove( formPenMedia );
        formPenMedia.setFormPen( null );
        return formPenMedia;
    }
}
