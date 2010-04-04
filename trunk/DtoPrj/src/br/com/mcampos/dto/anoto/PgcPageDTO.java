package br.com.mcampos.dto.anoto;

import java.io.Serializable;

public class PgcPageDTO implements Serializable
{
    private PGCDTO pgc;
    private Integer bookId;
    private Integer pageId;

    public PgcPageDTO()
    {
        super();
    }

    public PgcPageDTO( PGCDTO pgc, Integer bookId, Integer pageId )
    {
        super();
        setPgc ( pgc );
        setBookId( bookId );
        setPageId( pageId );
    }

    public void setBookId( Integer bookId )
    {
        this.bookId = bookId;
    }

    public Integer getBookId()
    {
        return bookId;
    }

    public void setPageId( Integer pageId )
    {
        this.pageId = pageId;
    }

    public Integer getPageId()
    {
        return pageId;
    }

    public void setPgc( PGCDTO pgc )
    {
        this.pgc = pgc;
    }

    public PGCDTO getPgc()
    {
        return pgc;
    }
}
