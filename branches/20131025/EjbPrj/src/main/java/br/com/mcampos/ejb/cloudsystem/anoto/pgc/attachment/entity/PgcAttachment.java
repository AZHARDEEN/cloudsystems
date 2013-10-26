package br.com.mcampos.ejb.cloudsystem.anoto.pgc.attachment.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = PgcAttachment.findAll, query = "select o from PgcAttachment o" ),
                 @NamedQuery( name = PgcAttachment.findAllPgc, query = "select o from PgcAttachment o where o.pgcId = ?1" ) } )
@Table( name = "pgc_attachment" )
@IdClass( PgcAttachmentPK.class )
public class PgcAttachment implements Serializable
{
    public static final String findAll = "PgcAttachment.findAll";
    public static final String findAllPgc = "PgcAttachment.findAllPgc";


    @Id
    @Column( name = "med_id_in", nullable = false )
    private Integer mediaId;
    @Id
    @Column( name = "pgc_id_in", nullable = false )
    private Integer pgcId;

    public PgcAttachment()
    {
    }


    public Integer getMediaId()
    {
        return mediaId;
    }

    public void setMediaId( Integer med_id_in )
    {
        this.mediaId = med_id_in;
    }

    public Integer getPgcId()
    {
        return pgcId;
    }

    public void setPgcId( Integer pgc_id_in )
    {
        this.pgcId = pgc_id_in;
    }
}
