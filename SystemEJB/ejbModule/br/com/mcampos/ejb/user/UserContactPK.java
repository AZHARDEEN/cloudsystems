package br.com.mcampos.ejb.user;

import java.io.Serializable;

public class UserContactPK implements Serializable
{
	private static final long serialVersionUID = -6044743929308223935L;
	private String description;
	private Integer userId;

	public UserContactPK()
	{
	}

	public UserContactPK( String uct_description_ch, Integer usr_id_in )
	{
		this.description = uct_description_ch;
		this.userId = usr_id_in;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( other instanceof UserContactPK ) {
			final UserContactPK otherUserContactPK = ( UserContactPK ) other;
			final boolean areEqual = ( otherUserContactPK.description.equals( this.description ) && otherUserContactPK.userId.equals( this.userId ) );
			return areEqual;
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		return super.hashCode();
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription( String uct_description_ch )
	{
		this.description = uct_description_ch;
	}

	public Integer getUserId()
	{
		return this.userId;
	}

	public void setUserId( Integer usr_id_in )
	{
		this.userId = usr_id_in;
	}
}
