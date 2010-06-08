package br.com.mcampos.ejb.cloudsystem.user.contact.entity;


import br.com.mcampos.ejb.cloudsystem.user.attribute.contacttype.entity.ContactType;
import br.com.mcampos.ejb.cloudsystem.user.Users;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = "UserContact.findAll", query = "select o from UserContact o" ) } )
@Table( name = "user_contact" )
@IdClass( UserContactPK.class )
public class UserContact implements Serializable, Comparable<UserContact>
{
    @Id
    @Column( name = "usr_id_in", nullable = false, insertable = false, updatable = false )
    private Integer userId;

    @Column( name = "cct_id_in", nullable = false, insertable = false, updatable = false )
    private Integer contactId;

    @Id
    @Column( name = "uct_description_ch", nullable = false )
    private String description;

    @Column( name = "uct_observation_tx" )
    private String comment;

    @ManyToOne
    @JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in", nullable = false )
    private Users user;

    @ManyToOne
    @JoinColumn( name = "cct_id_in", referencedColumnName = "cct_id_in", nullable = false )
    protected ContactType contactType;

    public UserContact()
    {
        super();
    }

    public UserContact( ContactType contactType, String description, String comment )
    {
        super();
        init( contactType, description, comment );
    }


    protected void init( ContactType contactType, String description, String comment )
    {
        setDescription( description );
        setComment( comment );
        setContactType( contactType );
    }

    public int compareTo( UserContact o )
    {
        int nRet;

        if ( o == null || o.getUser() == null )
            return 1;
        if ( getUser() == null )
            return -1;
        nRet = getDescription().compareTo( o.getDescription() );
        if ( nRet != 0 )
            return nRet;
        return getUser().compareTo( o.getUser() );
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj instanceof UserContact ) {
            UserContact o = ( UserContact )obj;
            return ( getUserId().equals( o.getUserId() ) && getDescription().equals( o.getDescription() ) );
        }
        else
            return false;
    }


    public Integer getContactId()
    {
        return contactId;
    }

    public void setContactId( Integer cct_id_in )
    {
        this.contactId = cct_id_in;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String uct_description_ch )
    {
        this.description = uct_description_ch;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment( String uct_observation_tx )
    {
        this.comment = uct_observation_tx;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId( Integer userId )
    {
        this.userId = userId;
    }

    public Users getUser()
    {
        return user;
    }

    public void setUser( Users user )
    {
        this.user = user;
        setUserId( user.getId() );
    }

    public ContactType getContactType()
    {
        return contactType;
    }

    public void setContactType( ContactType contactType )
    {
        this.contactType = contactType;
        setContactId( contactType.getId() );
    }
}
