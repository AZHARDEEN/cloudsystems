package br.com.mcampos.jpa.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the address database table.
 * 
 */
@Embeddable
public class AddressPK implements Serializable, Comparable<AddressPK>
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "usr_id_in", unique = true, nullable = false )
	private Integer userId;

	@Column( name = "adt_id_in", unique = true, nullable = false )
	private Integer addressType;

	public AddressPK( )
	{
	}

	public Integer getUserId( )
	{
		return this.userId;
	}

	public void setUserId( Integer usrIdIn )
	{
		this.userId = usrIdIn;
	}

	public Integer getAddressType( )
	{
		return this.addressType;
	}

	public void setAddressType( Integer adtIdIn )
	{
		this.addressType = adtIdIn;
	}

	@Override
	public boolean equals( Object other )
	{
		if( this == other ) {
			return true;
		}
		if( !(other instanceof AddressPK) ) {
			return false;
		}
		AddressPK castOther = (AddressPK) other;
		return this.userId.equals( castOther.userId )
				&& this.addressType.equals( castOther.addressType );

	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId.hashCode( );
		hash = hash * prime + this.addressType.hashCode( );

		return hash;
	}

	@Override
	public int compareTo( AddressPK other )
	{
		int nRet = this.getUserId( ).compareTo( other.getUserId( ) );
		if( nRet == 0 ) {
			nRet = this.getAddressType( ).compareTo( other.getAddressType( ) );
		}
		return nRet;
	}

}