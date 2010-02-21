package br.com.mcampos.ejb.cloudsystem.anode.entity;

import br.com.mcampos.dto.anode.PenDTO;
import br.com.mcampos.ejb.entity.core.EntityCopyInterface;

import br.com.mcampos.sysutils.SysUtils;

import java.io.Serializable;

import java.security.InvalidParameterException;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery( name = "Pen.findAll", query = "select o from Pen o" ) } )
@Table( name = "\"pen\"" )
public class Pen implements Serializable, EntityCopyInterface<PenDTO>
{
    private String id;
    private List<FormPen> forms;

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

    @OneToMany( mappedBy = "pen", fetch = FetchType.LAZY, cascade = CascadeType.ALL )
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

    public PenDTO toDTO()
    {
        return new PenDTO( getId() );
    }
}
