package br.com.mcampos.dto.user;


import br.com.mcampos.dto.address.CityDTO;
import br.com.mcampos.dto.user.attributes.CivilStateDTO;
import br.com.mcampos.dto.user.attributes.GenderDTO;
import br.com.mcampos.dto.user.attributes.TitleDTO;

import br.com.mcampos.dto.user.attributes.UserTypeDTO;

import br.com.mcampos.dto.user.login.LoginDTO;

import java.sql.Timestamp;

import java.util.Date;

public class PersonDTO extends UserDTO
{
    public static final Integer userTypeIdentification = 1;
    
    private Timestamp birthDate;
    private String fatherName;
    private String firstName;
    private Integer userId;
    private String lastName;
    private String middleName;
    private String motherName;

    private CivilStateDTO civilState;
    private GenderDTO gender;
    private TitleDTO title;
    
    private CityDTO bornCity;
    
    private LoginDTO login;
    
    public PersonDTO()
    {
        super();
        setUserType( new UserTypeDTO ( userTypeIdentification ) );
    }

    public static Integer getUserTypeIdentification()
    {
        return userTypeIdentification;
    }

    public void setBirthDate( Timestamp birthDate )
    {
        this.birthDate = birthDate;
    }
    
    public void setBirthDate ( java.util.Date birthdate )
    {
        setBirthDate( ( ( birthdate != null ) ? new Timestamp ( birthDate.getTime() ) : null ) );
    }

    public Timestamp getBirthDate()
    {
        return birthDate;
    }

    public void setFatherName( String fatherName )
    {
        this.fatherName = fatherName;
    }

    public String getFatherName()
    {
        return fatherName;
    }

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setUserId( Integer userId )
    {
        this.userId = userId;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setMiddleName( String middleName )
    {
        this.middleName = middleName;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public void setMotherName( String motherName )
    {
        this.motherName = motherName;
    }

    public String getMotherName()
    {
        return motherName;
    }

    public void setCivilState( CivilStateDTO civilState )
    {
        this.civilState = civilState;
    }

    public CivilStateDTO getCivilState()
    {
        return civilState;
    }

    public void setGender( GenderDTO gender )
    {
        this.gender = gender;
    }

    public GenderDTO getGender()
    {
        return gender;
    }

    public void setTitle( TitleDTO title )
    {
        this.title = title;
    }

    public TitleDTO getTitle()
    {
        return title;
    }

    public void setLogin( LoginDTO login )
    {
        this.login = login;
    }

    public LoginDTO getLogin()
    {
        return login;
    }

    public void setBornCity( CityDTO bornCity )
    {
        this.bornCity = bornCity;
    }

    public CityDTO getBornCity()
    {
        return bornCity;
    }
}
