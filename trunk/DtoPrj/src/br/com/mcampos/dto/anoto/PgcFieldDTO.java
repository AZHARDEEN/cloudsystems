package br.com.mcampos.dto.anoto;


import br.com.mcampos.dto.system.MediaDTO;

import java.io.Serializable;

public class PgcFieldDTO implements Serializable, Comparable<PgcFieldDTO>
{
    private Integer bookId;
    private String ircText;
    private String name;
    private Integer pageId;
    private String revisedText;
    private PGCDTO pgc;
    private MediaDTO media;
    private Boolean hasPenstrokes;
    private Integer type;

    public PgcFieldDTO()
    {
        super();
    }

    public void setBookId( Integer pfl_book_id )
    {
        this.bookId = pfl_book_id;
    }

    public Integer getBookId()
    {
        return bookId;
    }

    public void setIrcText( String pfl_icr_tx )
    {
        this.ircText = pfl_icr_tx;
    }

    public String getIrcText()
    {
        return ircText;
    }

    public void setName( String pfl_name_ch )
    {
        this.name = pfl_name_ch;
    }

    public String getName()
    {
        return name;
    }

    public void setPageId( Integer pfl_page_id )
    {
        this.pageId = pfl_page_id;
    }

    public Integer getPageId()
    {
        return pageId;
    }

    public void setRevisedText( String pfl_revised_tx )
    {
        this.revisedText = pfl_revised_tx;
    }

    public String getRevisedText()
    {
        return revisedText;
    }

    public void setPgc( PGCDTO pgc )
    {
        this.pgc = pgc;
    }

    public PGCDTO getPgc()
    {
        return pgc;
    }

    public void setMedia( MediaDTO media )
    {
        this.media = media;
    }

    public MediaDTO getMedia()
    {
        return media;
    }

    public int compareTo( PgcFieldDTO o )
    {
        return 0;
    }

    public void setHasPenstrokes( Boolean hasPenstrokes )
    {
        this.hasPenstrokes = hasPenstrokes;
    }

    public Boolean getHasPenstrokes()
    {
        return hasPenstrokes;
    }

    public void setType( Integer type )
    {
        this.type = type;
    }

    public Integer getType()
    {
        return type;
    }
}
