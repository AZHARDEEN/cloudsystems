package br.com.mcampos.ejb.cloudsystem.resale.entity;


import br.com.mcampos.ejb.cloudsystem.client.entity.Client;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;

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
@NamedQueries( { @NamedQuery( name = Resale.getAll, query = "select o from Resale o where o.company = ?1 and o.toDate is null " ),
                 @NamedQuery( name = Resale.nextSequence,
                              query = "select max(o.sequence) from Resale o where o.company = ?1 and o.toDate is null " ),
                 @NamedQuery( name = Resale.findResale,
                              query = "select o from Resale o where o.company = ?1 and o.resale = ?2 and o.toDate is null " ) } )
@Table( name = "resale" )
@IdClass( ResalePK.class )
public class Resale implements Serializable
{
    public static final String getAll = "Resale.findAll";
    public static final String nextSequence = "Resale.nextSequence";
    public static final String findResale = "Resale.findResale";

    @Column( name = "rsl_code_ch" )
    private String code;

    @Column( name = "rsl_from_dt" )
    @Temporal( value = TemporalType.TIMESTAMP )
    private Date fromDate;

    @Id
    @Column( name = "rsl_sequence_in", nullable = false )
    private Integer sequence;

    @Column( name = "rsl_to_dt" )
    @Temporal( value = TemporalType.TIMESTAMP )
    private Date toDate;

    /*
     * RESALE OWNER
     */
    @Id
    @Column( name = "usr_id_in", nullable = false, insertable = false, updatable = false )
    private Integer userId;

    @ManyToOne( optional = false )
    @JoinColumn( name = "usr_id_in", nullable = false, updatable = true, insertable = true )
    private Company company;

    /*
     * RESALE COMPANY IS A CLIENT
     */
    @Column( name = "rsl_id_in", nullable = false, insertable = false, updatable = false )
    private Integer resaleId;

    @Column( name = "cli_seq_in", nullable = false, insertable = false, updatable = false )
    private Integer resaleSequence;

    @ManyToOne( optional = false )
    @JoinColumns( { @JoinColumn( name = "rsl_id_in", referencedColumnName = "usr_id_in", nullable = false, updatable = true,
                                 insertable = true ),
                    @JoinColumn( name = "cli_seq_in", referencedColumnName = "cli_seq_in", nullable = false, updatable = true,
                                 insertable = true ) } )
    private Client resale;


    public Resale()
    {
    }

    public Resale( Company company )
    {
        setCompany( company );
    }


    public String getCode()
    {
        return code;
    }

    public void setCode( String rsl_code_ch )
    {
        this.code = rsl_code_ch;
    }

    public Date getFromDate()
    {
        return fromDate;
    }

    public void setFromDate( Date rsl_from_dt )
    {
        this.fromDate = rsl_from_dt;
    }

    public Integer getSequence()
    {
        return sequence;
    }

    public void setSequence( Integer rsl_sequence_in )
    {
        this.sequence = rsl_sequence_in;
    }

    public Date getToDate()
    {
        return toDate;
    }

    public void setToDate( Date rsl_to_dt )
    {
        this.toDate = rsl_to_dt;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId( Integer usr_id_in )
    {
        this.userId = usr_id_in;
    }

    public void setCompany( Company company )
    {
        this.company = company;
        setUserId( company != null ? company.getId() : null );
    }

    public Company getCompany()
    {
        return company;
    }

    public void setResaleId( Integer resaleId )
    {
        this.resaleId = resaleId;
    }

    public Integer getResaleId()
    {
        return resaleId;
    }

    public void setResaleSequence( Integer resaleSequence )
    {
        this.resaleSequence = resaleSequence;
    }

    public Integer getResaleSequence()
    {
        return resaleSequence;
    }

    public void setResale( Client resale )
    {
        this.resale = resale;
        setResaleSequence( resale != null ? resale.getClientId() : null );
        setResaleId( resale != null ? resale.getCompanyId() : null );
    }

    public Client getResale()
    {
        return resale;
    }
}
