package br.com.mcampos.dto.anoto;


import br.com.mcampos.dto.system.FieldTypeDTO;
import br.com.mcampos.dto.system.MediaDTO;
import br.com.mcampos.sysutils.SysUtils;

import java.io.Serializable;

public class PgcFieldDTO implements Serializable, Comparable<PgcFieldDTO>
{
    /**
     *
     */
    private static final long serialVersionUID = 8017130948615035955L;
    private String ircText;
    private String name;
    private String revisedText;
    private PgcPageDTO pgcPage;
    private MediaDTO media;
    private Boolean hasPenstrokes;
    private FieldTypeDTO type;
    private Long startTime;
    private Long endTime;
    private Integer sequence;


    public PgcFieldDTO()
    {
        super();
    }

    public PgcFieldDTO( PgcPageDTO pgcPage )
    {
        super();
        setPgcPage( pgcPage );
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

    public void setRevisedText( String pfl_revised_tx )
    {
        this.revisedText = pfl_revised_tx;
    }

    public String getRevisedText()
    {
        return revisedText;
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

    public void setType( FieldTypeDTO type )
    {
        this.type = type;
    }

    public FieldTypeDTO getType()
    {
        return type;
    }

    public void setPgcPage( PgcPageDTO pgcPage )
    {
        this.pgcPage = pgcPage;
    }

    public PgcPageDTO getPgcPage()
    {
        return pgcPage;
    }

    public void setStartTime( Long startTime )
    {
        this.startTime = startTime;
    }

    public Long getStartTime()
    {
        return startTime;
    }

    public void setEndTime( Long endTime )
    {
        this.endTime = endTime;
    }

    public Long getEndTime()
    {
        return endTime;
    }

    @Override
    public String toString()
    {
        return getName();
    }

    public void setSequence( Integer sequence )
    {
        this.sequence = sequence;
    }

    public Integer getSequence()
    {
        return sequence;
    }

    public String getValue()
    {
        if ( getType().getId().equals( FieldTypeDTO.typeBoolean ) == false )
            return ( SysUtils.isEmpty( getRevisedText() ) ? getIrcText() : getRevisedText() );
        else
            return getHasPenstrokes() ? "SIM" : "";
    }

    public boolean isBoolean()
    {
        return getType().getId().equals( FieldTypeDTO.typeBoolean );
    }
}
