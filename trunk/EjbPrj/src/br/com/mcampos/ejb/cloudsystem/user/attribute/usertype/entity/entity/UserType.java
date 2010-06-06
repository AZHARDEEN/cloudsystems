package br.com.mcampos.ejb.cloudsystem.user.attribute.usertype.entity.entity;


import br.com.mcampos.dto.core.SimpleTableDTO;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = UserType.getAll, query = "select o from UserType o" ),
				 @NamedQuery( name = UserType.nextId, query = "select max(o.id) from UserType o" ) } )
@Table( name = "user_type" )
public class UserType implements Serializable
{
	public static final String getAll = "UserType.findAll";
	public static final String nextId = "UserType.nextId";

	@Id
	@Column( name = "ust_id_in", nullable = false )
	protected String id;

	@Column( name = "ust_description_ch", nullable = false, length = 32 )
	protected String description;


	public UserType()
	{
	}

	public UserType( String id )
	{
		this.id = id;
	}

	public UserType( Integer id )
	{
		this.id = id.toString();
	}

	public UserType( Integer id, String description )
	{
		init( id, description );
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription( String description )
	{
		this.description = description;
	}

	public String getId()
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
	}

	protected void init( Integer id, String description )
	{
		if ( description != null && description.length() > 0 )
			setDescription( description.trim() );
		setId( id.toString() );
	}


	public void copyFrom( SimpleTableDTO dto )
	{
		setId( getId().toString() );
		setDescription( dto.getDescription() );
	}

}
