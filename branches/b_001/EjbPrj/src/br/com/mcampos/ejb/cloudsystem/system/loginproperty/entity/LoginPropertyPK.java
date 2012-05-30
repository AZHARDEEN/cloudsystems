package br.com.mcampos.ejb.cloudsystem.system.loginproperty.entity;

import java.io.Serializable;

public class LoginPropertyPK implements Serializable
{
    private Integer propertyId;
    private Integer userId;
    private Integer sequence;

    public LoginPropertyPK()
    {
    }

    public LoginPropertyPK( Integer sup_id_in, Integer companyId, Integer sequence )
    {
        this.propertyId = sup_id_in;
        this.userId = companyId;
        this.sequence = sequence;
    }

    public boolean equals( Object other )
    {
        if ( other instanceof LoginPropertyPK ) {
            final LoginPropertyPK otherLoginPropertyPK = ( LoginPropertyPK )other;
            final boolean areEqual =
                ( otherLoginPropertyPK.propertyId.equals( propertyId ) && otherLoginPropertyPK.userId.equals( userId ) &&
                  otherLoginPropertyPK.sequence.equals( sequence ) );
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Integer getPropertyId()
    {
        return propertyId;
    }

    void setPropertyId( Integer sup_id_in )
    {
        this.propertyId = sup_id_in;
    }

    Integer getUserId()
    {
        return userId;
    }

    void setUserId( Integer usr_id_in )
    {
        this.userId = usr_id_in;
    }
}
