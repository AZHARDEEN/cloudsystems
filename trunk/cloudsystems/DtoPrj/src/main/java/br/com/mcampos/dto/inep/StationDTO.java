package br.com.mcampos.dto.inep;

import java.io.Serializable;

public class StationDTO implements Serializable
{
	private static final long serialVersionUID = 1617184241579699673L;
	private Integer userId;
	private Integer clientId;
	private String name;
	private String interalCode;


	public StationDTO( Integer userId, Integer clientId, String name, String internalCode )
	{
		super( );
		this.userId = userId;
		this.clientId = clientId;
		this.name = name;
		setInteralCode( internalCode );
	}

	public Integer getUserId( )
	{
		return userId;
	}
	public void setUserId( Integer userId )
	{
		this.userId = userId;
	}
	public Integer getClientId( )
	{
		return clientId;
	}
	public void setClientId( Integer clientId )
	{
		this.clientId = clientId;
	}
	public String getName( )
	{
		return name;
	}
	public void setName( String name )
	{
		this.name = name;
	}

	public String getInteralCode( )
	{
		return interalCode;
	}

	public void setInteralCode( String interalCode )
	{
		this.interalCode = interalCode;
	}


}
