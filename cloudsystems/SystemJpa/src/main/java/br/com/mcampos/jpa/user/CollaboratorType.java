package br.com.mcampos.jpa.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.mcampos.jpa.BaseCompanyEntity;
import br.com.mcampos.jpa.SimpleEntity;

/**
 * The persistent class for the collaborator_type database table.
 * 
 */
@Entity
@Table( name = "collaborator_type", schema = "public" )
public class CollaboratorType extends BaseCompanyEntity implements Serializable, Comparable<CollaboratorType>, SimpleEntity<CollaboratorType>
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CollaboratorTypePK id;

	@Column( name = "clt_description_ch", nullable = false, length = 32 )
	private String description;

	@Column( name = "clt_inherit_role_bt" )
	private Boolean inheritRole;

	public CollaboratorType( )
	{
	}

	public CollaboratorType( Company company )
	{
		super( company );
	}

	public CollaboratorType( Company company, Integer id, String description, Boolean inherit )
	{
		super( company );
		this.getId( ).setId( id );
		this.setDescription( description );
		this.setInheritRole( inherit );
	}

	@Override
	public CollaboratorTypePK getId( )
	{
		if ( this.id == null ) {
			this.id = new CollaboratorTypePK( );
		}
		return this.id;
	}

	public void setId( CollaboratorTypePK key )
	{
		this.id = key;
	}

	@Override
	public String getDescription( )
	{
		return this.description;
	}

	@Override
	public void setDescription( String cltDescriptionCh )
	{
		this.description = cltDescriptionCh;
	}

	public Boolean getInheritRole( )
	{
		return this.inheritRole;
	}

	public void setInheritRole( Boolean inherit )
	{
		this.inheritRole = inherit;
	}

	@Override
	public int compareTo( CollaboratorType other )
	{
		return this.getId( ).compareTo( other.getId( ) );
	}

	@Override
	public boolean equals( Object arg0 )
	{
		return this.getId( ).equals( ( (CollaboratorType) arg0 ).getId( ) );
	}

	@Override
	public void setId( Integer id )
	{
		this.getId( ).setId( id );
	}

	@Override
	public String getField( Integer field )
	{
		switch ( field ) {
		case 0:
			return this.getId( ).getId( ).toString( );
		case 1:
			return this.getDescription( );
		case 2:
			return this.getInheritRole( ).toString( );
		}
		return null;
	}

	@Override
	public int compareTo( CollaboratorType object, Integer field )
	{
		switch ( field ) {
		case 0:
			return this.getId( ).compareTo( object.getId( ) );
		case 1:
			return this.getDescription( ).compareTo( object.getDescription( ) );
		case 2:
			return this.getInheritRole( ).compareTo( object.getInheritRole( ) );
		}
		return 0;
	}
}