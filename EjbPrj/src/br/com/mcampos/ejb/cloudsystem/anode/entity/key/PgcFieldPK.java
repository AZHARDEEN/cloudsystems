package br.com.mcampos.ejb.cloudsystem.anode.entity.key;

import java.io.Serializable;

public class PgcFieldPK implements Serializable
{
    private Integer bookId;
    private String name;
    private Integer pageId;
    private Integer pgcId;

    public PgcFieldPK()
    {
    }

    public PgcFieldPK( Integer pfl_book_id, String pfl_name_ch,
                       Integer pfl_page_id, Integer pgc_id_in )
    {
        this.bookId = pfl_book_id;
        this.name = pfl_name_ch;
        this.pageId = pfl_page_id;
        this.pgcId = pgc_id_in;
    }

    public boolean equals( Object other )
    {
        if (other instanceof PgcFieldPK) {
            final PgcFieldPK otherPgcFieldPK = (PgcFieldPK) other;
            final boolean areEqual =
                (otherPgcFieldPK.bookId.equals(bookId) && otherPgcFieldPK.name.equals(name) && otherPgcFieldPK.pageId.equals(pageId) && otherPgcFieldPK.pgcId.equals(pgcId));
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Integer getBookId()
    {
        return bookId;
    }

    void setBookId( Integer pfl_book_id )
    {
        this.bookId = pfl_book_id;
    }

    String getName()
    {
        return name;
    }

    void setName( String pfl_name_ch )
    {
        this.name = pfl_name_ch;
    }

    Integer getPageId()
    {
        return pageId;
    }

    void setPageId( Integer pfl_page_id )
    {
        this.pageId = pfl_page_id;
    }

    Integer getPgcId()
    {
        return pgcId;
    }

    void setPgcId( Integer pgc_id_in )
    {
        this.pgcId = pgc_id_in;
    }
}
