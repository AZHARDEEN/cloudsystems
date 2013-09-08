package br.com.mcampos.entity.security;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.mcampos.entity.user.Collaborator;

/**
 * The persistent class for the login_property database table.
 * 
 */
@Entity
@Table( name = "login_property", schema = "public" )
public class LoginProperty implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LoginPropertyPK id;

	@Column( name = "flt_id_in", nullable = false )
	private Integer fieldType;

	@Column( name = "lgp_value_ch", nullable = false, length = 512 )
	private String value;

	@ManyToOne( optional = false )
	@JoinColumns( {
			@JoinColumn(
					name = "usr_id_in", referencedColumnName = "usr_id_in", insertable = false, updatable = false, nullable = false ),
			@JoinColumn(
					name = "col_seq_in", referencedColumnName = "col_seq_in", insertable = false, updatable = false,
					nullable = false )
	} )
	private Collaborator collaborator;

	public LoginProperty( )
	{
	}

	public LoginPropertyPK getId( )
	{
		if ( id == null ) {
			id = new LoginPropertyPK( );
		}
		return id;
	}

	public void setId( LoginPropertyPK id )
	{
		this.id = id;
	}

	public Integer getFieldType( )
	{
		return fieldType;
	}

	public void setFieldType( Integer fltIdIn )
	{
		fieldType = fltIdIn;
	}

	public String getValue( )
	{
		return value;
	}

	public void setValue( String lgpValueCh )
	{
		value = lgpValueCh;
	}

	public Collaborator getCollaborator( )
	{
		return collaborator;
	}

	public void setCollaborator( Collaborator collaborator )
	{
		this.collaborator = collaborator;
		if ( getCollaborator( ) != null )
		{
			getId( ).setCompanyId( getCollaborator( ).getId( ).getCompanyId( ) );
			getId( ).setSequence( getCollaborator( ).getId( ).getSequence( ) );
		}
	}

}