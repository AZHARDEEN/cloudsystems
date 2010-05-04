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
@NamedQueries( { @NamedQuery( name = "AccessLog.findAll", query = "select o from AccessLog o" ),
                 @NamedQuery( name = "AccessLog.findToken",
                              query = "select o from AccessLog o where o.authenticationId = :token and o.login.userId = :userId " ) } )
@Table( name = "\"access_log\"" )
public class AccessLog implements Serializable
{

    @Column( name = "acl_obs_tx" )
    private String comment;
    @Column( name = "alg_computer_ch" )
    private String computer;
    @Id
    @Column( name = "alg_id_in", nullable = false )
    @SequenceGenerator( name = "accessLogIdGenerator", sequenceName = "seq_access_log", allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "accessLogIdGenerator" )
    private Integer id;
    @Column( name = "alg_ip_ch", nullable = false )
    private String ip;
    @Column( name = "alg_login_dt", nullable = false )
    private Timestamp loginDateTime;
    @Column( name = "log_program_ch" )
    private String program;
    @ManyToOne
    @JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in", nullable = false )
    private Login login;
    @ManyToOne( fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "alt_id_in", nullable = false, referencedColumnName = "alt_id_in", columnDefinition = "Integer" )
    private AccessLogType loginType;
    @Column( name = "alg_session_id_ch", nullable = false )
    private String sessionId;
    @Column( name = "alg_auth_token_ch", nullable = false )
    private String authenticationId;


    public AccessLog()
    {
        this.loginDateTime = new Timestamp( ( new Date() ).getTime() );
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment( String acl_obs_tx )
    {
        this.comment = acl_obs_tx;
    }

    public String getComputer()
    {
        return computer;
    }

    public void setComputer( String alg_computer_ch )
    {
        this.computer = alg_computer_ch;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp( String alg_ip_ch )
    {
        this.ip = alg_ip_ch;
    }

    public Timestamp getLoginDateTime()
    {
        return loginDateTime;
    }

    public void setLoginDateTime( Timestamp alg_login_dt )
    {
        this.loginDateTime = alg_login_dt;
    }

    public String getProgram()
    {
        return program;
    }

    public void setProgram( String log_program_ch )
    {
        this.program = log_program_ch;
    }

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

    public AccessLogType getLoginType()
    {
        return loginType;
    }

    public void setSessionId( String sessionId )
    {
        this.sessionId = sessionId;
    }

    public String getSessionId()
    {
        return sessionId;
    }

    public void setAuthenticationId( String authenticationId )
    {
        this.authenticationId = authenticationId;
    }

    public String getAuthenticationId()
    {
        return authenticationId;
    }
}
