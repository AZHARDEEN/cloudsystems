package br.com.mcampos.dto.user;


import br.com.mcampos.dto.address.AddressDTO;
import br.com.mcampos.dto.core.DisplayNameDTO;
import br.com.mcampos.dto.user.attributes.UserTypeDTO;

import java.util.ArrayList;

public abstract class UserDTO extends DisplayNameDTO implements Comparable<UserDTO>
{
    /**
     *
     */
    private static final long serialVersionUID = 230335703324655910L;
    private Integer id;
    private String name;
    private String nickName;
    private String comment;
    private UserTypeDTO userType;

    private ArrayList<AddressDTO> addressList;
    private ArrayList<UserDocumentDTO> documentList;
    private ArrayList<UserContactDTO> contactList;

    public UserDTO()
    {
        super();
    }

    public String getDisplayName()
    {
        return null;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setNickName( String nickName )
    {
        this.nickName = nickName;
    }

    public String getNickName()
    {
        return nickName;
    }

    public void setComment( String comment )
    {
        this.comment = comment;
    }

    public String getComment()
    {
        return comment;
    }


    public void setUserType( UserTypeDTO userType )
    {
        this.userType = userType;
    }

    public UserTypeDTO getUserType()
    {
        return userType;
    }

    public void setAddressList( ArrayList<AddressDTO> addressList )
    {
        this.addressList = addressList;
    }

    public ArrayList<AddressDTO> getAddressList()
    {
        if ( addressList == null )
            addressList = new ArrayList<AddressDTO>();
        return addressList;
    }

    public void add( AddressDTO dto )
    {
        if ( dto == null )
            return;
        /*exists some address with same type???*/
        for ( AddressDTO item : getAddressList() ) {
            if ( item.getAddressType().compareTo( dto.getAddressType() ) == 0 ) {
                getAddressList().remove( item );
                break;
            }
        }
        getAddressList().add( dto );
    }

    public void setDocumentList( ArrayList<UserDocumentDTO> documentList )
    {
        this.documentList = documentList;
    }

    public ArrayList<UserDocumentDTO> getDocumentList()
    {
        if ( documentList == null )
            documentList = new ArrayList<UserDocumentDTO>();
        return documentList;
    }

    public void setContactList( ArrayList<UserContactDTO> contactList )
    {
        this.contactList = contactList;
    }

    public ArrayList<UserContactDTO> getContactList()
    {
        if ( contactList == null )
            contactList = new ArrayList<UserContactDTO>();
        return contactList;
    }

    public void add( UserDocumentDTO dto )
    {
        if ( dto == null )
            return;
        dto.setUserId( this.getId() );
        for ( UserDocumentDTO item : getDocumentList() ) {
            if ( item.getDocumentType().compareTo( dto.getDocumentType() ) == 0 ) {
                getDocumentList().remove( item );
                break;
            }
        }
        getDocumentList().add( dto );
    }


    public void add( UserContactDTO dto )
    {
        if ( dto == null )
            return;
        dto.setUser( this );
        getContactList().add( dto );
    }

    public int compareTo( UserDTO o )
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
        if ( obj instanceof UserDTO ) {
            UserDTO dto = ( UserDTO )obj;

            if ( dto.getId() != null )
                return dto.getId().equals( dto.getId() );
            else
                return false;
        }
        return super.equals( obj );
    }
}
