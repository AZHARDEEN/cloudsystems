package br.com.mcampos.dto.anoto;


import br.com.mcampos.dto.system.MediaDTO;

import java.io.Serializable;

public class PgcAttachmentDTO implements Serializable
{
    private Integer bookId;
    private Integer type;
    private Integer sequence;
    private String value;
    private Integer pgcId;
    private PGCDTO pgc;
    private MediaDTO media;
    private Integer pageId;
    private Integer barcodeType;

    public PgcAttachmentDTO()
    {
        super();
    }

    public void setBookId( Integer bookId )
    {
        this.bookId = bookId;
    }

    public Integer getBookId()
    {
        return bookId;
    }

    public void setType( Integer type )
    {
        this.type = type;
    }

    public Integer getType()
    {
        return type;
    }

    public void setSequence( Integer sequence )
    {
        this.sequence = sequence;
    }

    public Integer getSequence()
    {
        return sequence;
    }

    public void setValue( String value )
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }

    public void setPgcId( Integer pgcId )
    {
        this.pgcId = pgcId;
    }

    public Integer getPgcId()
    {
        return pgcId;
    }

    public void setPgc( PGCDTO pgc )
    {
        this.pgc = pgc;
        if ( pgc != null )
            setPgcId( pgc.getId() );
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

    public void setPageId( Integer pageId )
    {
        this.pageId = pageId;
    }

    public Integer getPageId()
    {
        return pageId;
    }

    public void setBarcodeType( Integer barcodeType )
    {
        this.barcodeType = barcodeType;
    }

    public Integer getBarcodeType()
    {
        return barcodeType;
    }
}
