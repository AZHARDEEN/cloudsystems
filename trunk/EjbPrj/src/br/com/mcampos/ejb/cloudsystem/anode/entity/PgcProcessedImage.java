package br.com.mcampos.ejb.cloudsystem.anode.entity;


import br.com.mcampos.ejb.cloudsystem.anode.entity.key.PgcProcessedImagePK;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;

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
@NamedQueries({
  @NamedQuery(name = "PgcProcessedImage.findAll", query = "select o from PgcProcessedImage o")
})
@Table( name = "\"pgc_processed_image\"" )
@IdClass( PgcProcessedImagePK.class )
public class PgcProcessedImage implements Serializable
{
    @Id
    @Column( name="med_id_in", nullable = false, insertable = false, updatable = false )
    private Integer med_id_in;
    @Id
    @Column( name="pgc_id_in", nullable = false, insertable = false, updatable = false )
    private Integer pgc_id_in;

    @Id
    @Column( name="ppi_book_id", nullable = false )
    private Integer ppi_book_id;

    @Id
    @Column( name="ppi_page_id", nullable = false )
    private Integer ppi_page_id;

    @ManyToOne
    @JoinColumn( name = "pgc_id_in", referencedColumnName = "pgc_id_in" )
    private Pgc pgc;

    @ManyToOne
    @JoinColumn( name = "med_id_in", referencedColumnName = "med_id_in" )
    private Media media;

    public PgcProcessedImage()
    {
    }

    public PgcProcessedImage( Pgc pgc, Media media, Integer ppi_book_id, Integer ppi_page_id )
    {
        setPgc( pgc );
        setMedia( media );
        this.ppi_book_id = ppi_book_id;
        this.ppi_page_id = ppi_page_id;
    }

    public Integer getMed_id_in()
    {
        return med_id_in;
    }

    public void setMed_id_in( Integer med_id_in )
    {
        this.med_id_in = med_id_in;
    }

    public Integer getPgc_id_in()
    {
        return pgc_id_in;
    }

    public void setPgc_id_in( Integer pgc_id_in )
    {
        this.pgc_id_in = pgc_id_in;
    }

    public Integer getPpi_book_id()
    {
        return ppi_book_id;
    }

    public void setPpi_book_id( Integer ppi_book_id )
    {
        this.ppi_book_id = ppi_book_id;
    }

    public Integer getPpi_page_id()
    {
        return ppi_page_id;
    }

    public void setPpi_page_id( Integer ppi_page_id )
    {
        this.ppi_page_id = ppi_page_id;
    }

    public void setPgc( Pgc pgc )
    {
        this.pgc = pgc;
        if ( pgc != null )
            setPgc_id_in( pgc.getId() );
    }

    public Pgc getPgc()
    {
        return pgc;
    }

    public void setMedia( Media media )
    {
        this.media = media;
        if ( media != null )
            setMed_id_in( media.getId() );
    }

    public Media getMedia()
    {
        return media;
    }
}
