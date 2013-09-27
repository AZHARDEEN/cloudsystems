package br.com.mcampos.dto.core;

import java.io.Serializable;

/**
 * Brief DTO para persistir na sessão qual o usuário logado no sistema. Junto com o usuário, está vinculado também a empresa do usuário Então é esperado
 * que esta classe tenha ao menos usuário e empresa
 * 
 */
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
		this.setCompanyID( companyId );
		this.setUserId( userId );
	}

	public PrincipalDTO( Integer userId, String name )
	{
		this.setUserId( userId );
		this.setName( name );
	}

	public Integer getUserId( )
	{
		return this.userId;
	}

	public void setUserId( Integer userId )
	{
		this.userId = userId;
	}

	public Integer getCompanyID( )
	{
		return this.companyID;
	}

	public void setCompanyID( Integer companyID )
	{
		this.companyID = companyID;
	}

	public PrincipalDTO getPersonify( )
	{
		return this.personify;
	}

	public void setPersonify( PrincipalDTO personify )
	{
		this.personify = personify;
	}

	public String getName( )
	{
		return this.name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	@Override
	public String toString( )
	{
		if ( this.getUserId( ) != null && this.getName( ) != null ) {
			return this.getUserId( ).toString( ) + " - " + this.getName( ).toString( );
		}
		else if ( this.getUserId( ) != null && this.getCompanyID( ) != null ) {
			return "Company " + this.getCompanyID( ).toString( ) + " - User " + this.getUserId( ).toString( );
		}
		else if ( this.getUserId( ) != null ) {
			return "UserId: " + this.getUserId( ).toString( );
		}
		else {
			return super.toString( );
		}
	}

	public Integer getSequence( )
	{
		return this.sequence;
	}

	public void setSequence( Integer sequence )
	{
		this.sequence = sequence;
	}

}
