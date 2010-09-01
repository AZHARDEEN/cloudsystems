package br.com.mcampos.ejb.cloudsystem.account;

import java.io.Serializable;

public class AccountEventPK implements Serializable
{
    private Integer id;
    private Integer companyId;

    public AccountEventPK()
    {
    }

    public AccountEventPK( Integer aev_id_in, Integer usr_id_in )
    {
        this.id = aev_id_in;
        this.companyId = usr_id_in;
    }

    public boolean equals( Object other )
    {
        if ( other instanceof AccountEventPK ) {
            final AccountEventPK otherAccountEventPK = ( AccountEventPK )other;
            final boolean areEqual = ( otherAccountEventPK.id.equals( id ) && otherAccountEventPK.companyId.equals( companyId ) );
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

    void setId( Integer aev_id_in )
    {
        this.id = aev_id_in;
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
