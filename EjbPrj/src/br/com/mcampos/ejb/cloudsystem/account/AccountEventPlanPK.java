package br.com.mcampos.ejb.cloudsystem.account;

import java.io.Serializable;

public class AccountEventPlanPK implements Serializable
{
    private Integer acm_id_in;
    private String acp_number_ch;
    private Integer aev_id_in;
    private Integer usr_id_in;

    public AccountEventPlanPK()
    {
    }

    public AccountEventPlanPK( Integer acm_id_in, String acp_number_ch, Integer aev_id_in, Integer usr_id_in )
    {
        this.acm_id_in = acm_id_in;
        this.acp_number_ch = acp_number_ch;
        this.aev_id_in = aev_id_in;
        this.usr_id_in = usr_id_in;
    }

    public boolean equals( Object other )
    {
        if (other instanceof AccountEventPlanPK) {
            final AccountEventPlanPK otherAccountEventPlanPK = (AccountEventPlanPK) other;
            final boolean areEqual =
                (otherAccountEventPlanPK.acm_id_in.equals(acm_id_in) && otherAccountEventPlanPK.acp_number_ch.equals(acp_number_ch) && otherAccountEventPlanPK.aev_id_in.equals(aev_id_in) && otherAccountEventPlanPK.usr_id_in.equals(usr_id_in));
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
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

    Integer getAev_id_in()
    {
        return aev_id_in;
    }

    void setAev_id_in( Integer aev_id_in )
    {
        this.aev_id_in = aev_id_in;
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
