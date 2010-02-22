package br.com.mcampos.ejb.cloudsystem.anode.entity;

import br.com.mcampos.dto.anode.PenDTO;
import br.com.mcampos.ejb.entity.core.EntityCopyInterface;

import br.com.mcampos.sysutils.SysUtils;

import java.io.Serializable;

import java.security.InvalidParameterException;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = "Pen.findAll", query = "select o from Pen o" ), @NamedQuery( name = "Pen.findAvailablePensForForm", query = "select o from Pen o where ?1 NOT MEMBER OF o.forms" ) } )
@Table( name = "\"pen\"" )
public class Pen implements Serializable, EntityCopyInterface<PenDTO>
{
    private String id;
    private List<Form> forms;

    public Pen()
    {
    }

    public Pen( String pen_id_in )
    {
        setId( pen_id_in );
    }

    @Id
    @Column( name = "pen_id_ch", nullable = false )
    public String getId()
    {
        return id;
    }

    public void setId( String pen_id_in )
    {
        if ( SysUtils.isEmpty( pen_id_in ) )
            throw new InvalidParameterException( "Pen description could not be null or empty." );
        this.id = pen_id_in;
    }

    @ManyToMany( fetch = FetchType.LAZY )
    @JoinTable( name = "form_pen", joinColumns = @JoinColumn( name = "pen_id_ch", referencedColumnName = "pen_id_ch", nullable = false ), inverseJoinColumns = @JoinColumn( name = "frm_id_in", referencedColumnName = "frm_id_in", nullable = false ) )
    public List<Form> getForms()
    {
        return forms;
    }

    public void setForms( List<Form> formPenList )
    {
        this.forms = formPenList;
    }

    public Form addFormPen( Form formPen )
    {
        getForms().add( formPen );
        return formPen;
    }

    public Form removeFormPen( Form formPen )
    {
        getForms().remove( formPen );
        return formPen;
    }

    public PenDTO toDTO()
    {
        return new PenDTO( getId() );
    }
}
