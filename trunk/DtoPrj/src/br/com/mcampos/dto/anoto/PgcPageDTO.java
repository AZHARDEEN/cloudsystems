package br.com.mcampos.dto.anoto;

import java.io.Serializable;

public class PgcPageDTO implements Serializable, Comparable<PgcPageDTO>
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3342157668299884134L;
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

    public int compareTo( PgcPageDTO o )
    {
        int nRet;

        nRet = getPageId().compareTo( o.getPageId() );
        if ( nRet != 0 )
            return nRet;
        nRet = getBookId().compareTo( o.getBookId() );
        if ( nRet != 0 )
            return nRet;
        return getPgc().compareTo( o.getPgc() );
    }

    @Override
    public boolean equals( Object obj )
    {
        PgcPageDTO other = ( PgcPageDTO ) obj;
        return  getPageId().equals( other.getPageId() ) && getBookId().equals( other.getBookId() ) && getPgc().equals( other.getPgc() ) ;
    }
}
