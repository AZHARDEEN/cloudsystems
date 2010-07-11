package br.com.mcampos.ejb.cloudsystem.anoto.penpage.user.entity;


import br.com.mcampos.ejb.cloudsystem.anoto.penpage.entity.AnotoPenPage;
import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@NamedQueries( { @NamedQuery( name = PenUser.getAll, query = "select o from PenUser o where o.formId = ?1" ),
                 @NamedQuery( name = PenUser.getCurrent,
                              query = "select o from PenUser o where o.penPage = ?1 and o.toDate is null" ) } )
@Table( name = "anoto_pen_user" )
@IdClass( PenUserPK.class )
public class PenUser implements Serializable
{
    public static final String getAll = "PenUser.findAll";
    public static final String getCurrent = "PenUser.getCurrent";

    @Id
    @Column( name = "apg_id_ch", nullable = false, insertable = false, updatable = false )
    private String pageId;

    @Id
    @Column( name = "apu_seq_in", nullable = false, insertable = true, updatable = true )
    private Integer sequence;

    @Id
    @Column( name = "frm_id_in", nullable = false, insertable = false, updatable = false )
    private Integer formId;

    @Id
    @Column( name = "pad_id_in", nullable = false, insertable = false, updatable = false )
    private Integer padId;

    @Id
    @Column( name = "pdp_seq_in", nullable = false, insertable = false, updatable = false )
    private Integer penPageSequence;

    @Column( name = "usr_id_in", nullable = false, insertable = false, updatable = false )
    private Integer userId;


    @Column( name = "apu_from_dt", nullable = false )
    @Temporal( TemporalType.TIMESTAMP )
    private Date fromDate;

    @Column( name = "apu_to_dt" )
    @Temporal( TemporalType.TIMESTAMP )
    private Date toDate;


    @ManyToOne( optional = false )
    @JoinColumns( { @JoinColumn( name = "frm_id_in", referencedColumnName = "frm_id_in" ),
                    @JoinColumn( name = "pad_id_in", referencedColumnName = "pad_id_in" ),
                    @JoinColumn( name = "apg_id_ch", referencedColumnName = "apg_id_ch" ),
                    @JoinColumn( name = "pdp_seq_in", referencedColumnName = "pdp_seq_in" ) } )
    private AnotoPenPage penPage;

    @ManyToOne( optional = false, fetch = FetchType.LAZY )
    @JoinColumn( name = "usr_id_in", columnDefinition = "Integer", nullable = false )
    private Person person;

    public PenUser()
    {
    }


    public String getPageId()
    {
        return pageId;
    }

    public void setPageId( String apg_id_ch )
    {
        this.pageId = apg_id_ch;
    }

    public Date getFromDate()
    {
        return fromDate;
    }

    public void setFromDate( Date apu_from_dt )
    {
        this.fromDate = apu_from_dt;
    }

    public Integer getSequence()
    {
        return sequence;
    }

    public void setSequence( Integer apu_seq_in )
    {
        this.sequence = apu_seq_in;
    }

    public Date getToDate()
    {
        return toDate;
    }

    public void setToDate( Date apu_to_dt )
    {
        this.toDate = apu_to_dt;
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

    public Integer getPenPageSequence()
    {
        return penPageSequence;
    }

    public void setPenPageSequence( Integer seq )
    {
        this.penPageSequence = seq;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId( Integer usr_id_in )
    {
        this.userId = usr_id_in;
    }

    public void setPenPage( AnotoPenPage penPage )
    {
        this.penPage = penPage;
        setPadId( penPage != null ? penPage.getPadId() : null );
        setFormId( penPage != null ? penPage.getFormId() : null );
        setPenPageSequence( penPage != null ? penPage.getSequence() : null );
        setPageId( penPage != null ? penPage.getPageAddress() : null );
    }

    public AnotoPenPage getPenPage()
    {
        return penPage;
    }

    public void setPerson( Person person )
    {
        this.person = person;
        setUserId( person != null ? person.getId() : null );
    }

    public Person getPerson()
    {
        return person;
    }
}
