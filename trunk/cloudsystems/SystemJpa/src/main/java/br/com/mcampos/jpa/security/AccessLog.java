package br.com.mcampos.jpa.security;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.mcampos.dto.core.CredentialDTO;
import br.com.mcampos.jpa.BasicEntityRenderer;
import br.com.mcampos.sysutils.SysUtils;

@Entity
@NamedQueries( {
		@NamedQuery( name = "AccessLog.findAll", query = "select o from AccessLog o" ),
		@NamedQuery(
				name = AccessLog.getLastLogin,
				query = "select o from AccessLog o where o.user = :user and o.loginDate = ( select max (l.loginDate) from AccessLog l where l.user = :user1 ) " ) } )
@Table( name = "access_log", schema = "public" )
public class AccessLog implements Serializable, BasicEntityRenderer<AccessLog>, Comparable<AccessLog>
{
	private static final long serialVersionUID = -4575942676953543576L;

	public static final String getLastLogin = "AccessLog.lastLogin";

	@Id
	@Column( name = "alg_id_in", nullable = false )
	@SequenceGenerator( name = "accessLogIdGenerator", sequenceName = "seq_access_log", allocationSize = 1 )
	@GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "accessLogIdGenerator" )
	private Integer id;

	@ManyToOne( optional = false )
	@JoinColumn( name = "alt_id_in", insertable = true, updatable = true, nullable = false )
	private AccessLogType type;

	@Column( name = "alg_computer_ch" )
	private String computer;

	@Column( name = "alg_ip_ch", nullable = false )
	private String ip;

	@Column( name = "alg_login_dt", nullable = false )
	private Timestamp loginDate;

	@Column( name = "acl_obs_tx" )
	private String obs;

	@Column( name = "log_program_ch" )
	private String program;

	@Column( name = "alg_session_id_ch", nullable = false )
	private String sessionId;

	@Column( name = "alg_auth_token_ch" )
	private String token;

	@ManyToOne( optional = false )
	@JoinColumn( name = "usr_id_in", insertable = true, updatable = true, nullable = false )
	private Login user;

	public AccessLog( )
	{
	}

	public AccessLog( CredentialDTO credential )
	{
		if ( credential != null ) {
			setComputer( credential.getRemoteHost( ) );
			setIp( credential.getRemoteAddr( ) );
			setProgram( credential.getProgram( ) );
			setSessionId( credential.getSessionId( ) );
		}
	}

	public AccessLogType getType( )
	{
		return type;
	}

	public void setType( AccessLogType altId )
	{
		type = altId;
	}

	public String getComputer( )
	{
		return computer;
	}

	public void setComputer( String computer )
	{
		this.computer = computer;
	}

	public Integer getId( )
	{
		return id;
	}

	public void setId( Integer id )
	{
		this.id = id;
	}

	public String getIp( )
	{
		return ip;
	}

	public void setIp( String ip )
	{
		this.ip = ip;
	}

	public Timestamp getLoginDate( )
	{
		if ( loginDate == null ) {
			loginDate = new Timestamp( ( new Date( ) ).getTime( ) );
		}
		return loginDate;
	}

	public void setLoginDate( Timestamp loginDate )
	{
		this.loginDate = loginDate;
	}

	public String getObs( )
	{
		return obs;
	}

	public void setObs( String obs )
	{
		this.obs = obs;
	}

	public String getProgram( )
	{
		return program;
	}

	public void setProgram( String program )
	{
		this.program = program;
	}

	public String getSessionId( )
	{
		return sessionId;
	}

	public void setSessionId( String sessionId )
	{
		this.sessionId = sessionId;
	}

	public String getToken( )
	{
		return token;
	}

	public void setToken( String token )
	{
		this.token = token;
	}

	public Login getUser( )
	{
		return user;
	}

	public void setUser( Login login )
	{
		user = login;
	}

	@Override
	public String getField( Integer field )
	{
		switch ( field ) {
		case 0:
			return SysUtils.formatDate( getLoginDate( ) );
		case 1:
			return getIp( );
		case 2:
			return getType( ).getDescription( );
		case 3:
			return getUser( ).getPerson( ).getName( );
		}
		return null;
	}

	@Override
	public int compareTo( AccessLog object, Integer field )
	{
		switch ( field ) {
		case 0:
			return getLoginDate( ).compareTo( object.getLoginDate( ) );
		case 1:
			return getIp( ).compareTo( object.getIp( ) );
		case 2:
			return getType( ).getDescription( ).compareTo( object.getType( ).getDescription( ) );
		case 3:
			return getUser( ).getPerson( ).getName( ).compareTo( object.getUser( ).getPerson( ).getName( ) );
		}
		return 0;
	}

	@Override
	public int compareTo( AccessLog o )
	{
		return getId( ).compareTo( o.getId( ) );
	}
}
