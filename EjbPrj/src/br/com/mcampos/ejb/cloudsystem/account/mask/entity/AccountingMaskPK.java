package br.com.mcampos.ejb.cloudsystem.account.mask.entity;

import java.io.Serializable;

public class AccountingMaskPK implements Serializable
{
    private Integer id;
    private Integer companyId;

    public AccountingMaskPK()
    {
    }

    public AccountingMaskPK( Integer acm_id_in, Integer usr_id_in )
    {
        this.id = acm_id_in;
        this.companyId = usr_id_in;
    }

    public boolean equals( Object other )
    {
        if ( other instanceof AccountingMaskPK ) {
            final AccountingMaskPK otherAccountingMaskPK = ( AccountingMaskPK )other;
            final boolean areEqual =
                ( otherAccountingMaskPK.id.equals( id ) && otherAccountingMaskPK.companyId.equals( companyId ) );
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Integer getId()
    {
        return id;
    }

    void setId( Integer acm_id_in )
    {
        this.id = acm_id_in;
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
