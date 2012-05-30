package br.com.mcampos.ejb.cloudsystem.account;

import java.io.Serializable;

import java.sql.Timestamp;

public class AccountingBalancePK implements Serializable
{
    private Timestamp acb_date_dt;
    private Integer acm_id_in;
    private String acp_number_ch;
    private Integer usr_id_in;

    public AccountingBalancePK()
    {
    }

    public AccountingBalancePK( Timestamp acb_date_dt, Integer acm_id_in, String acp_number_ch, Integer usr_id_in )
    {
        this.acb_date_dt = acb_date_dt;
        this.acm_id_in = acm_id_in;
        this.acp_number_ch = acp_number_ch;
        this.usr_id_in = usr_id_in;
    }

    public boolean equals( Object other )
    {
        if (other instanceof AccountingBalancePK) {
            final AccountingBalancePK otherAccountingBalancePK = (AccountingBalancePK) other;
            final boolean areEqual =
                (otherAccountingBalancePK.acb_date_dt.equals(acb_date_dt) && otherAccountingBalancePK.acm_id_in.equals(acm_id_in) && otherAccountingBalancePK.acp_number_ch.equals(acp_number_ch) && otherAccountingBalancePK.usr_id_in.equals(usr_id_in));
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Timestamp getAcb_date_dt()
    {
        return acb_date_dt;
    }

    void setAcb_date_dt( Timestamp acb_date_dt )
    {
        this.acb_date_dt = acb_date_dt;
    }

    Integer getAcm_id_in()
    {
        return acm_id_in;
    }

    void setAcm_id_in( Integer acm_id_in )
    {
        this.acm_id_in = acm_id_in;
    }

    String getAcp_number_ch()
    {
        return acp_number_ch;
    }

    void setAcp_number_ch( String acp_number_ch )
    {
        this.acp_number_ch = acp_number_ch;
    }

    Integer getUsr_id_in()
    {
        return usr_id_in;
    }

    void setUsr_id_in( Integer usr_id_in )
    {
        this.usr_id_in = usr_id_in;
    }
}
