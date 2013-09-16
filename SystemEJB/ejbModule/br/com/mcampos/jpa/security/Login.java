package br.com.mcampos.jpa.security;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.mcampos.jpa.BasicEntityRenderer;
import br.com.mcampos.jpa.user.Person;
import br.com.mcampos.sysutils.SysUtils;

@Entity
@NamedQueries( { @NamedQuery( name = Login.getByToken, query = "select o from Login o where o.token = ?1" ) } )
@Table( name = "login", schema = "public" )
public class Login implements BasicEntityRenderer<Login>, Comparable<Login>
{
	private static final long serialVersionUID = 5299774581302076248L;

	public static final String getByToken = "Login.getByToken";

	@Id
	@Column( name = "usr_id_in", nullable = false, insertable = false, updatable = false )
	private Integer id;

	@Column( name = "lgi_passwd_exp_dt" )
	@Temporal( TemporalType.TIMESTAMP )
	private Date expDate;

	@Column( name = "lgi_password_ch" )
	private String password;

	@Column( name = "lgi_token_ch" )
	private String token;

	@Column( name = "lgi_try_count_in" )
	private Integer tryCount;

	@ManyToOne( optional = false )
	@JoinColumn( name = "uts_id_in", referencedColumnName = "uts_id_in", insertable = true, updatable = true, nullable = false )
	private UserStatus status;

	@OneToOne( fetch = FetchType.EAGER, optional = false )
	@JoinColumn(
			name = "usr_id_in", nullable = false, referencedColumnName = "usr_id_in", columnDefinition = "Integer",
			insertable = true, updatable = true, unique = true )
	protected Person person;

	@Transient
	private AccessLog lastLogin;

	@Transient
	private Login personify;

	public Login( )
	{
	}

	public Date getExpDate( )
	{
		return expDate;
	}

	public void setExpDate( Date date )
	{
		expDate = date;
	}

	public String getPassword( )
	{
		return password;
	}

	public void setPassword( String passwd )
	{
		password = passwd;
	}

	public String getToken( )
	{
		return token;
	}

	public void setToken( String token )
	{
		this.token = token;
	}

	public Integer getTryCount( )
	{
		if ( tryCount == null ) {
			tryCount = 0;
		}
		return tryCount;
	}

	public void setTryCount( Integer count )
	{
		tryCount = count;
	}

	@Override
	public Integer getId( )
	{
		return id;
	}

	public void setId( Integer id )
	{
		this.id = id;
	}

	public UserStatus getStatus( )
	{
		return status;
	}

	public void setStatus( UserStatus id )
	{
		status = id;
	}

	public void setPerson( Person person )
	{
		this.person = person;
		setId( getPerson( ) != null ? getPerson( ).getId( ) : null );
	}

	public Person getPerson( )
	{
		return person;
	}

	public void incrementTryCount( )
	{
		tryCount++;
	}

	@Override
	public String getField( Integer field )
	{
		switch ( field ) {
		case 0:
			return getId( ).toString( );
		case 1:
			return getStatus( ).getDescription( );
		case 2:
			return SysUtils.formatDate( getExpDate( ), "dd/MM/yyyy" );
		case 3:
			return SysUtils.formatDate( getLastLogin( ).getLoginDate( ) );
		case 4:
			return getPerson( ).getName( );
		default:
			return "";
		}
	}

	@Override
	public int compareTo( Login o, Integer field )
	{
		switch ( field ) {
		case 0:
			return o.getId( ).compareTo( getId( ) );
		case 1:
			return o.getStatus( ).getDescription( ).compareTo( getStatus( ).getDescription( ) );
		case 2:
			return o.getExpDate( ).compareTo( getExpDate( ) );
		case 3:
			return o.getLastLogin( ).getLoginDate( ).compareTo( getLastLogin( ).getLoginDate( ) );
		case 4:
			return o.getPerson( ).getName( ).compareTo( getPerson( ).getName( ) );
		default:
			return 0;
		}
	}

	@Override
	public int compareTo( Login o )
	{
		return o.getId( ).compareTo( getId( ) );
	}

	public void setLastLogin( AccessLog lastLogin )
	{
		this.lastLogin = lastLogin;
	}

	public AccessLog getLastLogin( )
	{
		return lastLogin;
	}

	@Override
	public String toString( )
	{
		return getPerson( ).getName( );
	}

	public Login getPersonify( )
	{
		return personify;
	}

	public void setPersonify( Login personify )
	{
		this.personify = personify;
	}

}
