package br.com.mcampos.ejb.cloudsystem.user.address.entity;

import java.io.Serializable;


public class AddressPK implements Serializable
{
    private Integer addressTypeId;
    private Integer userId;

    public AddressPK()
    {
    }

    public AddressPK( Integer addressTypeId,
                      Integer userId )
    {
        setAddressTypeId( addressTypeId );
        setUserId( userId );
    }

    public boolean equals( Object other )
    {
        if (other instanceof AddressPK) {
            final AddressPK otherAddressPK = (AddressPK) other;
            final boolean areEqual =
                ( otherAddressPK.addressTypeId.equals(addressTypeId) && otherAddressPK.userId.equals(userId));
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Integer getAddressTypeId()
    {
        return addressTypeId;
    }

    void setAddressTypeId( Integer adt_id_in )
    {
        this.addressTypeId = adt_id_in;
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
