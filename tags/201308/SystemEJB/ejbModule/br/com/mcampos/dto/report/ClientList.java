package br.com.mcampos.dto.report;

import java.io.Serializable;
import java.util.Date;

public class ClientList implements Serializable
{
	private static final long serialVersionUID = -2159817847611365442L;
	private Integer code;
	private String name;
	private String document;
	private String email;
	private Date insertDate;

	public Integer getCode( )
	{
		return this.code;
	}

	public void setCode( Integer code )
	{
		this.code = code;
	}

	public String getName( )
	{
		return this.name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getDocument( )
	{
		return this.document;
	}

	public void setDocument( String document )
	{
		this.document = document;
	}

	public String getEmail( )
	{
		return this.email;
	}

	public void setEmail( String email )
	{
		this.email = email;
	}

	public Date getInsertDate( )
	{
		return this.insertDate;
	}

	public void setInsertDate( Date insertDate )
	{
		this.insertDate = insertDate;
	}
}
