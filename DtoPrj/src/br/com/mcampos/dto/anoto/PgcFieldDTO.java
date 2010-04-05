package br.com.mcampos.dto.anoto;


import br.com.mcampos.dto.system.MediaDTO;

import java.io.Serializable;

public class PgcFieldDTO implements Serializable, Comparable<PgcFieldDTO>
{
    private String ircText;
    private String name;
    private String revisedText;
    private PgcPageDTO pgcPage;
    private MediaDTO media;
    private Boolean hasPenstrokes;
    private Integer type;
    private Long startTime;
    private Long endTime;


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

    public void setType( Integer type )
    {
        this.type = type;
    }

    public Integer getType()
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
}
