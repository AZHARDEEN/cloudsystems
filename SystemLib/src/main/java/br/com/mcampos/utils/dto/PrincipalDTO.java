package br.com.mcampos.utils.dto;

import java.io.Serializable;

public class PrincipalDTO implements Serializable
{
	private static final long serialVersionUID = 2357323473807507112L;
	private Integer userId;
	private Integer companyID;
	private PrincipalDTO personify;

	public PrincipalDTO( )
	{
	}

	public PrincipalDTO( Integer companyId, Integer userId )
	{
		setCompanyID( companyId );
		setUserId( userId );
	}

	public PrincipalDTO( Integer userId )
	{
		setUserId( userId );
	}

	public Integer getUserId( )
	{
		return userId;
	}

	public void setUserId( Integer userId )
	{
		this.userId = userId;
	}

	public Integer getCompanyID( )
	{
		return companyID;
	}

	public void setCompanyID( Integer companyID )
	{
		this.companyID = companyID;
	}

	public PrincipalDTO getPersonify( )
	{
		return personify;
	}

	public void setPersonify( PrincipalDTO personify )
	{
		this.personify = personify;
	}

}
