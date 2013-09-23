package br.com.mcampos.dto.address;


import java.io.Serializable;


public class AddressDTO implements Serializable, Comparable<AddressDTO>
{

    /**
     *
     */
    private static final long serialVersionUID = 5879723922351784270L;
    private AddressTypeDTO addressType;
    private CityDTO city;

    private String address;
    private String district;
    private String comment;
    private String zip;

    public AddressDTO()
    {
        super();
    }

    public void setAddressType( AddressTypeDTO addressType )
    {
        this.addressType = addressType;
    }

    public AddressTypeDTO getAddressType()
    {
        return addressType;
    }

    public void setCity( CityDTO city )
    {
        this.city = city;
    }

    public CityDTO getCity()
    {
        return city;
    }

    public void setAddress( String address )
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }

    public void setDistrict( String district )
    {
        this.district = district;
    }

    public String getDistrict()
    {
        return district;
    }

    public void setComment( String comment )
    {
        this.comment = comment;
    }

    public String getComment()
    {
        return comment;
    }

    public void setZip( String zip )
    {
        this.zip = zip;
    }

    public String getZip()
    {
        return zip;
    }

    public int compareTo( AddressDTO o )
    {
        return 0;
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj instanceof AddressDTO ) {
            AddressDTO dto = ( AddressDTO )obj;
            return getAddressType().equals( dto.getAddressType() );
        }
        return false;
    }
}
