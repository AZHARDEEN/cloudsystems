package br.com.mcampos.dto.user;

import br.com.mcampos.dto.user.attributes.ContactTypeDTO;

import java.io.Serializable;

public class UserContactDTO implements Serializable, Comparable<UserContactDTO>
{
    private ContactTypeDTO contactType;
    private String description;
    private String comment;
    
    private UserDTO user;
    
    public UserContactDTO()
    {
        super();
    }

    public UserContactDTO( ContactTypeDTO contactType, String description,
                           String comment )
    {
        super();
        init ( contactType, description, comment );
    }
    
    protected void init (ContactTypeDTO contactType, String description,
                           String comment)
    {
        this.contactType = contactType;
        this.description = description;
        this.comment = comment;
    }


    public void setContactType( ContactTypeDTO contactType )
    {
        this.contactType = contactType;
    }

    public ContactTypeDTO getContactType()
    {
        return contactType;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public void setComment( String comment )
    {
        this.comment = comment;
    }

    public String getComment()
    {
        return comment;
    }

    public void setUser( UserDTO user )
    {
        this.user = user;
    }

    public UserDTO getUser()
    {
        return user;
    }

    public int compareTo( UserContactDTO o )
    {
        int nRet;
        
        nRet = getUser().getId().compareTo( o.getUser().getId() );
        if ( nRet != 0 )
            return nRet;
        return getDescription().compareTo( o.getDescription() );
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj instanceof UserContactDTO  ) {
            UserContactDTO o = ( UserContactDTO ) obj;
            
            if ( getUser() != null ) {
                return ( getUser().getId().equals( o.getUser().getId() ) 
                       && getDescription().equals( o.getDescription() ) );
            }
            else return getDescription().equals( o.getDescription() );
        }
        else return false;
    }
}
