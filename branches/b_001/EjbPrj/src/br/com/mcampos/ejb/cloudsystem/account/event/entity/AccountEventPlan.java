package br.com.mcampos.ejb.cloudsystem.account.event.entity;


import br.com.mcampos.ejb.cloudsystem.account.nature.entity.AccountingNature;

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
@NamedQueries( { @NamedQuery( name = "AccountEventPlan.findAll", query = "select o from AccountEventPlan o" ) } )
@Table( name = "account_event_plan" )
@IdClass( AccountEventPlanPK.class )
public class AccountEventPlan implements Serializable
{
    @Id
    @Column( name = "usr_id_in", nullable = false, insertable = false, updatable = false )
    private Integer companyId;

    @Id
    @Column( name = "acm_id_in", nullable = false, insertable = false, updatable = false )
    private Integer maskId;

    @Id
    @Column( name = "aev_id_in", nullable = false, insertable = false, updatable = false )
    private Integer eventId;

    @Id
    @Column( name = "acp_number_ch", nullable = false )
    private String number;

    @Column( name = "acp_rate_mn" )
    private Double rate;

    @ManyToOne( optional = true )
    @JoinColumns( { @JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in" ),
                    @JoinColumn( name = "acm_id_in", referencedColumnName = "acm_id_in" ),
                    @JoinColumn( name = "aev_id_in", referencedColumnName = "aev_id_in" ) } )
    private AccountEvent event;

    @ManyToOne( optional = false )
    @JoinColumn( name = "acn_id_in" )
    private AccountingNature nature;

    @ManyToOne( optional = false )
    @JoinColumn( name = "act_id_in" )
    private AccountingRateType type;


    public AccountEventPlan()
    {
    }

    public Integer getMaskId()
    {
        return maskId;
    }

    public void setMaskId( Integer acm_id_in )
    {
        this.maskId = acm_id_in;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber( String acp_number_ch )
    {
        this.number = acp_number_ch;
    }

    public Double getRate()
    {
        return rate;
    }

    public void setRate( Double acp_rate_mn )
    {
        this.rate = acp_rate_mn;
    }

    public Integer getEventId()
    {
        return eventId;
    }

    public void setEventId( Integer aev_id_in )
    {
        this.eventId = aev_id_in;
    }

    public Integer getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId( Integer usr_id_in )
    {
        this.companyId = usr_id_in;
    }

    public AccountEvent getAccountEvent()
    {
        return event;
    }

    public void setAccountEvent( AccountEvent accountEvent )
    {
        this.event = accountEvent;
        if ( accountEvent != null ) {
            this.eventId = accountEvent.getId();
            this.companyId = accountEvent.getCompanyId();
        }
    }

    public void setNature( AccountingNature nature )
    {
        this.nature = nature;
    }

    public AccountingNature getNature()
    {
        return nature;
    }

    public void setType( AccountingRateType type )
    {
        this.type = type;
    }

    public AccountingRateType getType()
    {
        return type;
    }
}
