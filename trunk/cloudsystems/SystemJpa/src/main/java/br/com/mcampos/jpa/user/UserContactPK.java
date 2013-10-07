package br.com.mcampos.jpa.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserContactPK implements Serializable
{
	private static final long serialVersionUID = -6044743929308223935L;
	@Column( name = "uct_description_ch", nullable = false )
	private String description;

	@Column( name = "usr_id_in", nullable = false )
	private Integer userId;

	public UserContactPK( )
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
		if( other instanceof UserContactPK ) {
			final UserContactPK otherUserContactPK = (UserContactPK) other;
			final boolean areEqual = (otherUserContactPK.description.equals( this.description ) && otherUserContactPK.userId.equals( this.userId ));
			return areEqual;
		}
		return false;
	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.getUserId( ).hashCode( );
		hash = hash * prime + this.getDescription( ).hashCode( );

		return hash;
	}

	public String getDescription( )
	{
		return this.description;
	}

	public void setDescription( String uct_description_ch )
	{
		this.description = uct_description_ch;
	}

	public Integer getUserId( )
	{
		return this.userId;
	}

	public void setUserId( Integer usr_id_in )
	{
		this.userId = usr_id_in;
	}
}
