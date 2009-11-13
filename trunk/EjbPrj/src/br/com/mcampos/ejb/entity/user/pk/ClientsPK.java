package br.com.mcampos.ejb.entity.user.pk;

import java.io.Serializable;

import java.sql.Timestamp;

public class ClientsPK implements Serializable
{
    private Timestamp fromDate;
    private Integer clientId;
    private Integer userId;
    
    

    public ClientsPK()
    {
    }

    public ClientsPK( Timestamp cli_from_dt, Integer cli_id_in, Integer usr_id_in )
    {
        this.fromDate = cli_from_dt;
        this.clientId = cli_id_in;
        this.userId = usr_id_in;
    }

    public boolean equals( Object other )
    {
        if (other instanceof ClientsPK) {
            final ClientsPK otherClientsPK = (ClientsPK) other;
            final boolean areEqual =
                (otherClientsPK.fromDate.equals(fromDate) && otherClientsPK.clientId.equals(clientId) && otherClientsPK.userId.equals(userId));
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Timestamp getFromDate()
    {
        return fromDate;
    }

    void setFromDate( Timestamp cli_from_dt )
    {
        this.fromDate = cli_from_dt;
    }

    Integer getClientId()
    {
        return clientId;
    }

    void setClientId( Integer cli_id_in )
    {
        this.clientId = cli_id_in;
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
