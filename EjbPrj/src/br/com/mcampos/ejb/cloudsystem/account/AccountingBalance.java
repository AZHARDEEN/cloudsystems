package br.com.mcampos.ejb.cloudsystem.account;

import java.io.Serializable;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
  @NamedQuery(name = "AccountingBalance.findAll", query = "select o from AccountingBalance o")
})
@Table( name = "\"accounting_balance\"" )
@IdClass( AccountingBalancePK.class )
public class AccountingBalance implements Serializable
{
    @Id
    @Column( name="acb_date_dt", nullable = false )
    private Timestamp acb_date_dt;
    @Column( name="acb_value_nm", nullable = false )
    private String acb_value_nm;
    @Id
    @Column( name="acm_id_in", nullable = false )
    private Integer acm_id_in;
    @Id
    @Column( name="acp_number_ch", nullable = false )
    private String acp_number_ch;
    @Id
    @Column( name="usr_id_in", nullable = false )
    private Integer usr_id_in;

    public AccountingBalance()
    {
    }

    public AccountingBalance( Timestamp acb_date_dt, String acb_value_nm, Integer acm_id_in, String acp_number_ch,
                              Integer usr_id_in )
    {
        this.acb_date_dt = acb_date_dt;
        this.acb_value_nm = acb_value_nm;
        this.acm_id_in = acm_id_in;
        this.acp_number_ch = acp_number_ch;
        this.usr_id_in = usr_id_in;
    }

    public Timestamp getAcb_date_dt()
    {
        return acb_date_dt;
    }

    public void setAcb_date_dt( Timestamp acb_date_dt )
    {
        this.acb_date_dt = acb_date_dt;
    }

    public String getAcb_value_nm()
    {
        return acb_value_nm;
    }

    public void setAcb_value_nm( String acb_value_nm )
    {
        this.acb_value_nm = acb_value_nm;
    }

    public Integer getAcm_id_in()
    {
        return acm_id_in;
    }

    public void setAcm_id_in( Integer acm_id_in )
    {
        this.acm_id_in = acm_id_in;
    }

    public String getAcp_number_ch()
    {
        return acp_number_ch;
    }

    public void setAcp_number_ch( String acp_number_ch )
    {
        this.acp_number_ch = acp_number_ch;
    }

    public Integer getUsr_id_in()
    {
        return usr_id_in;
    }

    public void setUsr_id_in( Integer usr_id_in )
    {
        this.usr_id_in = usr_id_in;
    }
}
