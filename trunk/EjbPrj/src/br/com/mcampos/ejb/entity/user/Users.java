package br.com.mcampos.ejb.entity.user;


import br.com.mcampos.ejb.cloudsystem.user.attribute.usertype.entity.entity.UserType;

import java.io.Serializable;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@NamedQueries( { @NamedQuery( name = "Users.findAll", query = "select o from Users o" ) } )
@Table( name = "users" )
@Inheritance( strategy = InheritanceType.JOINED )
@DiscriminatorColumn( name = "ust_id_in", discriminatorType = DiscriminatorType.STRING )
public abstract class Users implements Serializable, Comparable<Users>
{
    @Id
    @SequenceGenerator( name = "userIdGenerator", sequenceName = "seq_user", allocationSize = 1 )
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "userIdGenerator" )
    @Column( name = "usr_id_in", nullable = false )
    private Integer id;

    @Column( name = "usr_name_ch", nullable = false, length = 128 )
    private String name;

    @Column( name = "usr_nick_name_ch", nullable = true, length = 128 )
    private String nickName;

    @Column( name = "usr_observation_tx", nullable = true )
    private String comment;

    @Column( name = "usr_insert_dt", insertable = false, updatable = false )
    private Timestamp insertDate;

    @Column( name = "usr_update_dt", insertable = false, updatable = false )
    private Timestamp updateDate;

    @ManyToOne( optional = false )
    @JoinColumn( name = "ust_id_in", nullable = false, referencedColumnName = "ust_id_in", columnDefinition = "Integer" )
    private UserType userType;

    @OneToMany( mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL )
    private List<Address> addresses;

    @OneToMany( mappedBy = "users", fetch = FetchType.EAGER, cascade = { CascadeType.ALL } )
    private List<UserDocument> documents;

    @OneToMany( mappedBy = "user", fetch = FetchType.EAGER, cascade = { CascadeType.ALL } )
    private List<UserContact> contacts;

    public Users()
    {
        super();
    }

    public Users( Integer id, String name, String nickName, String comment, UserType userType )
    {
        super();
        init( id, name, nickName, comment, userType );
    }


    protected void init( Integer id, String name, String nickName, String comment, UserType userType )
    {
        setId( id );
        setName( name );
        setNickName( nickName );
        setComment( comment );
        setUserType( userType );
    }


    public Integer getId()
    {
        return id;
    }

    public void setId( Integer userId )
    {
        this.id = userId;
    }

    public Timestamp getInsertDate()
    {
        return insertDate;
    }

    public void setInsertDate( Timestamp usr_insert_dt )
    {
        this.insertDate = usr_insert_dt;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String n )
    {
        /*Never null*/
        this.name = n;
    }

    public String getNickName()
    {
        return nickName;
    }

    public void setNickName( String nick )
    {
        this.nickName = nick;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment( String usr_observation_tx )
    {
        this.comment = usr_observation_tx;
    }

    public Timestamp getUpdateDate()
    {
        return updateDate;
    }

    public void setUpdateDate( Timestamp usr_update_dt )
    {
        this.updateDate = usr_update_dt;
    }


    public void setUserType( UserType userType )
    {
        this.userType = userType;
    }

    public UserType getUserType()
    {
        return userType;
    }


    public void setAddresses( List<Address> addresses )
    {
        this.addresses = addresses;
    }

    /*
     * A clausula mapped by deve informar o campo destino na entidade Address
     * que, neste caso, e o atributo privado address.user!!
    */

    public List<Address> getAddresses()
    {
        if ( addresses == null )
            addresses = new ArrayList<Address>();
        return addresses;
    }

    public Address addAddress( Address address )
    {
        address.setUser( this );
        getAddresses().add( address );
        return address;
    }

    public Address removeAddress( Address address )
    {
        getAddresses().remove( address );
        return address;
    }


    public void setDocuments( List<UserDocument> userDocument )
    {
        this.documents = userDocument;
    }

    public List<UserDocument> getDocuments()
    {
        if ( documents == null )
            documents = new ArrayList<UserDocument>();
        return documents;
    }

    public UserDocument addDocument( UserDocument item )
    {
        item.setUsers( this );
        getDocuments().add( item );
        return item;
    }

    public UserDocument removeDocument( UserDocument item )
    {
        getDocuments().remove( item );
        return item;
    }

    public void setContacts( List<UserContact> userContact )
    {
        this.contacts = userContact;
    }

    public List<UserContact> getContacts()
    {
        if ( contacts == null )
            contacts = new ArrayList<UserContact>();
        return contacts;
    }


    public UserContact addContact( UserContact item )
    {
        item.setUser( this );
        getContacts().add( item );
        return item;
    }

    public UserContact removeContact( UserContact item )
    {
        getContacts().remove( item );
        return item;
    }

    public int compareTo( Users o )
    {
        if ( o == null || o.getId() == null )
            return 1;
        if ( getId() == null )
            return -1;
        return getId().compareTo( o.getId() );
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj instanceof Users ) {
            Users u = ( Users )obj;

            return getId().equals( u.getId() );
        }
        else
            return false;
    }
}


