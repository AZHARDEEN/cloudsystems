package br.com.mcampos.ejb.cloudsystem.anode.entity;


import br.com.mcampos.dto.anoto.PgcPenPageDTO;
import br.com.mcampos.ejb.cloudsystem.anode.entity.key.PgcPenPagePK;
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
@NamedQueries( { @NamedQuery( name = PgcPenPage.getAll, query = "select o from PgcPenPage o" ),
                 @NamedQuery( name = PgcPenPage.getAllPgcQueryName, query = "select o from PgcPenPage o where o.penPage = ?1" ),
                 @NamedQuery( name = PgcPenPage.penGetAllPgcs, query = "select o from PgcPenPage o where o.penPage.pen = ?1" ),
                 @NamedQuery( name = PgcPenPage.getAllFromPGC, query = "select o from PgcPenPage o where o.pgc = ?1" ),
                 @NamedQuery( name = PgcPenPage.deleteFromPgc, query = "delete from PgcPenPage o where o.pgc = ?1" ) } )
@Table( name = "pgc_pen_page" )
@IdClass( PgcPenPagePK.class )
public class PgcPenPage implements Serializable, EntityCopyInterface<PgcPenPageDTO>
{
    public static final String getAll = "PgcPenPage.findAll";
    public static final String getAllPgcQueryName = "PgcPenPage.findAllPgc";
    public static final String penGetAllPgcs = "PgcPenPage.penGetAllPgcs";
    public static final String deleteFromPgc = "PgcPenPage.deleteFromPgc";
    public static final String getAllFromPGC = "PgcPenPage.getAllFromPGC";


    @Id
    @Column( name = "apg_id_ch", nullable = false, insertable = false, updatable = false )
    private String pageAddress;
    @Id
    @Column( name = "frm_id_in", nullable = false, insertable = false, updatable = false )
    private Integer formId;
    @Id
    @Column( name = "pad_id_in", nullable = false, insertable = false, updatable = false )
    private Integer padId;
    @Id
    @Column( name = "pen_id_ch", nullable = false, insertable = false, updatable = false )
    private String penId;
    @Id
    @Column( name = "pgc_id_in", nullable = false, insertable = false, updatable = false )
    private Integer pgcId;

    @ManyToOne
    @JoinColumns( { @JoinColumn( name = "frm_id_in", referencedColumnName = "frm_id_in" ),
                    @JoinColumn( name = "pad_id_in", referencedColumnName = "pad_id_in" ),
                    @JoinColumn( name = "apg_id_ch", referencedColumnName = "apg_id_ch" ),
                    @JoinColumn( name = "pen_id_ch", referencedColumnName = "pen_id_ch" ) } )
    private AnotoPenPage penPage;

    @ManyToOne
    @JoinColumn( name = "pgc_id_in", referencedColumnName = "pgc_id_in" )
    private Pgc pgc;

    public PgcPenPage()
    {
    }

    public PgcPenPage( AnotoPenPage penPage, Pgc pgc )
    {
        setPenPage( penPage );
        setPgc( pgc );
    }

    public PgcPenPage( String apg_id_ch, Integer frm_id_in, Integer pad_id_in, String pen_id_ch, Integer pgc_id_in )
    {
        this.pageAddress = apg_id_ch;
        this.formId = frm_id_in;
        this.padId = pad_id_in;
        this.penId = pen_id_ch;
        this.pgcId = pgc_id_in;
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

    public String getPenId()
    {
        return penId;
    }

    public void setPenId( String pen_id_ch )
    {
        this.penId = pen_id_ch;
    }

    public Integer getPgcId()
    {
        return pgcId;
    }

    public void setPgcId( Integer pgc_id_in )
    {
        this.pgcId = pgc_id_in;
    }

    public void setPenPage( AnotoPenPage penPage )
    {
        this.penPage = penPage;
        if ( penPage != null ) {
            setPadId( penPage.getPadId() );
            setFormId( penPage.getFormId() );
            setPenId( penPage.getPenId() );
            setPageAddress( penPage.getPageAddress() );
        }
    }

    public AnotoPenPage getPenPage()
    {
        return penPage;
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

    public PgcPenPageDTO toDTO()
    {
        PgcPenPageDTO dto = new PgcPenPageDTO( getPenPage().toDTO(), getPgc().toDTO() );
        return dto;
    }
}
