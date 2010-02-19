package br.com.mcampos.ejb.cloudsystem.anode.entity;

import java.io.Serializable;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery( name = "Pen.findAll", query = "select o from Pen o" ) } )
@Table( name = "\"pen\"" )
public class Pen implements Serializable
{
    private String description;
    private Integer id;
    private List<FormPen> forms;

    public Pen()
    {
    }

    public Pen( String pen_description_ch, Integer pen_id_in )
    {
        this.description = pen_description_ch;
        this.id = pen_id_in;
    }

    @Column( name = "pen_description_ch" )
    public String getDescription()
    {
        return description;
    }

    public void setDescription( String pen_description_ch )
    {
        this.description = pen_description_ch;
    }

    @Id
    @Column( name = "pen_id_in", nullable = false )
    public Integer getId()
    {
        return id;
    }

    public void setId( Integer pen_id_in )
    {
        this.id = pen_id_in;
    }

    @OneToMany( mappedBy = "pen" )
    public List<FormPen> getForms()
    {
        return forms;
    }

    public void setForms( List<FormPen> formPenList )
    {
        this.forms = formPenList;
    }

    public FormPen addFormPen( FormPen formPen )
    {
        getForms().add( formPen );
        formPen.setPen( this );
        return formPen;
    }

    public FormPen removeFormPen( FormPen formPen )
    {
        getForms().remove( formPen );
        formPen.setPen( null );
        return formPen;
    }
}
