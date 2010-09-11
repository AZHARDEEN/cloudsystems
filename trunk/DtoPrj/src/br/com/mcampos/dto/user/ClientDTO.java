package br.com.mcampos.dto.user;

import java.io.Serializable;

import java.util.Date;


public class ClientDTO implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6708755216727326044L;
	private Integer companyId;
    private Integer sequence;
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

    public void setSequence( Integer clientId )
    {
        this.sequence = clientId;
    }

    public Integer getSequence()
    {
        return sequence;
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


    @Override
    public String toString()
    {
        return getClient().toString();
    }
}
