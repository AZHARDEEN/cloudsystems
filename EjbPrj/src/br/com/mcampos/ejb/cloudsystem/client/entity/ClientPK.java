package br.com.mcampos.ejb.cloudsystem.client.entity;

import java.io.Serializable;

public class ClientPK implements Serializable
{
    private Integer companyId;
    private Integer clientId;

    public ClientPK()
    {
    }

    public ClientPK( Integer cli_id_in, Integer usr_id_in )
    {
        this.companyId = cli_id_in;
        this.clientId = usr_id_in;
    }

    public ClientPK( Client client )
    {
        setCompanyId( client.getCompanyId() );
        setClientId( client.getClientId() );
    }

    public boolean equals( Object other )
    {
        if ( other instanceof ClientPK ) {
            final ClientPK otherClientsPK = ( ClientPK )other;
            final boolean areEqual = ( otherClientsPK.companyId.equals( companyId ) && otherClientsPK.clientId.equals( clientId ) );
            return areEqual;
        }
        return false;
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    Integer getCompanyId()
    {
        return companyId;
    }

    void setCompanyId( Integer cli_id_in )
    {
        this.companyId = cli_id_in;
    }

    Integer getClientId()
    {
        return clientId;
    }

    void setClientId( Integer usr_id_in )
    {
        this.clientId = usr_id_in;
    }
}
