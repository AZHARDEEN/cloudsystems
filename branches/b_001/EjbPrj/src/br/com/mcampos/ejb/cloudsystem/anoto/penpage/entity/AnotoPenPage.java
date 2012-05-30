package br.com.mcampos.ejb.cloudsystem.anoto.penpage.entity;


import br.com.mcampos.dto.anoto.AnotoPenPageDTO;
import br.com.mcampos.ejb.cloudsystem.anoto.page.AnotoPage;
import br.com.mcampos.ejb.cloudsystem.anoto.pen.AnotoPen;
import br.com.mcampos.ejb.entity.core.EntityCopyInterface;

import java.io.Serializable;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@NamedQueries( { @NamedQuery( name = AnotoPenPage.nextId, query = "select max(o.sequence) from AnotoPenPage o where o.page = ?1" ),
                 @NamedQuery( name = AnotoPenPage.pagePensQueryName,
                              query = "select o from AnotoPenPage o where o.page = ?1 and o.toDate is null" ),
                 @NamedQuery( name = AnotoPenPage.formPensQueryName,
                              query = "select o from AnotoPenPage o where o.page.pad.form = ?1 and o.toDate is null" ),
                 @NamedQuery( name = AnotoPenPage.pagePenQueryName,
                              query = "select o from AnotoPenPage o where o.page = ?1 and o.pen = ?2 and o.toDate is null" ),
                 @NamedQuery( name = AnotoPenPage.penPageAddressQueryName,
                              query = "select o from AnotoPenPage o where o.pen = ?1 and o.pageAddress = ?2 and o.toDate is null" ) } )
@Table( name = "anoto_pen_page" )
@IdClass( AnotoPenPagePK.class )
public class AnotoPenPage implements Serializable, EntityCopyInterface<AnotoPenPageDTO>
{
    public static final String pagePensQueryName = "AnotoPenPage.getPagePens";
    public static final String formPensQueryName = "AnotoPenPage.getFormPens";
    public static final String pagePenQueryName = "AnotoPenPage.getPagePen";
    public static final String pgcQueryName = "AnotoPenPage.getPenPageFromPGC";
    public static final String penPageAddressQueryName = "AnotoPenPage.getPenPageAddress";
    public static final String nextId = "AnotoPenPage.nextId";

    @Id
    @Column( name = "apg_id_ch", nullable = false, insertable = false, updatable = false )
    private String pageAddress;

    @Id
    @Column( name = "frm_id_in", nullable = false, insertable = false, updatable = false )
    private Integer formId;

    @Id
    @Column( name = "pad_id_in", nullable = false, insertable = false, updatable = false )
    private Integer padId;

    @Column( name = "pdp_insert_dt", nullable = false )
    @Temporal( TemporalType.TIMESTAMP )
    private Date insertDate;


    @Column( name = "pdp_to_dt", nullable = false )
    @Temporal( TemporalType.TIMESTAMP )
    private Date toDate;

    @Id
    @Column( name = "pdp_seq_in", nullable = false )
    private Integer sequence;

    @ManyToOne
    @JoinColumn( name = "pen_id_ch", referencedColumnName = "pen_id_ch" )
    private AnotoPen pen;

    @ManyToOne
    @JoinColumns( { @JoinColumn( name = "frm_id_in", referencedColumnName = "frm_id_in" ),
                    @JoinColumn( name = "pad_id_in", referencedColumnName = "pad_id_in" ),
                    @JoinColumn( name = "apg_id_ch", referencedColumnName = "apg_id_ch" ) } )
    private AnotoPage page;

    public AnotoPenPage()
    {
    }

    public AnotoPenPage( AnotoPen pen, AnotoPage page )
    {
        setPen( pen );
        setPage( page );
    }


    public String getPageAddress()
    {
        return pageAddress;
    }

    public void setPageAddress( String apg_id_ch )
    {
        this.pageAddress = apg_id_ch;
    }

    public Integer getFormId()
    {
        return formId;
    }

    public void setFormId( Integer frm_id_in )
    {
        this.formId = frm_id_in;
    }

    public Integer getPadId()
    {
        return padId;
    }

    public void setPadId( Integer pad_id_in )
    {
        this.padId = pad_id_in;
    }

    public Date getInsertDate()
    {
        return insertDate;
    }

    public void setInsertDate( Date pdp_insert_dt )
    {
        this.insertDate = pdp_insert_dt;
    }

    public void setPen( AnotoPen pen )
    {
        this.pen = pen;
    }

    public AnotoPen getPen()
    {
        return pen;
    }

    public void setPage( AnotoPage page )
    {
        this.page = page;
        if ( page != null ) {
            setPadId( page.getPadId() );
            setFormId( page.getFormId() );
            setPageAddress( page.getPageAddress() );
        }
    }

    public AnotoPage getPage()
    {
        return page;
    }

    public AnotoPenPageDTO toDTO()
    {
        AnotoPenPageDTO dto = new AnotoPenPageDTO( getPen().toDTO(), getPage().toDTO() );
        return dto;
    }

    public void setSequence( Integer sequence )
    {
        this.sequence = sequence;
    }

    public Integer getSequence()
    {
        return sequence;
    }

    public void setToDate( Date toDate )
    {
        this.toDate = toDate;
    }

    public Date getToDate()
    {
        return toDate;
    }
}
