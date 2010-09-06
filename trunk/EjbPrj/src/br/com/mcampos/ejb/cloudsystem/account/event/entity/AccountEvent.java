package br.com.mcampos.ejb.cloudsystem.account.event.entity;


import br.com.mcampos.ejb.cloudsystem.account.history.entity.AccountingHistory;
import br.com.mcampos.ejb.cloudsystem.account.mask.entity.AccountingMask;

import java.io.Serializable;

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
@NamedQueries( { @NamedQuery( name = AccountEvent.getAll, query = "select o from AccountEvent o where o.mask = ?1 order by o.id" ),
                 @NamedQuery( name = AccountEvent.nextId, query = "select max (o.id) from AccountEvent o where o.mask = ?1" ) } )
@Table( name = "account_event" )
@IdClass( AccountEventPK.class )
public class AccountEvent implements Serializable
{
    public static final String getAll = "AccountEvent.findAll";
    public static final String nextId = "AccountEvent.nextId";


    @Id
    @Column( name = "usr_id_in", nullable = false, insertable = false, updatable = false )
    private Integer companyId;

    @Id
    @Column( name = "acm_id_in", nullable = false, insertable = false, updatable = false )
    private Integer maskId;

    @Id
    @Column( name = "aev_id_in", nullable = false )
    private Integer id;

    @Column( name = "aev_description_ch", nullable = false )
    private String description;

    @Column( name = "aev_history_tx" )
    private String history;

    @ManyToOne( optional = true )
    @JoinColumns( { @JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in", insertable = false, updatable = false ),
                    @JoinColumn( name = "ach_id_in", referencedColumnName = "ach_id_in" ) } )
    private AccountingHistory accountingHistory;

    @ManyToOne( optional = false )
    @JoinColumns( { @JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in" ),
                    @JoinColumn( name = "acm_id_in", referencedColumnName = "acm_id_in" ) } )
    private AccountingMask mask;

    @OneToMany( mappedBy = "event" )
    private List<AccountEventPlan> items;


    public AccountEvent()
    {
    }

    public AccountEvent( AccountingMask owner, Integer id )
    {
        setMask( owner );
        setId( id );
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
    }

    public void setMask( AccountingMask mask )
    {
        this.mask = mask;
        setMaskId( mask != null ? mask.getId() : null );
        setCompanyId( mask != null ? mask.getCompany().getId() : null );
    }

    public AccountingMask getMask()
    {
        return mask;
    }

    public void setMaskId( Integer maskId )
    {
        this.maskId = maskId;
    }

    public Integer getMaskId()
    {
        return maskId;
    }

    public void setItems( List<AccountEventPlan> items )
    {
        this.items = items;
    }

    public List<AccountEventPlan> getItems()
    {
        return items;
    }

    public AccountEventPlan add( AccountEventPlan item )
    {
        getItems().add( item );
        item.setAccountEvent( this );
        return item;
    }

    public AccountEventPlan remove( AccountEventPlan item )
    {
        getItems().remove( item );
        item.setAccountEvent( null );
        return item;
    }

}
