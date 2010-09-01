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
  @NamedQuery(name = "AccountingEntryValue.findAll", query = "select o from AccountingEntryValue o")
})
@Table( name = "\"accounting_entry_value\"" )
@IdClass( AccountingEntryValuePK.class )
public class AccountingEntryValue implements Serializable
{
    @Id
    @Column( name="ace_id_in", nullable = false, insertable = false, updatable = false )
    private Integer ace_id_in;
    @Id
    @Column( name="acm_id_in", nullable = false )
    private Integer acm_id_in;
    @Column( name="acn_id_in", nullable = false )
    private String acn_id_in;
    @Id
    @Column( name="acp_number_ch", nullable = false )
    private String acp_number_ch;
    @Column( name="aev_value_nm", nullable = false )
    private String aev_value_nm;
    @Id
    @Column( name="usr_id_in", nullable = false, insertable = false, updatable = false )
    private Integer usr_id_in;
    @ManyToOne
    @JoinColumns({
    @JoinColumn(name = "usr_id_in", referencedColumnName = "usr_id_in"),
    @JoinColumn(name = "ace_id_in", referencedColumnName = "ace_id_in")
    })
    private AccountingEntry accountingEntry;

    public AccountingEntryValue()
    {
    }

    public AccountingEntryValue( Integer acm_id_in, String acn_id_in, String acp_number_ch, String aev_value_nm,
                                 AccountingEntry accountingEntry )
    {
        this.acm_id_in = acm_id_in;
        this.acn_id_in = acn_id_in;
        this.acp_number_ch = acp_number_ch;
        this.aev_value_nm = aev_value_nm;
        this.accountingEntry = accountingEntry;
    }

    public Integer getAce_id_in()
    {
        return ace_id_in;
    }

    public void setAce_id_in( Integer ace_id_in )
    {
        this.ace_id_in = ace_id_in;
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

    public String getAev_value_nm()
    {
        return aev_value_nm;
    }

    public void setAev_value_nm( String aev_value_nm )
    {
        this.aev_value_nm = aev_value_nm;
    }

    public Integer getUsr_id_in()
    {
        return usr_id_in;
    }

    public void setUsr_id_in( Integer usr_id_in )
    {
        this.usr_id_in = usr_id_in;
    }

    public AccountingEntry getAccountingEntry()
    {
        return accountingEntry;
    }

    public void setAccountingEntry( AccountingEntry accountingEntry )
    {
        this.accountingEntry = accountingEntry;
        if (accountingEntry != null) {
            this.ace_id_in = accountingEntry.getId();
            this.usr_id_in = accountingEntry.getCompanyId();
        }
    }
}
