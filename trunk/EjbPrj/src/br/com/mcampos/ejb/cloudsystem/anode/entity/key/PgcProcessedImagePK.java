package br.com.mcampos.ejb.cloudsystem.anode.entity.key;

import java.io.Serializable;

public class PgcProcessedImagePK implements Serializable
{
    private Integer mediaId;
    private Integer pgcId;
    private Integer bookId;
    private Integer pageId;

    public PgcProcessedImagePK()
    {
    }

    public PgcProcessedImagePK( Integer med_id_in, Integer pgc_id_in, Integer ppi_book_id, Integer ppi_page_id )
    {
        this.mediaId = med_id_in;
        this.pgcId = pgc_id_in;
        this.bookId = ppi_book_id;
        this.pageId = ppi_page_id;
    }

    public boolean equals( Object other )
    {
        if ( other instanceof PgcProcessedImagePK ) {
            final PgcProcessedImagePK otherPgcProcessedImagePK = ( PgcProcessedImagePK )other;
            final boolean areEqual = ( otherPgcProcessedImagePK.mediaId.equals( mediaId ) && otherPgcProcessedImagePK.pgcId.equals( pgcId ) && otherPgcProcessedImagePK.bookId.equals( bookId ) && otherPgcProcessedImagePK.pageId.equals( pageId ) );
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Integer getMediaId()
    {
        return mediaId;
    }

    void setMediaId( Integer med_id_in )
    {
        this.mediaId = med_id_in;
    }

    Integer getPgcId()
    {
        return pgcId;
    }

    void setPgcId( Integer pgc_id_in )
    {
        this.pgcId = pgc_id_in;
    }

    Integer getBookId()
    {
        return bookId;
    }

    void setBookId( Integer ppi_book_id )
    {
        this.bookId = ppi_book_id;
    }

    Integer getPageId()
    {
        return pageId;
    }

    void setPageId( Integer ppi_page_id )
    {
        this.pageId = ppi_page_id;
    }
}
