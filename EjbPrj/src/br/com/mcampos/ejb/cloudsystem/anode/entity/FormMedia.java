package br.com.mcampos.ejb.cloudsystem.anode.entity;

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
@NamedQueries( { @NamedQuery( name = "FormMedia.findAll", query = "select o from FormMedia o" ) } )
@Table( name = "\"form_media\"" )
@IdClass( FormMediaPK.class )
public class FormMedia implements Serializable
{
    private Integer form_id;
    private Integer media_id;
    private Form form;

    public FormMedia()
    {
    }

    public FormMedia( Form form, Integer med_id_in )
    {
        this.form = form;
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
}
