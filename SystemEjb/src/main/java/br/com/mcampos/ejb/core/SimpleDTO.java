package br.com.mcampos.ejb.core;

import java.io.Serializable;

public class SimpleDTO implements Serializable, Comparable<SimpleDTO>
{
	private static final long serialVersionUID = -8947266378087267359L;
	private Integer id;
	private String description;


	public SimpleDTO( )
	{
		super( );
	}

	public SimpleDTO( Integer id, String description )
	{
		super( );
		this.id = id;
		this.description = description;
	}

	@Override
	public int compareTo( SimpleDTO o )
	{
		if ( o != null) {
			return getId( ).compareTo( o.getId( ) );
		}
		else {
			return 1;
		}
	}


	public Integer getId( )
	{
		return this.id;
	}


	public void setId( Integer id )
	{
		this.id = id;
	}


	public String getDescription( )
	{
		return this.description;
	}


	public void setDescription( String description )
	{
		this.description = description;
	}

}
