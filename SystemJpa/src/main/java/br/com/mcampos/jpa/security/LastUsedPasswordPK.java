package br.com.mcampos.jpa.security;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the last_used_password database table.
 * 
 */
@Embeddable
public class LastUsedPasswordPK implements Serializable
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "usr_id_in", unique = true, nullable = false )
	private Integer id;

	@Column( name = "lup_password_ch", unique = true, nullable = false, length = 64 )
	private String password;

	public LastUsedPasswordPK( )
	{
	}

	public LastUsedPasswordPK( Login login )
	{
		set( login );
	}

	public void set( Login login )
	{
		if ( login != null ) {
			setId( login.getId( ) );
			setPassword( login.getPassword( ) );
		}
	}

	public Integer getId( )
	{
		return this.id;
	}

	public void setId( Integer usrIdIn )
	{
		this.id = usrIdIn;
	}

	public String getPassword( )
	{
		return this.password;
	}

	public void setPassword( String lupPasswordCh )
	{
		this.password = lupPasswordCh;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( this == other ) {
			return true;
		}
		if ( !( other instanceof LastUsedPasswordPK ) ) {
			return false;
		}
		LastUsedPasswordPK castOther = (LastUsedPasswordPK) other;
		return this.id.equals( castOther.id )
				&& this.password.equals( castOther.password );

	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id.hashCode( );
		hash = hash * prime + this.password.hashCode( );

		return hash;
	}
}