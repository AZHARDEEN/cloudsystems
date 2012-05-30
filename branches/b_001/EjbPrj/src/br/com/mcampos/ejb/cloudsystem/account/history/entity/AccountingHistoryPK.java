package br.com.mcampos.ejb.cloudsystem.account.history.entity;

import java.io.Serializable;

public class AccountingHistoryPK implements Serializable
{
    private Integer id;
    private Integer companyId;

    public AccountingHistoryPK()
    {
    }

    public AccountingHistoryPK( Integer ach_id_in, Integer usr_id_in )
    {
        this.id = ach_id_in;
        this.companyId = usr_id_in;
    }

    public boolean equals( Object other )
    {
        if ( other instanceof AccountingHistoryPK ) {
            final AccountingHistoryPK otherAccountingHistoryPK = ( AccountingHistoryPK )other;
            final boolean areEqual =
                ( otherAccountingHistoryPK.id.equals( id ) && otherAccountingHistoryPK.companyId.equals( companyId ) );
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

    void setId( Integer ach_id_in )
    {
        this.id = ach_id_in;
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
