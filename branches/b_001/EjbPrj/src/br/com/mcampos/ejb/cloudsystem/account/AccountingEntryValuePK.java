package br.com.mcampos.ejb.cloudsystem.account;

import java.io.Serializable;

public class AccountingEntryValuePK implements Serializable
{
    private Integer ace_id_in;
    private Integer acm_id_in;
    private String acp_number_ch;
    private Integer usr_id_in;

    public AccountingEntryValuePK()
    {
    }

    public AccountingEntryValuePK( Integer ace_id_in, Integer acm_id_in, String acp_number_ch, Integer usr_id_in )
    {
        this.ace_id_in = ace_id_in;
        this.acm_id_in = acm_id_in;
        this.acp_number_ch = acp_number_ch;
        this.usr_id_in = usr_id_in;
    }

    public boolean equals( Object other )
    {
        if (other instanceof AccountingEntryValuePK) {
            final AccountingEntryValuePK otherAccountingEntryValuePK = (AccountingEntryValuePK) other;
            final boolean areEqual =
                (otherAccountingEntryValuePK.ace_id_in.equals(ace_id_in) && otherAccountingEntryValuePK.acm_id_in.equals(acm_id_in) && otherAccountingEntryValuePK.acp_number_ch.equals(acp_number_ch) && otherAccountingEntryValuePK.usr_id_in.equals(usr_id_in));
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Integer getAce_id_in()
    {
        return ace_id_in;
    }

    void setAce_id_in( Integer ace_id_in )
    {
        this.ace_id_in = ace_id_in;
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
