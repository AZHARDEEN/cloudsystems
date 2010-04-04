package br.com.mcampos.dto.anoto;


import br.com.mcampos.dto.system.MediaDTO;

import java.io.Serializable;

public class PgcAttachmentDTO implements Serializable
{
    private Integer type;
    private Integer sequence;
    private String value;
    private PgcPageDTO pgcPage;
    private MediaDTO media;
    private Integer barcodeType;

    public PgcAttachmentDTO()
    {
        super();
    }

    public PgcAttachmentDTO( PgcPageDTO pgcPage )
    {
        super();
        setPgcPage( pgcPage );
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


    public void setMedia( MediaDTO media )
    {
        this.media = media;
    }

    public MediaDTO getMedia()
    {
        return media;
    }

    public void setBarcodeType( Integer barcodeType )
    {
        this.barcodeType = barcodeType;
    }

    public Integer getBarcodeType()
    {
        return barcodeType;
    }

    public void setPgcPage( PgcPageDTO pgcPage )
    {
        this.pgcPage = pgcPage;
    }

    public PgcPageDTO getPgcPage()
    {
        return pgcPage;
    }
}
