package br.com.mcampos.ejb.cloudsystem.anode.entity;


import br.com.mcampos.dto.anoto.PgcAttachmentDTO;
import br.com.mcampos.ejb.cloudsystem.anode.entity.key.PgcAttachmentPK;
import br.com.mcampos.ejb.cloudsystem.media.entity.Media;
import br.com.mcampos.ejb.entity.core.EntityCopyInterface;

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
@NamedQueries( { @NamedQuery( name = PgcAttachment.findAll, query = "select o from PgcAttachment o" ),
                 @NamedQuery( name = PgcAttachment.findByPage, query = "select o from PgcAttachment o where o.pgcPage = ?1" ) } )
@Table( name = "pgc_attachment" )
@IdClass( PgcAttachmentPK.class )
public class PgcAttachment implements Serializable, EntityCopyInterface<PgcAttachmentDTO>
{
    public static final String findAll = "PgcAttachment.findAll";
    public static final String findByPage = "PgcAttachment.findByPage";


    @Id
    @Column( name = "ppg_book_id", nullable = false, insertable = false, updatable = false )
    private Integer bookId;
    @Column( name = "pat_id_in", nullable = false )
    private Integer type;
    @Id
    @Column( name = "ppg_page_id", nullable = false, insertable = false, updatable = false )
    private Integer pageId;
    @Id
    @Column( name = "pat_seq_in", nullable = false )
    private Integer sequence;
    @Column( name = "pat_value_ch" )
    private String value;
    @Id
    @Column( name = "pgc_id_in", nullable = false, insertable = false, updatable = false )
    private Integer pgcId;

    @Column( name = "pat_barcode_type_in" )
    private Integer barcodeType;

    @ManyToOne
    @JoinColumns( { @JoinColumn( name = "pgc_id_in", referencedColumnName = "pgc_id_in" ),
                    @JoinColumn( name = "ppg_book_id", referencedColumnName = "ppg_book_id" ),
                    @JoinColumn( name = "ppg_page_id", referencedColumnName = "ppg_page_id" ) } )
    private PgcPage pgcPage;

    @ManyToOne
    @JoinColumn( name = "med_id_in", referencedColumnName = "med_id_in" )
    private Media media;


    public PgcAttachment()
    {
    }


    public Integer getBookId()
    {
        return bookId;
    }

    public void setBookId( Integer pat_book_id )
    {
        this.bookId = pat_book_id;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType( Integer pat_id_in )
    {
        this.type = pat_id_in;
    }

    public Integer getPageId()
    {
        return pageId;
    }

    public void setPageId( Integer pat_page_id )
    {
        this.pageId = pat_page_id;
    }

    public Integer getSequence()
    {
        return sequence;
    }

    public void setSequence( Integer pat_seq_in )
    {
        this.sequence = pat_seq_in;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue( String pat_value_ch )
    {
        this.value = pat_value_ch;
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
    }

    public Media getMedia()
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

    public PgcAttachmentDTO toDTO()
    {
        PgcAttachmentDTO dto = new PgcAttachmentDTO( getPgcPage().toDTO() );
        dto.setBarcodeType( getBarcodeType() );
        if ( getMedia() != null )
            dto.setMedia( getMedia().toDTO() );
        dto.setSequence( getSequence() );
        dto.setType( getType() );
        dto.setValue( getValue() );
        return dto;
    }
}
