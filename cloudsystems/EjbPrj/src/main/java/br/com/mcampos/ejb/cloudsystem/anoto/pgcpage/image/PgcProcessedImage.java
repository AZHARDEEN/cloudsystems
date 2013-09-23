package br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.image;


import br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.PgcPage;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( {
    @NamedQuery( name = "PgcProcessedImage.findAll", query = "select o from PgcProcessedImage o" ),
    @NamedQuery( name = PgcProcessedImage.findPgcPageImages, query = "select o from PgcProcessedImage o where o.pgcPage = ?1" )
    } )
@Table( name = "pgc_processed_image" )
@IdClass( PgcProcessedImagePK.class )
public class PgcProcessedImage implements Serializable
{
    public static final String findPgcPageImages = "PgcProcessedImage.findPgcPageImages";

    @Id
    @Column( name = "med_id_in", nullable = false, insertable = false, updatable = false )
    private Integer mediaId;

    @Id
    @Column( name = "pgc_id_in", nullable = false, insertable = false, updatable = false )
    private Integer pgcId;

    @Id
    @Column( name = "ppg_book_id", nullable = false, insertable = false, updatable = false )
    private Integer bookId;

    @Id
    @Column( name = "ppg_page_id", nullable = false, insertable = false, updatable = false )
    private Integer pageId;

    @ManyToOne
    @JoinColumns( { @JoinColumn( name = "pgc_id_in", referencedColumnName = "pgc_id_in" ), @JoinColumn( name = "ppg_book_id", referencedColumnName = "ppg_book_id" ), @JoinColumn( name = "ppg_page_id", referencedColumnName = "ppg_page_id" ) } )
    private PgcPage pgcPage;

    @ManyToOne
    @JoinColumn( name = "med_id_in", referencedColumnName = "med_id_in" )
    private Media media;

    public PgcProcessedImage()
    {
    }

    public PgcProcessedImage( PgcPage pgc, Media media, Integer ppi_book_id, Integer ppi_page_id )
    {
        setPgc( pgc );
        setMedia( media );
        setBookId( ppi_book_id );
        setPageId( ppi_page_id );
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

    public Integer getBookId()
    {
        return bookId;
    }

    public void setBookId( Integer ppi_book_id )
    {
        this.bookId = ppi_book_id;
    }

    public Integer getPageId()
    {
        return pageId;
    }

    public void setPageId( Integer ppi_page_id )
    {
        this.pageId = ppi_page_id;
    }

    public void setPgc( PgcPage pgc )
    {
        this.pgcPage = pgc;
        if ( pgcPage != null ) {
            setBookId( pgcPage.getBookId() );
            setPageId( pgcPage.getPageId() );
            setPgcId( pgcPage.getPgcId() );
        }
    }

    public PgcPage getPgc()
    {
        return pgcPage;
    }

    public void setMedia( Media media )
    {
        this.media = media;
        if ( media != null )
            setMediaId( media.getId() );
    }

    public Media getMedia()
    {
        return media;
    }
}
