package br.com.mcampos.ejb.cloudsystem.account.plan.entity;


import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = AccountingPlan.getAll, query = "select o from AccountingPlan o where o.company = ?1" ) } )
@Table( name = "accounting_plan" )
@IdClass( AccountingPlanPK.class )
public class AccountingPlan implements Serializable
{
    public static final String getAll = "AccountingPlan.findAll";

    @Column( name = "acp_balance_mn" )
    private Double balance;

    @Id
    @Column( name = "acp_number_ch", nullable = false )
    private String number;

    @Column( name = "acp_simplified_number_ch" )
    private String shortNumber;

    @Column( name = "acp_description_ch", nullable = false )
    private String description;

    @Id
    @Column( name = "usr_id_in", nullable = false, insertable = false, updatable = false )
    private Integer companyId;


    @ManyToOne( fetch = FetchType.LAZY, optional = false )
    @JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in", insertable = true, updatable = true )
    private Company company;

    public AccountingPlan()
    {
    }

    public AccountingPlan( Company owner, String accNumber )
    {
        setCompany( owner );
        setNumber( accNumber );
    }

    public Double getBalance()
    {
        return balance;
    }

    public void setBalance( Double value )
    {
        this.balance = value;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber( String acp_number_ch )
    {
        this.number = acp_number_ch;
    }

    public String getShortNumber()
    {
        return shortNumber;
    }

    public void setShortNumber( String acp_simplified_number_ch )
    {
        this.shortNumber = acp_simplified_number_ch;
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

    @Override
    public String toString()
    {
        return getNumber();
    }

    @Override
    public boolean equals( Object obj )
    {
        return getCompany().equals( obj ) && getNumber().equals( ( ( AccountingPlan )obj ).getNumber() );
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
}
