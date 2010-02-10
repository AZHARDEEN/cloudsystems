package br.com.mcampos.ejb.entity.login;


import br.com.mcampos.ejb.entity.user.Person;
import br.com.mcampos.ejb.entity.user.attributes.UserStatus;

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
@NamedQueries( { @NamedQuery( name = "Login.findAll", query = "select o from Login o" ), @NamedQuery( name = "Login.findToken", query = "select o from Login o where o.token = :token" ) } )
@Table( name = "\"login\"" )
public class Login implements Serializable
{
    private Integer userId;
    private Timestamp passwordExpirationDate;
    private String password;
    private Integer tryCount;
    private String token;


    protected Person person;
    protected UserStatus userStatus;
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
        this.passwordExpirationDate = usr_passwd_exp_dt;
        this.password = usr_password_ch;
        this.userStatus = userType;

    }


    @Id
    @Column( name = "usr_id_in", nullable = false, insertable = false, updatable = false )
    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId( Integer usr_id_in )
    {
        this.userId = usr_id_in;
    }

    @Column( name = "lgi_passwd_exp_dt" )
    public Timestamp getPasswordExpirationDate()
    {
        return passwordExpirationDate;
    }

    public void setPasswordExpirationDate( Timestamp usr_passwd_exp_dt )
    {
        this.passwordExpirationDate = usr_passwd_exp_dt;
    }

    @Column( name = "lgi_password_ch", nullable = false, columnDefinition = "VARCHAR(64)" )
    public String getPassword()
    {
        return password;
    }

    public void setPassword( String usr_password_ch )
    {
        this.password = usr_password_ch;
    }


    public void setPerson( Person person )
    {
        this.person = person;
        if ( getPerson() != null ) {
            person.setLogin( this );
        }
    }

    @OneToOne( fetch = FetchType.EAGER )
    @JoinColumn( name = "usr_id_in", nullable = false, referencedColumnName = "usr_id_in", columnDefinition = "Integer" )
    public Person getPerson()
    {
        return person;
    }

    public void setUserStatus( UserStatus userType )
    {
        this.userStatus = userType;
    }

    @ManyToOne( fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "uts_id_in", nullable = false, referencedColumnName = "uts_id_in", columnDefinition = "Integer" )
    public UserStatus getUserStatus()
    {
        return userStatus;
    }

    public void setTryCount( Integer tryCount )
    {
        this.tryCount = tryCount;
    }

    @Column( name = "lgi_try_count_in", nullable = true, columnDefinition = "Integer" )
    public Integer getTryCount()
    {
        return tryCount;
    }

    public Integer incrementTryCount()
    {
        if ( tryCount == null )
            tryCount = new Integer( 1 );
        else
            tryCount += 1;
        return tryCount;
    }

    public void setToken( String token )
    {
        this.token = token;
    }

    @Column( name = "lgi_token_ch", nullable = true, columnDefinition = "VARCHAR(32)" )
    public String getToken()
    {
        return token;
    }

    public void setLastUsedPasswords( List<LastUsedPassword> lastUsedPasswords )
    {
        this.lastUsedPasswords = lastUsedPasswords;
    }

    @OneToMany( mappedBy = "login", cascade = CascadeType.ALL )
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
