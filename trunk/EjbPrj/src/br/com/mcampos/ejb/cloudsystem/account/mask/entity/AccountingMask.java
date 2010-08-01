package br.com.mcampos.ejb.cloudsystem.account.mask.entity;


import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = "AccountingMask.findAll", query = "select o from AccountingMask o" ) } )
@Table( name = "\"accounting_mask\"" )
public class AccountingMask implements Serializable
{
    @Column( name = "acm_mask_ch", nullable = false )
    private String mask;

    @Id
    @Column( name = "usr_id_in", nullable = false, insertable = false, updatable = false )
    private Integer companyId;

    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    @JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in", insertable = true, updatable = true )
    private Company company;


    public AccountingMask()
    {
    }

    public AccountingMask( String acm_mask_ch, Integer usr_id_in )
    {
        this.mask = acm_mask_ch;
        this.companyId = usr_id_in;
    }

    public String getMask()
    {
        return mask;
    }

    public void setMask( String acm_mask_ch )
    {
        this.mask = acm_mask_ch;
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
