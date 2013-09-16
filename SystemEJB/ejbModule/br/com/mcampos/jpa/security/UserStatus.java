package br.com.mcampos.jpa.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.mcampos.jpa.SimpleTable;

@Entity
@NamedQueries( { @NamedQuery( name = "UserStatus.findAll", query = "select o from UserStatus o" ) } )
@Table( name = "user_status", schema = "public" )
public class UserStatus extends SimpleTable<UserStatus>
{
	private static final long serialVersionUID = 5439295347003979636L;

	public static final int statusOk = 1;
	public static final int statusInativo = 2;
	public static final int statusEmailNotValidated = 3;
	public static final int statusMaxLoginTryCount = 4;
	public static final int statusFullfillRecord = 5;
	public static final int statusExpiredPassword = 6;

	@Column( name = "uts_allow_login_bt" )
	private Boolean allowLogin;

	@Column( name = "uts_description_ch", nullable = false )
	private String description;

	@Id
	@Column( name = "uts_id_in", nullable = false )
	private Integer id;

	public UserStatus( )
	{
	}

	public UserStatus( Boolean allow, String description, Integer id )
	{
		allowLogin = allow;
		this.description = description;
		this.id = id;
	}

	public Boolean getAllowLogin( )
	{
		return allowLogin;
	}

	public void setAllowLogin( Boolean allow )
	{
		allowLogin = allow;
	}

	@Override
	public String getDescription( )
	{
		return description;
	}

	@Override
	public void setDescription( String description )
	{
		this.description = description;
	}

	@Override
	public Integer getId( )
	{
		return id;
	}

	@Override
	public void setId( Integer id )
	{
		this.id = id;
	}
}
