package br.com.mcampos.ejb.cloudsystem.account;

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
@NamedQueries({
  @NamedQuery(name = "AccountEventPlan.findAll", query = "select o from AccountEventPlan o")
})
@Table( name = "\"account_event_plan\"" )
@IdClass( AccountEventPlanPK.class )
public class AccountEventPlan implements Serializable
{
    @Id
    @Column( name="acm_id_in", nullable = false )
    private Integer acm_id_in;
    @Column( name="acn_id_in", nullable = false )
    private String acn_id_in;
    @Id
    @Column( name="acp_number_ch", nullable = false )
    private String acp_number_ch;
    @Column( name="acp_rate_mn" )
    private String acp_rate_mn;
    @Id
    @Column( name="aev_id_in", nullable = false, insertable = false, updatable = false )
    private Integer aev_id_in;
    @Id
    @Column( name="usr_id_in", nullable = false, insertable = false, updatable = false )
    private Integer usr_id_in;
    @ManyToOne
    @JoinColumns({
    @JoinColumn(name = "usr_id_in", referencedColumnName = "usr_id_in"),
    @JoinColumn(name = "aev_id_in", referencedColumnName = "aev_id_in")
    })
    private AccountEvent accountEvent;

    public AccountEventPlan()
    {
    }

    public AccountEventPlan( Integer acm_id_in, String acn_id_in, String acp_number_ch, String acp_rate_mn,
                             AccountEvent accountEvent )
    {
        this.acm_id_in = acm_id_in;
        this.acn_id_in = acn_id_in;
        this.acp_number_ch = acp_number_ch;
        this.acp_rate_mn = acp_rate_mn;
        this.accountEvent = accountEvent;
    }

    public Integer getAcm_id_in()
    {
        return acm_id_in;
    }

    public void setAcm_id_in( Integer acm_id_in )
    {
        this.acm_id_in = acm_id_in;
    }

    public String getAcn_id_in()
    {
        return acn_id_in;
    }

    public void setAcn_id_in( String acn_id_in )
    {
        this.acn_id_in = acn_id_in;
    }

    public String getAcp_number_ch()
    {
        return acp_number_ch;
    }

    public void setAcp_number_ch( String acp_number_ch )
    {
        this.acp_number_ch = acp_number_ch;
    }

    public String getAcp_rate_mn()
    {
        return acp_rate_mn;
    }

    public void setAcp_rate_mn( String acp_rate_mn )
    {
        this.acp_rate_mn = acp_rate_mn;
    }

    public Integer getAev_id_in()
    {
        return aev_id_in;
    }

    public void setAev_id_in( Integer aev_id_in )
    {
        this.aev_id_in = aev_id_in;
    }

    public Integer getUsr_id_in()
    {
        return usr_id_in;
    }

    public void setUsr_id_in( Integer usr_id_in )
    {
        this.usr_id_in = usr_id_in;
    }

    public AccountEvent getAccountEvent()
    {
        return accountEvent;
    }

    public void setAccountEvent( AccountEvent accountEvent )
    {
        this.accountEvent = accountEvent;
        if (accountEvent != null) {
            this.aev_id_in = accountEvent.getId();
            this.usr_id_in = accountEvent.getCompanyId();
        }
    }
}
