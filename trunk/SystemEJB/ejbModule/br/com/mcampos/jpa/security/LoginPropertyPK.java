package br.com.mcampos.jpa.security;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.com.mcampos.jpa.user.Collaborator;

/**
 * The primary key class for the login_property database table.
 * 
 */
@Embeddable
public class LoginPropertyPK implements Serializable
{
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column( name = "usr_id_in", unique = true, nullable = false )
	private Integer companyId;

	@Column( name = "col_seq_in", unique = true, nullable = false )
	private Integer sequence;

	@Column( name = "lgp_name_ch", unique = true, nullable = false, length = 64 )
	private String name;

	public LoginPropertyPK( )
	{
	}

	public LoginPropertyPK( Collaborator collaborator, String name )
	{
		setCompanyId( collaborator.getId( ).getCompanyId( ) );
		setSequence( collaborator.getId( ).getSequence( ) );
		setName( name );
	}

	public String getName( )
	{
		return this.name;
	}

	public void setName( String lgpNameCh )
	{
		this.name = lgpNameCh;
	}

	@Override
	public boolean equals( Object other )
	{
		if ( this == other ) {
			return true;
		}
		if ( !( other instanceof LoginPropertyPK ) ) {
			return false;
		}
		LoginPropertyPK castOther = (LoginPropertyPK) other;
		return getCompanyId( ).equals( castOther.getCompanyId( ) )
				&& this.getSequence( ).equals( castOther.getSequence( ) )
				&& this.name.equals( castOther.name );

	}

	@Override
	public int hashCode( )
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.getCompanyId( ).hashCode( );
		hash = hash * prime + this.getSequence( ).hashCode( );
		hash = hash * prime + this.getName( ).hashCode( );

		return hash;
	}

	protected Integer getCompanyId( )
	{
		return this.companyId;
	}

	protected void setCompanyId( Integer companyId )
	{
		this.companyId = companyId;
	}

	protected Integer getSequence( )
	{
		return this.sequence;
	}

	protected void setSequence( Integer sequence )
	{
		this.sequence = sequence;
	}
}