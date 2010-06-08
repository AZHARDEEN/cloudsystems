package br.com.mcampos.ejb.cloudsystem.user.person.entity;


import br.com.mcampos.ejb.cloudsystem.user.attribute.civilstate.entity.CivilState;
import br.com.mcampos.ejb.cloudsystem.user.attribute.gender.entity.Gender;
import br.com.mcampos.ejb.cloudsystem.user.attribute.title.entity.Title;
import br.com.mcampos.ejb.cloudsystem.user.login.Login;
import br.com.mcampos.ejb.cloudsystem.locality.city.entity.City;
import br.com.mcampos.ejb.cloudsystem.user.Users;

import java.io.Serializable;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = "Person.findAll", query = "select o from Person o" ) } )
@Table( name = "person" )
@DiscriminatorValue( "1" )
public class Person extends Users implements Serializable
{
    public static final Integer userTypeIdentification = 1;

    @ManyToOne( fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "cst_id_in", nullable = false, referencedColumnName = "cst_id_in" )
    private CivilState civilState;

    @ManyToOne( fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "gnd_id_in", referencedColumnName = "gnd_id_in" )
    private Gender gender;

    @ManyToOne( fetch = FetchType.EAGER, optional = false )
    @JoinColumn( name = "ttl_id_in", referencedColumnName = "ttl_id_in" )
    private Title title;

    @Column( name = "usr_birth_dt" )
    private Timestamp birthDate;

    @Column( name = "usr_father_name_ch" )
    private String fatherName;

    @Column( name = "usr_first_name_ch" )
    private String firstName;

    @Column( name = "usr_last_name_ch" )
    private String lastName;

    @Column( name = "usr_middle_name_ch" )
    private String middleName;

    @Column( name = "usr_mother_name_ch" )
    private String motherName;

    @ManyToOne( fetch = FetchType.EAGER, optional = true )
    @JoinColumn( name = "usr_born_city_in", nullable = true, referencedColumnName = "cit_id_in" )
    private City bornCity;

    @OneToOne( mappedBy = "person", fetch = FetchType.EAGER )
    private Login login;

    public Person()
    {
        super();
    }

    public CivilState getCivilState()
    {
        return civilState;
    }

    public void setCivilState( CivilState civilState )
    {
        this.civilState = civilState;
    }

    public Gender getGender()
    {
        return gender;
    }

    public void setGender( Gender gender )
    {
        this.gender = gender;
    }

    public Title getTitle()
    {
        return title;
    }

    public void setTitle( Title Title )
    {
        this.title = Title;
    }

    public Timestamp getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate( Timestamp usr_birth_dt )
    {
        this.birthDate = usr_birth_dt;
    }

    public String getFatherName()
    {
        return fatherName;
    }

    protected String toUpperCase( String s )
    {
        if ( s != null )
            return s.toUpperCase();
        else
            return s;
    }

    public void setFatherName( String name )
    {
        this.fatherName = toUpperCase( name );
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName( String usr_first_name_ch )
    {
        this.firstName = toUpperCase( usr_first_name_ch );
    }


    public String getLastName()
    {
        return lastName;
    }

    public void setLastName( String usr_last_name_ch )
    {
        this.lastName = toUpperCase( usr_last_name_ch );
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public void setMiddleName( String usr_middle_name_ch )
    {
        this.middleName = toUpperCase( usr_middle_name_ch );
    }

    public String getMotherName()
    {
        return motherName;
    }

    public void setMotherName( String usr_mother_name_ch )
    {
        this.motherName = toUpperCase( usr_mother_name_ch );
    }

    public void setLogin( Login l )
    {
        this.login = l;
        if ( l != null )
            l.setUserId( getId() );
    }

    /*
     * DO NOT change fetch type.
     */

    public Login getLogin()
    {
        return login;
    }

    public void setBornCity( City bornCity )
    {
        this.bornCity = bornCity;
    }

    public City getBornCity()
    {
        return bornCity;
    }
}
