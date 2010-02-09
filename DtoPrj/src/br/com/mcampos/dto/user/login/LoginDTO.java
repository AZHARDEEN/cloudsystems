package br.com.mcampos.dto.user.login;


import br.com.mcampos.dto.core.BasicDTO;
import br.com.mcampos.dto.user.PersonDTO;
import br.com.mcampos.dto.user.UserDTO;
import br.com.mcampos.dto.user.attributes.UserStatusDTO;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta classe representa o DTO da entidade Login.
 *
 *
 */
public class LoginDTO extends BasicDTO
{
    Integer userId;
    protected PersonDTO person;
    protected UserStatusDTO userStatus;
    protected Timestamp passwordExpirationDate;
    protected String password;
    protected List<UserDTO> businessEntities;


    public LoginDTO()
    {
        super();
    }

    public LoginDTO( UserStatusDTO userStatus, String usr_password_ch )
    {
        super();
        init( userStatus, usr_password_ch );
    }


    protected void init( UserStatusDTO userStatus, String usr_password_ch )
    {
        setPerson( null );
        this.userStatus = userStatus;
        this.passwordExpirationDate = null;
        this.password = usr_password_ch;
    }


    public void setPasswordExpirationDate( Timestamp usr_passwd_exp_dt )
    {
        this.passwordExpirationDate = usr_passwd_exp_dt;
    }

    public Timestamp getPasswordExpirationDate()
    {
        return passwordExpirationDate;
    }

    public void setPassword( String usr_password_ch )
    {
        this.password = usr_password_ch;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPerson( PersonDTO person )
    {
        this.person = person;
        if ( person != null ) {
            person.setLogin( this );
            this.setUserId( person.getUserId() );
        }
    }

    public PersonDTO getPerson()
    {
        return person;
    }

    public void setUserStatus( UserStatusDTO userType )
    {
        this.userStatus = userType;
    }

    public UserStatusDTO getUserStatus()
    {
        return userStatus;
    }

    public void setUserId( Integer userId )
    {
        this.userId = userId;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setBusinessEntities( List<UserDTO> businessEntities )
    {
        this.businessEntities = businessEntities;
    }

    public List<UserDTO> getBusinessEntities()
    {
        if ( businessEntities == null )
            businessEntities = new ArrayList<UserDTO>();
        return businessEntities;
    }

    public void addBusinessEntity( UserDTO dto )
    {
        getBusinessEntities().add( dto );
    }
}


