package br.com.mcampos.ejb.cloudsystem.account;

import java.io.Serializable;

public class AccountingEntryPK implements Serializable
{
    private Integer id;
    private Integer companyId;

    public AccountingEntryPK()
    {
    }

    public AccountingEntryPK( Integer ace_id_in, Integer usr_id_in )
    {
        this.id = ace_id_in;
        this.companyId = usr_id_in;
    }

    public boolean equals( Object other )
    {
        if ( other instanceof AccountingEntryPK ) {
            final AccountingEntryPK otherAccountingEntryPK = ( AccountingEntryPK )other;
            final boolean areEqual =
                ( otherAccountingEntryPK.id.equals( id ) && otherAccountingEntryPK.companyId.equals( companyId ) );
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

    void setId( Integer ace_id_in )
    {
        this.id = ace_id_in;
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
