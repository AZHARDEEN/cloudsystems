package br.com.mcampos.ejb.cloudsystem.account;


import br.com.mcampos.ejb.cloudsystem.account.costcenter.entity.CostCenter;
import br.com.mcampos.ejb.cloudsystem.account.event.entity.AccountEvent;
import br.com.mcampos.ejb.cloudsystem.account.history.entity.AccountingHistory;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;

import java.io.Serializable;

import java.sql.Timestamp;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = "AccountingEntry.findAll", query = "select o from AccountingEntry o" ) } )
@Table( name = "accounting_entry" )
@IdClass( AccountingEntryPK.class )
public class AccountingEntry implements Serializable
{
    @Column( name = "ace_history_tx", nullable = false )
    private String history;

    @Id
    @Column( name = "ace_id_in", nullable = false )
    private Integer id;

    @Column( name = "ace_insert_dt", nullable = false )
    private Timestamp insertDate;

    @Id
    @Column( name = "usr_id_in", nullable = false, insertable = false, updatable = false )
    private Integer companyId;

    @ManyToOne( optional = true )
    @JoinColumns( { @JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in", insertable = false, updatable = false ),
                    @JoinColumn( name = "acm_id_in", referencedColumnName = "acm_id_in" ),
                    @JoinColumn( name = "aev_id_in", referencedColumnName = "aev_id_in" ) } )
    private AccountEvent event;

    @ManyToOne
    @JoinColumns( { @JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in", insertable = false, updatable = false ),
                    @JoinColumn( name = "ach_id_in", referencedColumnName = "ach_id_in", insertable = true, updatable = true ) } )
    private AccountingHistory accountingHistory;

    @OneToMany( mappedBy = "accountingEntry" )
    private List<AccountingEntryValue> values;

    @ManyToOne( optional = true )
    @JoinColumns( { @JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in", insertable = false, updatable = false ),
                    @JoinColumn( name = "car_id_in", referencedColumnName = "car_id_in" ),
                    @JoinColumn( name = "cct_id_in", referencedColumnName = "cct_id_in" ) } )
    private CostCenter costCenter;

    @ManyToOne( optional = false )
    @JoinColumn( name = "usr_id_in", nullable = false, updatable = true, insertable = true )
    private Company company;


    public AccountingEntry()
    {
    }

    public AccountingEntry( String ace_history_tx, Integer ace_id_in, Timestamp ace_insert_dt, AccountEvent accountEvent )
    {
        this.history = ace_history_tx;
        this.id = ace_id_in;
        this.insertDate = ace_insert_dt;
        this.event = accountEvent;
    }

    public String getHistory()
    {
        return history;
    }

    public void setHistory( String ace_history_tx )
    {
        this.history = ace_history_tx;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer ace_id_in )
    {
        this.id = ace_id_in;
    }

    public Timestamp getInsertDate()
    {
        return insertDate;
    }

    public void setInsertDate( Timestamp ace_insert_dt )
    {
        this.insertDate = ace_insert_dt;
    }


    public Integer getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId( Integer usr_id_in )
    {
        this.companyId = usr_id_in;
    }

    public AccountEvent getEvent()
    {
        return event;
    }

    public void setEvent( AccountEvent accountEvent )
    {
        this.event = accountEvent;
        if ( accountEvent != null ) {
            this.companyId = accountEvent.getCompanyId();
        }
    }

    public AccountingHistory getAccountingHistory()
    {
        return accountingHistory;
    }

    public void setAccountingHistory( AccountingHistory accountingHistory )
    {
        this.accountingHistory = accountingHistory;
        if ( accountingHistory != null ) {
            this.companyId = accountingHistory.getCompanyId();
        }
    }

    public List<AccountingEntryValue> getValues()
    {
        return values;
    }

    public void setValues( List<AccountingEntryValue> accountingEntryValueList )
    {
        this.values = accountingEntryValueList;
    }

    public AccountingEntryValue addAccountingEntryValue( AccountingEntryValue accountingEntryValue )
    {
        getValues().add( accountingEntryValue );
        accountingEntryValue.setAccountingEntry( this );
        return accountingEntryValue;
    }

    public AccountingEntryValue removeAccountingEntryValue( AccountingEntryValue accountingEntryValue )
    {
        getValues().remove( accountingEntryValue );
        accountingEntryValue.setAccountingEntry( null );
        return accountingEntryValue;
    }

    public CostCenter getCostCenter()
    {
        return costCenter;
    }

    public void setCostCenter( CostCenter costCenter )
    {
        this.costCenter = costCenter;
        if ( costCenter != null ) {
            this.companyId = costCenter.getCompanyId();
        }
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
