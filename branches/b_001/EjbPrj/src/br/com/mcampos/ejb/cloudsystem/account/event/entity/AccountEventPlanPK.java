package br.com.mcampos.ejb.cloudsystem.account.event.entity;

import java.io.Serializable;

public class AccountEventPlanPK implements Serializable
{
    private Integer maskId;
    private String number;
    private Integer eventId;
    private Integer companyId;

    public AccountEventPlanPK()
    {
    }

    public boolean equals( Object other )
    {
        if ( other instanceof AccountEventPlanPK ) {
            final AccountEventPlanPK otherAccountEventPlanPK = ( AccountEventPlanPK )other;
            final boolean areEqual =
                ( otherAccountEventPlanPK.maskId.equals( maskId ) && otherAccountEventPlanPK.number.equals( number ) &&
                  otherAccountEventPlanPK.eventId.equals( eventId ) && otherAccountEventPlanPK.companyId.equals( companyId ) );
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Integer getMaskId()
    {
        return maskId;
    }

    void setMaskId( Integer acm_id_in )
    {
        this.maskId = acm_id_in;
    }

    String getNumber()
    {
        return number;
    }

    void setNumber( String acp_number_ch )
    {
        this.number = acp_number_ch;
    }

    Integer getEventId()
    {
        return eventId;
    }

    void setEventId( Integer aev_id_in )
    {
        this.eventId = aev_id_in;
    }

    Integer getCompanyId()
    {
        return companyId;
    }

    void setCompanyId( Integer usr_id_in )
    {
        this.companyId = usr_id_in;
    }
}
