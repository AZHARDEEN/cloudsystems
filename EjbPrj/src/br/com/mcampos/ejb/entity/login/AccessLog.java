package br.com.mcampos.ejb.entity.login;

import java.io.Serializable;

import java.sql.Timestamp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@NamedQueries( { @NamedQuery( name = "AccessLog.findAll", query = "select o from AccessLog o" ), @NamedQuery( name = "AccessLog.findToken", query = "select o from AccessLog o where o.authenticationId = :token and o.login.userId = :userId " ) } )
@Table( name = "\"access_log\"" )
public class AccessLog implements Serializable
{

    private String comment;
    private String computer;
    private Integer id;
    private String ip;
    private Timestamp loginDateTime;
    private String program;
    private Login login;
    private AccessLogType loginType;
    private String sessionId;
    private String authenticationId;


    public AccessLog()
    {
        this.loginDateTime = new Timestamp( ( new Date() ).getTime() );
    }

    @Column( name = "acl_obs_tx" )
    public String getComment()
    {
        return comment;
    }

    public void setComment( String acl_obs_tx )
    {
        this.comment = acl_obs_tx;
    }

    @Column( name = "alg_computer_ch" )
    public String getComputer()
    {
        return computer;
    }

    public void setComputer( String alg_computer_ch )
    {
        this.computer = alg_computer_ch;
    }

    @Id
    @Column( name = "alg_id_in", nullable = false )
    @SequenceGenerator( name = "accessLogIdGenerator", sequenceName = "seq_access_log", allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "accessLogIdGenerator" )
    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    @Column( name = "alg_ip_ch", nullable = false )
    public String getIp()
    {
        return ip;
    }

    public void setIp( String alg_ip_ch )
    {
        this.ip = alg_ip_ch;
    }

    @Column( name = "alg_login_dt", nullable = false )
    public Timestamp getLoginDateTime()
    {
        return loginDateTime;
    }

    public void setLoginDateTime( Timestamp alg_login_dt )
    {
        this.loginDateTime = alg_login_dt;
    }

    @Column( name = "log_program_ch" )
    public String getProgram()
    {
        return program;
    }

    public void setProgram( String log_program_ch )
    {
        this.program = log_program_ch;
    }

    @ManyToOne
    @JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in", nullable = false )
    public Login getLogin()
    {
        return login;
    }


    public void setLogin( Login login )
    {
        this.login = login;
    }

    public void setLoginType( AccessLogType loginType )
    {
        this.loginType = loginType;
    }

    @ManyToOne( fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "alt_id_in", nullable = false, referencedColumnName = "alt_id_in", columnDefinition = "Integer" )
    public AccessLogType getLoginType()
    {
        return loginType;
    }

    public void setSessionId( String sessionId )
    {
        this.sessionId = sessionId;
    }

    @Column( name = "alg_session_id_ch", nullable = false )
    public String getSessionId()
    {
        return sessionId;
    }

    public void setAuthenticationId( String authenticationId )
    {
        this.authenticationId = authenticationId;
    }

    @Column( name = "alg_auth_token_ch", nullable = false )
    public String getAuthenticationId()
    {
        return authenticationId;
    }
}
