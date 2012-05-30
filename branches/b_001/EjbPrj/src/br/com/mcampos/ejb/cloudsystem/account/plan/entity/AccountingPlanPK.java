package br.com.mcampos.ejb.cloudsystem.account.plan.entity;


import br.com.mcampos.ejb.cloudsystem.account.mask.entity.AccountingMask;

import java.io.Serializable;

public class AccountingPlanPK implements Serializable
{
    private Integer maskId;
    private String number;
    private Integer companyId;

    public AccountingPlanPK()
    {
    }

    public AccountingPlanPK( AccountingMask mask, String number )
    {
        setCompanyId( mask.getCompanyId() );
        setMaskId( mask.getId() );
        setNumber( number );
    }

    public boolean equals( Object other )
    {
        if ( other instanceof AccountingPlanPK ) {
            final AccountingPlanPK otherAccountingPlanPK = ( AccountingPlanPK )other;
            final boolean areEqual =
                ( otherAccountingPlanPK.maskId.equals( maskId ) && otherAccountingPlanPK.number.equals( number ) &&
                  otherAccountingPlanPK.companyId.equals( companyId ) );
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

    Integer getCompanyId()
    {
        return companyId;
    }

    void setCompanyId( Integer usr_id_in )
    {
        this.companyId = usr_id_in;
    }
}
