package br.com.mcampos.dto;

import java.io.Serializable;

public class Authentication implements Serializable
{
	private static final long serialVersionUID = -3273622651531827077L;
	private Integer companyId;
	private Integer userId;


	public Authentication( )
	{
		super( );

	}


	public Authentication( Integer companyId, Integer userId )
	{
		super( );
		this.companyId = companyId;
		this.userId = userId;
	}


	public Integer getCompanyId( )
	{
		return this.companyId;
	}
	public void setCompanyId( Integer companyId )
	{
		this.companyId = companyId;
	}
	public Integer getUserId( )
	{
		return this.userId;
	}
	public void setUserId( Integer userId )
	{
		this.userId = userId;
	}
}
