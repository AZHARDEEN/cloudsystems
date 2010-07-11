package br.com.mcampos.ejb.cloudsystem.anoto.pgcpage.field;


import br.com.mcampos.dto.anoto.PgcFieldDTO;

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

    public PgcFieldPK( PgcField entity )
    {
        setBookId( entity.getBookId() );
        setPageId( entity.getPageId() );
        setPgcId( entity.getPgcId() );
        setName( entity.getName() );
    }


    public PgcFieldPK( PgcFieldDTO entity )
    {
        setBookId( entity.getPgcPage().getBookId() );
        setPageId( entity.getPgcPage().getPageId() );
        setPgcId( entity.getPgcPage().getPgc().getId() );
        setName( entity.getName() );
    }


    public boolean equals( Object other )
    {
        if ( other instanceof PgcFieldPK ) {
            final PgcFieldPK otherPgcFieldPK = ( PgcFieldPK )other;
            final boolean areEqual = ( otherPgcFieldPK.bookId.equals( bookId ) && otherPgcFieldPK.name.equals( name ) && otherPgcFieldPK.pageId.equals( pageId ) && otherPgcFieldPK.pgcId.equals( pgcId ) );
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

    public void setBookId( Integer pfl_book_id )
    {
        this.bookId = pfl_book_id;
    }

    String getName()
    {
        return name;
    }

    public void setName( String pfl_name_ch )
    {
        this.name = pfl_name_ch;
    }

    Integer getPageId()
    {
        return pageId;
    }

    public void setPageId( Integer pfl_page_id )
    {
        this.pageId = pfl_page_id;
    }

    Integer getPgcId()
    {
        return pgcId;
    }

    public void setPgcId( Integer pgc_id_in )
    {
        this.pgcId = pgc_id_in;
    }
}
