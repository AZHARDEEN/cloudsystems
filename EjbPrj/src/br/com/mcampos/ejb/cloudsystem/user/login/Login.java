package br.com.mcampos.ejb.cloudsystem.user.login;


import br.com.mcampos.ejb.cloudsystem.user.person.entity.Person;
import br.com.mcampos.ejb.cloudsystem.user.login.lastusedpassword.LastUsedPassword;
import br.com.mcampos.ejb.cloudsystem.user.attribute.userstatus.entity.UserStatus;

import java.io.Serializable;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/** Entity bean Login. Esta é a classe que mapeia a tabela Login.
 *
 * @author Marcelo de Campos
 * @version 1.0
 */
@Entity
@NamedQueries( { @NamedQuery( name = "Login.findAll", query = "select o from Login o" ),
                 @NamedQuery( name = "Login.findToken", query = "select o from Login o where o.token = :token" ) } )
@Table( name = "login" )
public class Login implements Serializable
{
    @Id
    @Column( name = "usr_id_in", nullable = false, insertable = false, updatable = false )
    private Integer userId;
    @Column( name = "lgi_passwd_exp_dt" )
    private Timestamp passwordExpirationDate;
    @Column( name = "lgi_password_ch", nullable = false, columnDefinition = "VARCHAR(64)" )
    private String password;
    @Column( name = "lgi_try_count_in", nullable = true, columnDefinition = "Integer" )
    private Integer tryCount;
    @Column( name = "lgi_token_ch", nullable = true, columnDefinition = "VARCHAR(32)" )
    private String token;


    @OneToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "usr_id_in", nullable = false, referencedColumnName = "usr_id_in", columnDefinition = "Integer" )
    protected Person person;
    @ManyToOne( fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "uts_id_in", nullable = false, referencedColumnName = "uts_id_in", columnDefinition = "Integer" )
    protected UserStatus userStatus;
    @OneToMany( mappedBy = "login", cascade = CascadeType.ALL )
    protected List<LastUsedPassword> lastUsedPasswords;

    public Login()
    {
        super();
    }

    public Login( Timestamp passwordExpirationDate, String password, UserStatus userType )
    {
        super();
        init( passwordExpirationDate, password, userType );
    }


    protected void init( Timestamp usr_passwd_exp_dt, String usr_password_ch, UserStatus userType )
    {
        setPasswordExpirationDate( usr_passwd_exp_dt );
        setPassword( usr_password_ch );
        setUserStatus( userType );
    }


    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId( Integer usr_id_in )
    {
        this.userId = usr_id_in;
    }

    public Timestamp getPasswordExpirationDate()
    {
        return passwordExpirationDate;
    }

    public void setPasswordExpirationDate( Timestamp usr_passwd_exp_dt )
    {
        this.passwordExpirationDate = usr_passwd_exp_dt;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String usr_password_ch )
    {
        this.password = usr_password_ch;
    }


    public void setPerson( Person p )
    {
        this.person = p;
    }

    public Person getPerson()
    {
        return person;
    }

    public void setUserStatus( UserStatus userType )
    {
        this.userStatus = userType;
    }

    public UserStatus getUserStatus()
    {
        return userStatus;
    }

    public void setTryCount( Integer tryCount )
    {
        this.tryCount = tryCount;
    }

    public Integer getTryCount()
    {
        return tryCount;
    }

    public Integer incrementTryCount()
    {
        if ( getTryCount() == null )
            setTryCount( new Integer( 1 ) );
        else
            setTryCount( getTryCount() + 1 );
        return getTryCount();
    }

    public void setToken( String token )
    {
        this.token = token;
    }

    public String getToken()
    {
        return token;
    }

    public void setLastUsedPasswords( List<LastUsedPassword> lastUsedPasswords )
    {
        this.lastUsedPasswords = lastUsedPasswords;
    }

    public List<LastUsedPassword> getLastUsedPasswords()
    {
        if ( lastUsedPasswords == null )
            lastUsedPasswords = new ArrayList<LastUsedPassword>();
        return lastUsedPasswords;
    }

    public void add( LastUsedPassword password )
    {
        getLastUsedPasswords().add( password );
    }
}
