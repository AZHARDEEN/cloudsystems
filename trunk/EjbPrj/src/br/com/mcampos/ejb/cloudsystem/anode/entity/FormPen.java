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
@NamedQueries( { @NamedQuery( name = "FormPen.findAll", query = "select o from FormPen o" ) } )
@Table( name = "\"form_pen\"" )
@IdClass( FormPenPK.class )
public class FormPen implements Serializable
{
    private Integer form_id;
    private Integer pen_id;
    private Pen pen;
    private Form form;
    private List<FormPenMedia> medias;

    public FormPen()
    {
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
    @JoinColumn( name = "pen_id_in" )
    public Pen getPen()
    {
        return pen;
    }

    public void setPen( Pen pen )
    {
        this.pen = pen;
        if ( pen != null ) {
            this.pen_id = pen.getId();
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
            this.form_id = form.getId();
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
