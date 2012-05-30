package br.com.mcampos.ejb.entity.system;

import java.io.Serializable;

public class SystemMessagePK implements Serializable
{
    private Integer id;
    private Integer messageTypeID;

    public SystemMessagePK()
    {
    }

    public SystemMessagePK( Integer id, Integer messageTypeId )
    {
        this.id = id;
        this.messageTypeID = messageTypeId;
    }

    public boolean equals( Object other )
    {
        if (other instanceof SystemMessagePK) {
            final SystemMessagePK otherSystemMessagePK = (SystemMessagePK) other;
            final boolean areEqual = (otherSystemMessagePK.id.equals(id) && otherSystemMessagePK.messageTypeID.equals(messageTypeID));
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

    void setId( Integer smg_id_in )
    {
        this.id = smg_id_in;
    }

    Integer getMessageTypeID()
    {
        return messageTypeID;
    }

    void setMessageTypeID( Integer smt_id_in )
    {
        this.messageTypeID = smt_id_in;
    }
}
