package br.com.mcampos.dto;

import java.io.Serializable;

public class RegisterDTO implements Serializable
{
	private static final long serialVersionUID = -5579488040377907754L;

	private String name;
	private String email;
	private String document;
	private String password;

	public String getName( )
	{
		return this.name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getEmail( )
	{
		return this.email;
	}

	public void setEmail( String email )
	{
		this.email = email;
	}

	public String getDocument( )
	{
		return this.document;
	}

	public void setDocument( String document )
	{
		this.document = document;
	}

	public String getPassword( )
	{
		return this.password;
	}

	public void setPassword( String password )
	{
		this.password = password;
	}

}
