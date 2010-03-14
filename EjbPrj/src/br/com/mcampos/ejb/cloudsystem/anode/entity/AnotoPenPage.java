package br.com.mcampos.ejb.cloudsystem.anode.entity;


import br.com.mcampos.dto.anoto.AnotoPenPageDTO;
import br.com.mcampos.ejb.cloudsystem.anode.entity.key.AnotoPenPagePK;
import br.com.mcampos.ejb.entity.core.EntityCopyInterface;

import java.io.Serializable;

import java.sql.Timestamp;

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
@NamedQueries( { @NamedQuery( name = "AnotoPenPage.findAll", query = "select o from AnotoPenPage o" ), @NamedQuery( name = AnotoPenPage.pageAvailablePensQueryName, query = "select o from AnotoPenPage o where o.page <> ?1" ), @NamedQuery( name = AnotoPenPage.pagePensQueryName, query = "select o from AnotoPenPage o where o.page = ?1" ), @NamedQuery( name = AnotoPenPage.formPensQueryName, query = "select o from AnotoPenPage o where o.page.pad.form = ?1" ), @NamedQuery( name = AnotoPenPage.formAndPenQueryName, query = "select o from AnotoPenPage o where o.page.pad.form = ?1 and o.pen = ?2" ), @NamedQuery( name = AnotoPenPage.pagePenQueryName, query = "select o from AnotoPenPage o where o.page = ?1 and o.pen = ?2" ) } )
@Table( name = "anoto_pen_page" )
@IdClass( AnotoPenPagePK.class )
public class AnotoPenPage implements Serializable, EntityCopyInterface<AnotoPenPageDTO>
{
    public static final String pageAvailablePensQueryName = "AnotoPenPage.getPageAvailablePens";
    public static final String pagePensQueryName = "AnotoPenPage.getPagePens";
    public static final String formPensQueryName = "AnotoPenPage.getFormPens";
    public static final String formAndPenQueryName = "AnotoPenPage.getFormAndPen";
    public static final String pagePenQueryName = "AnotoPenPage.getPagePen";

    @Id
    @Column( name = "apg_id_ch", nullable = false, insertable = false, updatable = false )
    private String pageAddress;
    @Id
    @Column( name = "frm_id_in", nullable = false, insertable = false, updatable = false )
    private Integer formId;
    @Id
    @Column( name = "pad_id_in", nullable = false, insertable = false, updatable = false )
    private Integer padId;
    @Column( name = "pdp_insert_dt", nullable = false, insertable = false, updatable = false )
    private Timestamp insertDate;
    @Id
    @Column( name = "pen_id_ch", nullable = false, insertable = false, updatable = false )
    private String penId;

    @ManyToOne
    @JoinColumn( name = "pen_id_ch", referencedColumnName = "pen_id_ch" )
    private AnotoPen pen;

    @ManyToOne
    @JoinColumns( { @JoinColumn( name = "frm_id_in", referencedColumnName = "frm_id_in" ), @JoinColumn( name = "pad_id_in", referencedColumnName = "pad_id_in" ), @JoinColumn( name = "apg_id_ch", referencedColumnName = "apg_id_ch" ) } )
    private AnotoPage page;

    public AnotoPenPage()
    {
    }

    public AnotoPenPage( AnotoPen pen, AnotoPage page )
    {
        setPen( pen );
        setPage( page );
    }

    public AnotoPenPage( String apg_id_ch, Integer frm_id_in, Integer pad_id_in, Timestamp pdp_insert_dt, String pen_id_ch )
    {
        this.pageAddress = apg_id_ch;
        this.formId = frm_id_in;
        this.padId = pad_id_in;
        this.insertDate = pdp_insert_dt;
        this.penId = pen_id_ch;
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

    public Timestamp getInsertDate()
    {
        return insertDate;
    }

    public void setInsertDate( Timestamp pdp_insert_dt )
    {
        this.insertDate = pdp_insert_dt;
    }

    public String getPenId()
    {
        return penId;
    }

    public void setPenId( String pen_id_ch )
    {
        this.penId = pen_id_ch;
    }

    public void setPen( AnotoPen pen )
    {
        this.pen = pen;
        if ( pen != null ) {
            setPenId( pen.getId() );
        }
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
}
