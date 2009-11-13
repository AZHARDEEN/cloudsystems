package br.com.mcampos.ejb.entity.login;

import java.io.Serializable;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
  @NamedQuery(name = "LastUsedPassword.findAll", query = "select o from LastUsedPassword o where o.login.userId = :id")
})
@Table( name = "\"last_used_password\"" )
@IdClass( LastUsedPasswordPK.class )
public class LastUsedPassword implements Serializable
{
    private Timestamp fromDate;
    private String password;
    private Timestamp toDate;
    private Integer userId;
    
    Login login;

    public LastUsedPassword()
    {
    }

    public LastUsedPassword( Timestamp lup_from_dt, String lup_password_ch,
                             Timestamp lup_to_dt, Integer usr_id_in )
    {
        setFromDate( lup_from_dt );
        setPassword( lup_password_ch );
        setToDate( lup_to_dt );
        setUserId( usr_id_in );
    }

    @Column( name="lup_from_dt", nullable = false )
    public Timestamp getFromDate()
    {
        return fromDate;
    }

    public void setFromDate( Timestamp lup_from_dt )
    {
        this.fromDate = lup_from_dt;
    }

    @Id
    @Column( name="lup_password_ch", nullable = false )
    public String getPassword()
    {
        return password;
    }

    public void setPassword( String lup_password_ch )
    {
        this.password = lup_password_ch;
    }

    @Column( name="lup_to_dt" )
    public Timestamp getToDate()
    {
        return toDate;
    }

    public void setToDate( Timestamp lup_to_dt )
    {
        this.toDate = lup_to_dt;
    }

    @Id
    @Column( name="usr_id_in", nullable = false, insertable = false, updatable = false )
    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId( Integer usr_id_in )
    {
        this.userId = usr_id_in;
    }

    public void setLogin( Login login )
    {
        this.login = login;
        if ( getLogin() != null )
            setUserId( getLogin().getUserId() );
    }

    @ManyToOne (fetch = FetchType.EAGER, optional = false )
    @JoinColumn(name = "usr_id_in", nullable = false, referencedColumnName = "usr_id_in", columnDefinition = "Integer" )
    public Login getLogin()
    {
        return login;
    }
}
