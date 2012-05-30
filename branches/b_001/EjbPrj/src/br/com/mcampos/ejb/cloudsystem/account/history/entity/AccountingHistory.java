package br.com.mcampos.ejb.cloudsystem.account.history.entity;


import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = AccountingHistory.getAll,
                              query = "select o from AccountingHistory o where o.company = ?1 order by o.id" ),
                 @NamedQuery( name = AccountingHistory.nextId,
                              query = "select max(o.id) from AccountingHistory o where o.company = ?1" ) } )
@Table( name = "accounting_history" )
@IdClass( AccountingHistoryPK.class )
public class AccountingHistory implements Serializable
{
    public static final String getAll = "AccountingHistory.findAll";
    public static final String nextId = "AccountingHistory.nextId";

    @Column( name = "ach_history_tx", nullable = false )
    private String history;

    @Column( name = "ach_description_ch", nullable = false )
    private String description;

    @Id
    @Column( name = "ach_id_in", nullable = false )
    private Integer id;

    @Id
    @Column( name = "usr_id_in", nullable = false, updatable = false, insertable = false )
    private Integer companyId;

    @ManyToOne( optional = false )
    @JoinColumn( name = "usr_id_in", nullable = false, updatable = true, insertable = true )
    private Company company;


    public AccountingHistory()
    {
    }

    public AccountingHistory( Company owner, Integer id )
    {
        setCompany( owner );
        setId( id );
    }

    public String getHistory()
    {
        return history;
    }

    public void setHistory( String ach_history_tx )
    {
        this.history = ach_history_tx;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer ach_id_in )
    {
        this.id = ach_id_in;
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

    public void setDescription( String description )
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
}
