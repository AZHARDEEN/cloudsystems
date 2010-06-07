package br.com.mcampos.ejb.cloudsystem.user.address.entity;


import br.com.mcampos.ejb.cloudsystem.user.address.addresstype.entity.AddressType;
import br.com.mcampos.ejb.cloudsystem.locality.city.entity.City;
import br.com.mcampos.ejb.entity.user.Users;

import java.io.Serializable;

import java.sql.Timestamp;

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
@NamedQueries( { @NamedQuery( name = Address.getAll, query = "select o from Address o where o.user = ?1" ) } )
@Table( name = "address" )
@IdClass( AddressPK.class )
public class Address implements Serializable, Comparable<Address>
{
    public static final String getAll = "Address.findAll";

    @Id
    @Column( name = "usr_id_in", nullable = false, insertable = false, updatable = false )
    private Integer userId;

    @Id
    @Column( name = "adt_id_in", nullable = false, updatable = false, insertable = false )
    private Integer addressTypeId;

    @Column( name = "adr_adress_ch", nullable = false, length = 256 )
    private String address;

    @Column( name = "adr_district_ch", length = 64 )
    private String district;

    @Column( name = "adr_obs_tx" )
    private String comment;

    @Column( name = "adr_to_dt" )
    private Timestamp toDate;

    @Column( name = "adr_zip_code_ch", nullable = false, length = 24 )
    private String zip;

    @ManyToOne
    @JoinColumn( name = "adt_id_in", referencedColumnName = "adt_id_in", nullable = false )
    private AddressType addressType;

    @ManyToOne
    @JoinColumn( name = "cit_id_in", referencedColumnName = "cit_id_in", nullable = false )
    private City city;

    @ManyToOne
    @JoinColumn( name = "usr_id_in", referencedColumnName = "usr_id_in", nullable = false )
    private Users user;

    public Address()
    {
        super();
    }

    public Address( Users user, AddressType type )
    {
        super();
        setUser( user );
        setAddressType( type );
    }


    public String getAddress()
    {
        return address;
    }

    public void setAddress( String address )
    {
        this.address = address;
    }

    public String getDistrict()
    {
        return district;
    }

    public void setDistrict( String district )
    {
        this.district = district;
    }


    public String getComment()
    {
        return comment;
    }

    public void setComment( String comment )
    {
        this.comment = comment;
    }

    public Timestamp getToDate()
    {
        return toDate;
    }

    public void setToDate( Timestamp toDt )
    {
        this.toDate = toDt;
    }

    public String getZip()
    {
        return zip;
    }

    public void setZip( String zip )
    {
        this.zip = zip;
    }

    public AddressType getAddressType()
    {
        return addressType;
    }

    public void setAddressType( AddressType type )
    {
        this.addressType = type;
        if ( type != null )
            setAddressTypeId( type.getId() );
    }

    public City getCity()
    {
        return city;
    }

    public void setCity( City city )
    {
        this.city = city;
    }

    public Users getUser()
    {
        return user;
    }

    public void setUser( Users u )
    {
        this.user = u;
        if ( u != null )
            this.setUserId( u.getId() );
    }

    public void setUserId( Integer userId )
    {
        this.userId = userId;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setAddressTypeId( Integer type )
    {
        this.addressTypeId = type;
    }

    public Integer getAddressTypeId()
    {
        return addressTypeId;
    }

    public int compareTo( Address o )
    {
        if ( o == null )
            return -1;
        int nRet = getAddressTypeId().compareTo( o.getAddressTypeId() );
        if ( nRet != 0 )
            return nRet;
        return getUserId().compareTo( o.getUserId() );
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj instanceof Address ) {
            Address a = ( Address )obj;

            return getUserId().equals( a.getUserId() ) && getAddressTypeId().equals( a.getAddressTypeId() );
        }
        else
            return false;
    }
}

