package br.com.mcampos.utils.dto;

import java.io.Serializable;

public class PrincipalDTO implements Serializable
{
	private static final long serialVersionUID = 2357323473807507112L;
	private Integer userId;
	private Integer companyID;
	private PrincipalDTO personify;
	private String name;
	private Integer sequence;

	public PrincipalDTO( )
	{
	}

	public PrincipalDTO( Integer companyId, Integer userId, String name )
	{
		setCompanyID( companyId );
		setUserId( userId );
	}

	public PrincipalDTO( Integer userId, String name )
	{
		setUserId( userId );
		setName( name );
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

	public String getName( )
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	@Override
	public String toString( )
	{
		if ( getUserId( ) != null && getName( ) != null )
			return getUserId( ).toString( ) + " - " + getName( ).toString( );
		else if ( getUserId( ) != null && getCompanyID( ) != null ) {
			return "Company " + getCompanyID( ).toString( ) + " - User " + getUserId( ).toString( );
		}
		else if ( getUserId( ) != null ) {
			return "UserId: " + getUserId( ).toString( );
		}
		else
			return super.toString( );
	}

	public Integer getSequence( )
	{
		return sequence;
	}

	public void setSequence( Integer sequence )
	{
		this.sequence = sequence;
	}

}
