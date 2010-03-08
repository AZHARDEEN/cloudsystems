package br.com.mcampos.ejb.cloudsystem.anode.entity;


import br.com.mcampos.dto.anoto.PenDTO;
import br.com.mcampos.ejb.entity.core.EntityCopyInterface;
import br.com.mcampos.sysutils.SysUtils;

import java.io.Serializable;

import java.security.InvalidParameterException;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@NamedQueries( { @NamedQuery( name = "Pen.findAll", query = "select o from AnotoPen o" ) } )
@Table( name = "anoto_pen" )
public class AnotoPen implements Serializable, EntityCopyInterface<PenDTO>
{
    @Id
    @Column( name = "pen_id_ch", nullable = false )
    private String id;

    @Column( name = "pen_insert_dt", nullable = false )
    @Temporal( TemporalType.DATE )
    private Date insertDate;

    @Column( name = "pen_description_ch", nullable = false )
    private String description;


    public AnotoPen()
    {
    }

    public AnotoPen( String pen_id_in, String description )
    {
        setId( pen_id_in );
        setDescription( description );
    }

    public String getId()
    {
        return id;
    }

    public void setId( String pen_id_in )
    {
        if ( SysUtils.isEmpty( pen_id_in ) )
            throw new InvalidParameterException( "Pen identification could not be null or empty." );
        this.id = pen_id_in.trim();
    }

    public PenDTO toDTO()
    {
        return new PenDTO( getId(), getDescription() );
    }

    public void setInsertDate( Date insertDate )
    {
        this.insertDate = insertDate;
    }

    public Date getInsertDate()
    {
        return insertDate;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
}
