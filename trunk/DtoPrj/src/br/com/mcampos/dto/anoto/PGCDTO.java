package br.com.mcampos.dto.anoto;


import br.com.mcampos.dto.system.MediaDTO;

import java.io.Serializable;

import java.util.Date;
import java.util.List;


public class PGCDTO implements Comparable<PGCDTO>, Serializable
{
    protected Integer id;
    protected MediaDTO media;
    protected PgcStatusDTO pgcStatus;
    protected String penId;
    protected Long timeDiff;

    protected Date insertDate;

    protected List<MediaDTO> backgroundImages;

    public PGCDTO()
    {
        super();
    }

    public PGCDTO( MediaDTO media )
    {
        super();
        setMedia( media );
    }

    public int compareTo( PGCDTO o )
    {
        return getId().compareTo( o.getId() );
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public Integer getId()
    {
        if ( id == null )
            id = new Integer( 0 );
        return id;
    }

    @Override
    public boolean equals( Object obj )
    {
        return getId().equals( ( ( PGCDTO )obj ).getId() );
    }

    public void setMedia( MediaDTO media )
    {
        this.media = media;
        setId( media.getId() );
    }

    public MediaDTO getMedia()
    {
        return media;
    }

    public void setPgcStatus( PgcStatusDTO pgcStatus )
    {
        this.pgcStatus = pgcStatus;
    }

    public PgcStatusDTO getPgcStatus()
    {
        return pgcStatus;
    }

    @Override
    public String toString()
    {
        return getMedia().toString();
    }

    public void setBackgroundImages( List<MediaDTO> backgroundImages )
    {
        this.backgroundImages = backgroundImages;
    }

    public List<MediaDTO> getBackgroundImages()
    {
        return backgroundImages;
    }

    public void setInsertDate( Date insertDate )
    {
        this.insertDate = insertDate;
    }

    public Date getInsertDate()
    {
        return insertDate;
    }

    public void setPenId( String penId )
    {
        this.penId = penId;
    }

    public String getPenId()
    {
        return penId;
    }

    public void setTimeDiff( Long timeDiff )
    {
        this.timeDiff = timeDiff;
    }

    public Long getTimeDiff()
    {
        return timeDiff;
    }
}
