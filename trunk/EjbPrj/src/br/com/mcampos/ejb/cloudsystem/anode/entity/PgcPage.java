package br.com.mcampos.ejb.cloudsystem.anode.entity;


import br.com.mcampos.dto.anoto.PgcPageDTO;
import br.com.mcampos.ejb.cloudsystem.anode.entity.key.PgcPagePK;
import br.com.mcampos.ejb.entity.core.EntityCopyInterface;

import java.io.Serializable;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = "PgcPage.findAll", query = "select o from PgcPage o" ) } )
@Table( name = "\"pgc_page\"" )
@IdClass( PgcPagePK.class )
public class PgcPage implements Serializable, EntityCopyInterface<PgcPageDTO>
{
    @Id
    @Column( name = "pgc_id_in", nullable = false, insertable = false, updatable = false )
    private Integer pgcId;

    @Id
    @Column( name = "ppg_book_id", nullable = false )
    private Integer bookId;

    @Id
    @Column( name = "ppg_page_id", nullable = false )
    private Integer pageId;

    @ManyToOne
    @JoinColumn( name = "pgc_id_in", referencedColumnName = "pgc_id_in" )
    private Pgc pgc;

    @OneToMany( mappedBy = "pgcPage", cascade = CascadeType.ALL )
    private List<PgcAttachment> attachments;

    @OneToMany( mappedBy = "pgcPage", cascade = CascadeType.ALL )
    private List<PgcField> fields;


    public PgcPage()
    {
    }

    public PgcPage( Pgc pgc, Integer bookId, Integer ppg_page_id )
    {
        setPgc( pgc );
        this.bookId = bookId;
        this.pageId = ppg_page_id;
    }

    public Integer getPgcId()
    {
        return pgcId;
    }

    public void setPgcId( Integer pgc_id_in )
    {
        this.pgcId = pgc_id_in;
    }

    public Integer getBookId()
    {
        return bookId;
    }

    public void setBookId( Integer ppg_book_id )
    {
        this.bookId = ppg_book_id;
    }

    public Integer getPageId()
    {
        return pageId;
    }

    public void setPageId( Integer ppg_page_id )
    {
        this.pageId = ppg_page_id;
    }

    public void setPgc( Pgc pgc )
    {
        this.pgc = pgc;
        if ( pgc != null )
            setPgcId( pgc.getId() );
    }

    public Pgc getPgc()
    {
        return pgc;
    }

    public void setAttachments( List<PgcAttachment> attachments )
    {
        this.attachments = attachments;
    }

    public List<PgcAttachment> getAttachments()
    {
        return attachments;
    }

    public void setFields( List<PgcField> fields )
    {
        this.fields = fields;
    }

    public List<PgcField> getFields()
    {
        return fields;
    }

    public PgcPageDTO toDTO()
    {
        PgcPageDTO dto = new PgcPageDTO ();
        dto.setBookId( getBookId() );
        dto.setPageId( getPageId() );
        dto.setPgc( getPgc().toDTO() );
        return dto;
    }
}