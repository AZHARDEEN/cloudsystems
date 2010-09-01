package br.com.mcampos.ejb.cloudsystem.account.mask.entity;


import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@NamedQueries( { @NamedQuery( name = AccountingMask.getAll,
                              query = "select o from AccountingMask o where o.company = ?1 order by o.id" ),
                 @NamedQuery( name = AccountingMask.nextId,
                              query = "select max(o.id) from AccountingMask o where o.company = ?1" ) } )
@Table( name = "accounting_mask" )
@IdClass( AccountingMaskPK.class )
public class AccountingMask implements Serializable
{
    public static final String getAll = "AccountingMask.findAll";
    public static final String nextId = "AccountingMask.nextId";

    @Column( name = "acm_description_ch" )
    private String description;

    @Column( name = "acm_from_dt", nullable = false )
    @Temporal( TemporalType.TIMESTAMP )
    private Date from;

    @Id
    @Column( name = "acm_id_in", nullable = false )
    private Integer id;

    @Column( name = "acm_mask_ch", nullable = false )
    private String mask;

    @Column( name = "acm_to_date" )
    @Temporal( TemporalType.TIMESTAMP )
    private Date to;

    @Id
    @Column( name = "usr_id_in", nullable = false, insertable = false, updatable = false )
    private Integer companyId;

    @ManyToOne( optional = false )
    @JoinColumn( name = "usr_id_in", nullable = false, updatable = true, insertable = true )
    private Company company;

    public AccountingMask()
    {
    }

    public AccountingMask( Company owner, Integer id )
    {
        setCompany( owner );
        setId( id );
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String acm_description_ch )
    {
        this.description = acm_description_ch;
    }

    public Date getFrom()
    {
        return from;
    }

    public void setFrom( Date acm_from_dt )
    {
        this.from = acm_from_dt;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer acm_id_in )
    {
        this.id = acm_id_in;
    }

    public String getMask()
    {
        return mask;
    }

    public void setMask( String acm_mask_ch )
    {
        this.mask = acm_mask_ch;
    }

    public Date getTo()
    {
        return to;
    }

    public void setTo( Date acm_to_date )
    {
        this.to = acm_to_date;
    }

    public Integer getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId( Integer usr_id_in )
    {
        this.companyId = usr_id_in;
    }

    public void setCompany( Company company )
    {
        this.company = company;
        setCompanyId( company != null ? company.getId() : null );
    }

    public Company getCompany()
    {
        return company;
    }
}
