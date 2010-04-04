package br.com.mcampos.ejb.cloudsystem.anode.entity.key;

import java.io.Serializable;

public class PgcPagePK implements Serializable
{
    private Integer pgcId;
    private Integer bookId;
    private Integer pageId;

    public PgcPagePK()
    {
    }

    public PgcPagePK( Integer pgc_id_in, Integer ppg_book_id, Integer ppg_page_id )
    {
        this.pgcId = pgc_id_in;
        this.bookId = ppg_book_id;
        this.pageId = ppg_page_id;
    }

    public boolean equals( Object other )
    {
        if ( other instanceof PgcPagePK ) {
            final PgcPagePK otherPgcPagePK = ( PgcPagePK )other;
            final boolean areEqual = ( otherPgcPagePK.pgcId.equals( pgcId ) && otherPgcPagePK.bookId.equals( bookId ) && otherPgcPagePK.pageId.equals( pageId ) );
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
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

    void setBookId( Integer ppg_book_id )
    {
        this.bookId = ppg_book_id;
    }

    Integer getPageId()
    {
        return pageId;
    }

    void setPageId( Integer ppg_page_id )
    {
        this.pageId = ppg_page_id;
    }
}
