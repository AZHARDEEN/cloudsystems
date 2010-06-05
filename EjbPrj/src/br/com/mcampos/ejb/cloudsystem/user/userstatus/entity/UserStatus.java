package br.com.mcampos.ejb.cloudsystem.user.userstatus.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = UserStatus.getAll, query = "select o from UserStatus o" ),
				 @NamedQuery( name = UserStatus.nextId, query = "select max(o.id) from UserStatus o" ) } )
@Table( name = "user_status" )
public class UserStatus implements Serializable
{
	public static final String getAll = "UserStatus.findAll";
	public static final String nextId = "UserStatus.nextId";

	@Id
	@Column( name = "uts_id_in", nullable = false )
	protected Integer id;
	@Column( name = "uts_description_ch", nullable = false, length = 32 )
	protected String description;
	@Column( name = "uts_allow_login_bt", nullable = true, columnDefinition = "boolean" )
	protected Boolean allowLogin;


	public static final int statusOk = 1;
	public static final int statusInativo = 2;
	public static final int statusEmailNotValidated = 3;
	public static final int statusMaxLoginTryCount = 4;
	public static final int statusFullfillRecord = 5;
	public static final int statusExpiredPassword = 6;


	public UserStatus()
	{
	}

	public UserStatus( Integer id )
	{
		this.id = id;
	}

	public UserStatus( Integer id, String description )
	{
		this.id = id;
		this.description = description;
	}


	public String getDescription()
	{
		return description;
	}

	public void setDescription( String description )
	{
		this.description = description;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId( Integer id )
	{
		this.id = id;
	}

	public Boolean getAllowLogin()
	{
		return allowLogin;
	}

	public void setAllowLogin( Boolean allowLogin )
	{
		this.allowLogin = allowLogin;
	}
}
