package br.com.mcampos.ejb.entity.user;


import br.com.mcampos.ejb.entity.address.AddressType;

import br.com.mcampos.ejb.entity.address.City;

import br.com.mcampos.ejb.entity.user.pk.AddressPK;

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
@NamedQueries({
  @NamedQuery(name = "Address.findAll", query = "select o from Address o")
})
@Table( name = "\"address\"" )
@IdClass( AddressPK.class )
public class Address implements Serializable, Comparable<Address>
{
    private Integer userId;
    private Integer addressTypeId;
    
    
    private String address;
    private String district;
    private String comment;
    private Timestamp toDate;
    private String zip;
    
    
    private AddressType addressType;
    private City  city;
    private Users user;

    public Address()
    {
        super ();
    }

    public Address( String adr_adress_ch, String adr_district_ch,
                    String adr_obs_tx, String adr_zip_code_ch,
                    AddressType adt_id_in, City cit_id_in )
    {
        super ();
        init ( adr_adress_ch, adr_district_ch, adr_obs_tx,
                    adr_zip_code_ch, adt_id_in, cit_id_in );
    }
    
    protected  void init( String address, String district,
                    String comment,
                    String zip,
                    AddressType addressType, City city )
    {
        setAddress ( address );
        setDistrict( district );
        setComment( comment );
        setZip(zip );
        setAddressType ( addressType );
        setCity ( city );
    }
    

    @Column( name="adr_adress_ch", nullable = false, length = 256 )
    public String getAddress()
    {
        return address;
    }

    public void setAddress( String address )
    {
        this.address = address;
    }

    @Column( name="adr_district_ch", length = 64 )
    public String getDistrict()
    {
        return district;
    }

    public void setDistrict( String district )
    {
        this.district = district;
    }


    @Column( name="adr_obs_tx" )
    public String getComment()
    {
        return comment;
    }

    public void setComment( String comment )
    {
        this.comment = comment;
    }

    @Column( name="adr_to_dt" )
    public Timestamp getToDate()
    {
        return toDate;
    }

    public void setToDate( Timestamp toDt )
    {
        this.toDate = toDt;
    }

    @Column( name="adr_zip_code_ch", nullable = false, length = 24 )
    public String getZip()
    {
        return zip;
    }

    public void setZip( String zip )
    {
        this.zip = zip;
    }

    @ManyToOne
    @JoinColumn (name = "adt_id_in", referencedColumnName = "adt_id_in", nullable = false )
    public AddressType getAddressType()
    {
        return addressType;
    }

    public void setAddressType( AddressType addressType )
    {
        this.addressType = addressType;
        if ( this.addressType != null )
            setAddressTypeId( getAddressType().getId() );
    }

    @ManyToOne
    @JoinColumn (name = "cit_id_in", referencedColumnName = "cit_id_in", nullable = false )
    public City getCity()
    {
        return city;
    }

    public void setCity( City city )
    {
        this.city = city;
    }

    @ManyToOne
    @JoinColumn (name = "usr_id_in", referencedColumnName = "usr_id_in", nullable = false )
    public Users getUser()
    {
        return user;
    }

    public void setUser( Users user )
    {
        this.user = user;
        if ( user != null )
            this.setUserId( user.getId() );
    }

    public void setUserId( Integer userId )
    {
        this.userId = userId;
    }

    @Id
    @Column( name="usr_id_in", nullable = false, insertable = false, updatable = false )
    public Integer getUserId()
    {
        return userId;
    }

    public void setAddressTypeId( Integer addressTypeId )
    {
        this.addressTypeId = addressTypeId;
    }

    @Id
    @Column( name="adt_id_in", nullable = false, updatable = false, insertable = false )
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
            Address a = ( Address ) obj;
            
            return getUserId().equals( a.getUserId() ) && getAddressTypeId().equals( a.getAddressTypeId() );
        }
        else 
            return false;
    }
}

