package br.com.mcampos.ejb.user;

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
		description = uct_description_ch;
		userId = usr_id_in;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( other instanceof UserContactPK ) {
			final UserContactPK otherUserContactPK = (UserContactPK) other;
			final boolean areEqual = ( otherUserContactPK.description.equals( description ) && otherUserContactPK.userId.equals( userId ) );
			return areEqual;
		}
		return false;
	}

	@Override
	public int hashCode( )
	{
		return super.hashCode( );
	}

	public String getDescription( )
	{
		return description;
	}

	public void setDescription( String uct_description_ch )
	{
		description = uct_description_ch;
	}

	public Integer getUserId( )
	{
		return userId;
	}

	public void setUserId( Integer usr_id_in )
	{
		userId = usr_id_in;
	}
}
