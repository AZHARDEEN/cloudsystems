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
	@Column( name = "usr_id_in", nullable = false, insertable = true, updatable = true, unique = true )
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

	@OneToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in", insertable = false, updatable = false )
	private Person person;

	@Transient
	private AccessLog lastLogin;

	@Transient
	private Login personify;

	public Login( )
	{
	}

	public Date getExpDate( )
	{
		return this.expDate;
	}

	public void setExpDate( Date date )
	{
		this.expDate = date;
	}

	public String getPassword( )
	{
		return this.password;
	}

	public void setPassword( String passwd )
	{
		this.password = passwd;
	}

	public String getToken( )
	{
		return this.token;
	}

	public void setToken( String token )
	{
		this.token = token;
	}

	public Integer getTryCount( )
	{
		if ( this.tryCount == null ) {
			this.tryCount = 0;
		}
		return this.tryCount;
	}

	public void setTryCount( Integer count )
	{
		this.tryCount = count;
	}

	@Override
	public Integer getId( )
	{
		return this.id;
	}

	public void setId( Integer id )
	{
		this.id = id;
	}

	public UserStatus getStatus( )
	{
		return this.status;
	}

	public void setStatus( UserStatus id )
	{
		this.status = id;
	}

	public void setPerson( Person person )
	{
		this.person = person;
		this.setId( this.getPerson( ) != null ? this.getPerson( ).getId( ) : null );
	}

	public Person getPerson( )
	{
		return this.person;
	}

	public void incrementTryCount( )
	{
		this.tryCount++;
	}

	@Override
	public String getField( Integer field )
	{
		switch ( field ) {
		case 0:
			return this.getId( ).toString( );
		case 1:
			return this.getStatus( ).getDescription( );
		case 2:
			return SysUtils.formatDate( this.getExpDate( ), "dd/MM/yyyy" );
		case 3:
			return SysUtils.formatDate( this.getLastLogin( ).getLoginDate( ) );
		case 4:
			return this.getPerson( ).getName( );
		default:
			return "";
		}
	}

	@Override
	public int compareTo( Login o, Integer field )
	{
		switch ( field ) {
		case 0:
			return o.getId( ).compareTo( this.getId( ) );
		case 1:
			return o.getStatus( ).getDescription( ).compareTo( this.getStatus( ).getDescription( ) );
		case 2:
			return o.getExpDate( ).compareTo( this.getExpDate( ) );
		case 3:
			return o.getLastLogin( ).getLoginDate( ).compareTo( this.getLastLogin( ).getLoginDate( ) );
		case 4:
			return o.getPerson( ).getName( ).compareTo( this.getPerson( ).getName( ) );
		default:
			return 0;
		}
	}

	@Override
	public int compareTo( Login o )
	{
		return o.getId( ).compareTo( this.getId( ) );
	}

	public void setLastLogin( AccessLog lastLogin )
	{
		this.lastLogin = lastLogin;
	}

	public AccessLog getLastLogin( )
	{
		return this.lastLogin;
	}

	@Override
	public String toString( )
	{
		return this.getPerson( ).getName( );
	}

	public Login getPersonify( )
	{
		return this.personify;
	}

	public void setPersonify( Login personify )
	{
		this.personify = personify;
	}

}
