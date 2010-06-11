package br.com.mcampos.dto.user;

import java.io.Serializable;

import java.util.Date;


public class ClientDTO implements Serializable
{
    private Integer companyId;
    private Integer clientId;
    private Date insertDate;
    ListUserDTO client;

    public ClientDTO()
    {
        super();
    }

    public void setCompanyId( Integer companyId )
    {
        this.companyId = companyId;
    }

    public Integer getCompanyId()
    {
        return companyId;
    }

    public void setClientId( Integer clientId )
    {
        this.clientId = clientId;
    }

    public Integer getClientId()
    {
        return clientId;
    }

    public void setClient( ListUserDTO client )
    {
        this.client = client;
    }

    public ListUserDTO getClient()
    {
        return client;
    }

    public void setInsertDate( Date insertDate )
    {
        this.insertDate = insertDate;
    }

    public Date getInsertDate()
    {
        return insertDate;
    }
}
