package br.com.mcampos.ejb.cloudsystem.anode.entity;


import br.com.mcampos.ejb.cloudsystem.anode.entity.key.PgcFieldPK;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = "PgcField.findAll", query = "select o from PgcField o" ) } )
@Table( name = "pgc_field" )
@IdClass( PgcFieldPK.class )
public class PgcField implements Serializable
{
    @Column( name = "med_id_in", insertable = false, updatable = false )
    private Integer mediaId;
    @Id
    @Column( name = "ppg_book_id", nullable = false, insertable = false, updatable = false )
    private Integer bookId;
    @Column( name = "pfl_icr_tx" )
    private String icrText;
    @Id
    @Column( name = "pfl_name_ch", nullable = false )
    private String name;
    @Id
    @Column( name = "ppg_page_id", nullable = false, insertable = false, updatable = false )
    private Integer pageId;
    @Column( name = "pfl_revised_tx" )
    private String revisedText;
    @Id
    @Column( name = "pgc_id_in", nullable = false, insertable = false, updatable = false )
    private Integer pgcId;
    @Column( name = "pfl_type_in" )
    private Integer type;

    @Column( name = "pfl_start_time_in" )
    private Long startTime;

    @Column( name = "pfl_end_time_in" )
    private Long endTime;

    @Column( name = "pfl_has_penstrokes_bt" )
    private Boolean hasPenstrokes;


    @ManyToOne
    @JoinColumn( name = "med_id_in", referencedColumnName = "med_id_in" )
    private Media media;

    @ManyToOne
    @JoinColumns( { @JoinColumn( name = "pgc_id_in", referencedColumnName = "pgc_id_in" ), @JoinColumn( name = "ppg_book_id", referencedColumnName = "ppg_book_id" ), @JoinColumn( name = "ppg_page_id", referencedColumnName = "ppg_page_id" ) } )
    private PgcPage pgcPage;


    public PgcField()
    {
    }

    public Integer getMediaId()
    {
        return mediaId;
    }

    public void setMediaId( Integer med_id_in )
    {
        this.mediaId = med_id_in;
    }

    public Integer getBookId()
    {
        return bookId;
    }

    public void setBookId( Integer pfl_book_id )
    {
        this.bookId = pfl_book_id;
    }

    public String getIcrText()
    {
        return icrText;
    }

    public void setIcrText( String pfl_icr_tx )
    {
        this.icrText = pfl_icr_tx;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String pfl_name_ch )
    {
        this.name = pfl_name_ch;
    }

    public Integer getPageId()
    {
        return pageId;
    }

    public void setPageId( Integer pfl_page_id )
    {
        this.pageId = pfl_page_id;
    }

    public String getRevisedText()
    {
        return revisedText;
    }

    public void setRevisedText( String pfl_revised_tx )
    {
        this.revisedText = pfl_revised_tx;
    }

    public Integer getPgcId()
    {
        return pgcId;
    }

    public void setPgcId( Integer pgc_id_in )
    {
        this.pgcId = pgc_id_in;
    }


    public void setMedia( Media media )
    {
        this.media = media;
        if ( media != null )
            setMediaId( media.getId() );
    }

    public Media getMedia()
    {
        return media;
    }

    public void setHasPenstrokes( Boolean pfl_has_penstrokes_bt )
    {
        this.hasPenstrokes = pfl_has_penstrokes_bt;
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

    public void setPgcPage( PgcPage pgcPage )
    {
        this.pgcPage = pgcPage;
        if ( pgcPage != null ) {
            setBookId( pgcPage.getBookId() );
            setPageId( pgcPage.getPageId() );
            setPgcId( pgcPage.getPgcId() );
        }
    }

    public PgcPage getPgcPage()
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
