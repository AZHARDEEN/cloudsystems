package br.com.mcampos.dto;

import java.io.Serializable;

public class FieldFilterDTO implements Serializable
{
	private static final long serialVersionUID = -4640282590570477419L;

	private String displayName;
	private String name;

	public String getDisplayName( )
	{
		return this.displayName;
	}

	public void setDisplayName( String displayName )
	{
		this.displayName = displayName;
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
		return getDisplayName( );
	}

}
