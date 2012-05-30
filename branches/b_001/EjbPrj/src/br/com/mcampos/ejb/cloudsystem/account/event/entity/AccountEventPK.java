package br.com.mcampos.ejb.cloudsystem.account.event.entity;


import br.com.mcampos.ejb.cloudsystem.account.mask.entity.AccountingMask;

import java.io.Serializable;

public class AccountEventPK implements Serializable
{
    private Integer id;
    private Integer companyId;
    private Integer maskId;

    public AccountEventPK()
    {
    }

    public AccountEventPK( AccountingMask owner, Integer id )
    {
        setCompanyId( owner.getCompany().getId() );
        setMaskId( owner.getId() );
        setId( id );
    }


    public boolean equals( Object other )
    {
        if ( other instanceof AccountEventPK ) {
            final AccountEventPK otherAccountEventPK = ( AccountEventPK )other;
            final boolean areEqual =
                ( otherAccountEventPK.id.equals( id ) && otherAccountEventPK.companyId.equals( companyId ) && otherAccountEventPK.maskId.equals( maskId ) );
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

    public void setMaskId( Integer maskId )
    {
        this.maskId = maskId;
    }

    public Integer getMaskId()
    {
        return maskId;
    }
}
