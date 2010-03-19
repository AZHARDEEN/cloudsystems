package br.com.mcampos.ejb.cloudsystem.anode.entity;


import br.com.mcampos.dto.anoto.PGCDTO;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.entity.core.EntityCopyInterface;

import java.io.Serializable;

import java.sql.Timestamp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@NamedQueries( { @NamedQuery( name = Pgc.findAllQueryName, query = "select o from Pgc o" ) } )
@Table( name = "pgc" )
public class Pgc implements Serializable, EntityCopyInterface<PGCDTO>
{
    public static final String findAllQueryName = "Pgc.findAll";
    @Id
    @Column( name = "pgc_id_in", nullable = false, insertable = false, updatable = false, unique = true )
    private Integer id;


    @Column( name = "pgc_insert_dt" )
    @Temporal( TemporalType.TIMESTAMP )
    private Date insertDate;

    @ManyToOne
    @JoinColumn( name = "pgc_id_in", referencedColumnName = "med_id_in" )
    Media media;

    @ManyToOne
    @JoinColumn( name = "pgs_id_in" )
    private PgcStatus pgcStatus;

    @Column( name = "pgc_description_ch", nullable = false )
    private String description;

    public Pgc()
    {
    }

    public Pgc( Integer pk, Timestamp insertDate )
    {
        this.id = pk;
        this.insertDate = insertDate;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer pk )
    {
        this.id = pk;
    }

    public Date getInsertDate()
    {
        return insertDate;
    }

    public void setInsertDate( Date indate )
    {
        this.insertDate = indate;
    }

    public PGCDTO toDTO()
    {
        PGCDTO dto = new PGCDTO( this.getMedia().toDTO() );
        dto.setPgcStatus( getPgcStatus().toDTO() );
        dto.setInsertDate( getInsertDate() );
        return dto;
    }

    public void setMedia( Media media )
    {
        this.media = media;
        if ( media != null )
            setId( media.getId() );
    }

    public Media getMedia()
    {
        return media;
    }

    public void setPgcStatus( PgcStatus pgcStatus )
    {
        this.pgcStatus = pgcStatus;
    }

    public PgcStatus getPgcStatus()
    {
        return pgcStatus;
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
