package br.com.mcampos.ejb.cloudsystem.account.plan.entity;


import br.com.mcampos.ejb.cloudsystem.user.company.entity.Company;

import java.io.Serializable;

public class AccountingPlanPK implements Serializable
{
    private String number;
    private Integer companyId;

    public AccountingPlanPK()
    {
    }

    public AccountingPlanPK( Company company, String accNumber )
    {
        this.number = accNumber;
        this.companyId = company.getId();
    }

    public boolean equals( Object other )
    {
        if ( other instanceof AccountingPlanPK ) {
            final AccountingPlanPK otherAccountingPlanPK = ( AccountingPlanPK )other;
            final boolean areEqual =
                ( otherAccountingPlanPK.number.equals( number ) && otherAccountingPlanPK.companyId.equals( companyId ) );
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
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
