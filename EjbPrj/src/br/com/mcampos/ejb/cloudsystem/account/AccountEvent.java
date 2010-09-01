package br.com.mcampos.ejb.cloudsystem.account;


import br.com.mcampos.ejb.cloudsystem.account.history.entity.AccountingHistory;
import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;

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
@NamedQueries( { @NamedQuery( name = "AccountEvent.findAll", query = "select o from AccountEvent o" ) } )
@Table( name = "account_event" )
@IdClass( AccountEventPK.class )
public class AccountEvent implements Serializable
{
    @Column( name = "aev_description_ch", nullable = false )
    private String description;

    @Column( name = "aev_history_tx" )
    private String history;

    @Id
    @Column( name = "aev_id_in", nullable = false )
    private Integer id;

    @Id
    @Column( name = "usr_id_in", nullable = false, insertable = false, updatable = false )
    private Integer companyId;

    @ManyToOne
    @JoinColumns( { @JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in", insertable = false, updatable = false ),
                    @JoinColumn( name = "ach_id_in", referencedColumnName = "ach_id_in" ) } )
    private AccountingHistory accountingHistory;

    @ManyToOne( optional = false )
    @JoinColumn( name = "usr_id_in", nullable = false, updatable = true, insertable = true )
    private Company company;


    public AccountEvent()
    {
    }

    public AccountEvent( String aev_description_ch, String aev_history_tx, Integer aev_id_in, AccountingHistory accountingHistory )
    {
        this.description = aev_description_ch;
        this.history = aev_history_tx;
        this.id = aev_id_in;
        this.accountingHistory = accountingHistory;
    }


    public String getDescription()
    {
        return description;
    }

    public void setDescription( String aev_description_ch )
    {
        this.description = aev_description_ch;
    }

    public String getHistory()
    {
        return history;
    }

    public void setHistory( String aev_history_tx )
    {
        this.history = aev_history_tx;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer aev_id_in )
    {
        this.id = aev_id_in;
    }

    public Integer getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId( Integer usr_id_in )
    {
        this.companyId = usr_id_in;
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
