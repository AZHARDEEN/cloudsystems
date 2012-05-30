package br.com.mcampos.ejb.user.address;

import java.io.Serializable;
import java.util.Date;


public class AddressPK implements Serializable
{
	private static final long serialVersionUID = 2486390518187267255L;
	private Integer addressTypeId;
	private Integer userId;
	private Date toDate;


	public AddressPK()
	{
	}

	public AddressPK( Integer userId, Integer type, Date toDate )
	{
		this.addressTypeId = type;
		this.userId = userId;
		this.toDate = toDate;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( other instanceof AddressPK ) {
			final AddressPK otherAddressPK = ( AddressPK ) other;
			final boolean areEqual = ( otherAddressPK.addressTypeId.equals( this.addressTypeId ) && otherAddressPK.userId.equals( this.userId ) && otherAddressPK.toDate.equals( this.toDate ) );
			return areEqual;
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		return super.hashCode();
	}

	public Integer getAddressTypeId()
	{
		return this.addressTypeId;
	}

	public void setAddressTypeId( Integer adt_id_in )
	{
		this.addressTypeId = adt_id_in;
	}

	public Integer getUserId()
	{
		return this.userId;
	}

	public void setUserId( Integer usr_id_in )
	{
		this.userId = usr_id_in;
	}

	public void setToDate( Date toDate )
	{
		this.toDate = toDate;
	}

	public Date getToDate()
	{
		return this.toDate;
	}
}
